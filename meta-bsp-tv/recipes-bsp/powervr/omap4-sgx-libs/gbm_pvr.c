/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice (including the next
 * paragraph) shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <stdint.h>
#include <stdbool.h>
#include <string.h>
#include <errno.h>
#include <limits.h>
#include <assert.h>

#include <sys/types.h>
#include <unistd.h>
#include <xf86drm.h>
#include <omap_drmif.h>
#include <drm_fourcc.h>
#include <sys/mman.h>

#include "gbm_pvrint.h"

static uint32_t
gbm_pvr_format_canonicalize(uint32_t gbm_format)
{
   switch (gbm_format) {
   case GBM_BO_FORMAT_XRGB8888:
      return GBM_FORMAT_XRGB8888;
   case GBM_BO_FORMAT_ARGB8888:
      return GBM_FORMAT_ARGB8888;
   default:
      return gbm_format;
   }
}

static int
gbm_pvr_is_format_supported(struct gbm_device *gbm,
                            uint32_t format,
                            uint32_t usage)
{
   struct gbm_pvr_device *dri = gbm_pvr_device(gbm);

   if ((usage & GBM_BO_USE_CURSOR) && (usage & GBM_BO_USE_RENDERING))
      return 0;

   format = gbm_pvr_format_canonicalize(format);
   switch (format) {
      case GBM_FORMAT_XRGB8888:
      case GBM_FORMAT_ARGB8888:
         return 1;
      default:
         return 0;
   }
}

static int
gbm_pvr_get_format_modifier_plane_count(struct gbm_device *gbm,
                                        uint32_t format,
                                        uint64_t modifier)
{
   return -1;
}

static int
gbm_pvr_bo_write(struct gbm_bo *_bo, const void *buf, size_t count)
{
   struct gbm_pvr_bo *bo = gbm_pvr_bo(_bo);

   void *map = omap_bo_map(bo->bo);
   if (!map) {
      errno = EINVAL;
      return -1;
   }

   memcpy(map, buf, count);

   return 0;
}

static int
gbm_pvr_bo_get_fd(struct gbm_bo *_bo)
{
   return -1;
}

static int
gbm_pvr_bo_get_planes(struct gbm_bo *_bo)
{
   return 1;
}

static union gbm_bo_handle
gbm_pvr_bo_get_handle_for_plane(struct gbm_bo *_bo, int plane)
{
   return _bo->handle;
}

static uint32_t
gbm_pvr_bo_get_stride(struct gbm_bo *_bo, int plane)
{
   return _bo->stride;
}

static uint32_t
gbm_pvr_bo_get_offset(struct gbm_bo *_bo, int plane)
{
   return 0;
}

static uint64_t
gbm_pvr_bo_get_modifier(struct gbm_bo *_bo)
{
   return DRM_FORMAT_MOD_LINEAR;
}

static struct gbm_bo *
gbm_pvr_bo_import(struct gbm_device *gbm,
                  uint32_t type, void *buffer, uint32_t usage)
{
   switch (type) {
   case GBM_BO_IMPORT_WL_BUFFER:
      fprintf(stderr, "gbm_pvr_bo_import: GBM_BO_IMPORT_WL_BUFFER is not implemented\n");
      errno = ENOSYS;
      return NULL;
   case GBM_BO_IMPORT_EGL_IMAGE:
      fprintf(stderr, "gbm_pvr_bo_import: GBM_BO_IMPORT_EGL_IMAGE is not implemented\n");
      errno = ENOSYS;
      return NULL;
   case GBM_BO_IMPORT_FD:
      fprintf(stderr, "gbm_pvr_bo_import: GBM_BO_IMPORT_FD is not supported\n");
      errno = ENOSYS;
      return NULL;
   case GBM_BO_IMPORT_FD_MODIFIER:
      fprintf(stderr, "gbm_pvr_bo_import: GBM_BO_IMPORT_FD_MODIFIER is not supported\n");
      errno = ENOSYS;
      return NULL;
   default:
      fprintf(stderr, "gbm_pvr_bo_import: is not supported type: %x\n", type);
      errno = ENOSYS;
      return NULL;
   }
}

static struct gbm_bo *
gbm_pvr_bo_create(struct gbm_device *gbm,
                  uint32_t width, uint32_t height,
                  uint32_t format, uint32_t usage,
                  const uint64_t *modifiers,
                  const unsigned int count)
{
   struct gbm_pvr_device *pvr = gbm_pvr_device(gbm);
   struct gbm_pvr_bo *bo;
   int is_cursor, is_scanout;

   if (modifiers) {
      fprintf(stderr, "gbm_pvr_bo_create: modifiers are not supported!\n");
      errno = ENOSYS;
      return NULL;
   }

   assert(!(usage && count));

   is_cursor = (usage & GBM_BO_USE_CURSOR) != 0 &&
      format == GBM_FORMAT_ARGB8888;
   is_scanout = (usage & GBM_BO_USE_SCANOUT) != 0 &&
      (format == GBM_FORMAT_XRGB8888 || format == GBM_FORMAT_ARGB8888);
   if (!is_cursor && !is_scanout) {
      errno = EINVAL;
      return NULL;
   }

   bo = calloc(1, sizeof *bo);
   if (bo == NULL)
      return NULL;

   bo->base.gbm = gbm;
   bo->base.width = width;
   bo->base.height = height;
   bo->base.stride = ALIGN(width, 32) * 4;
   bo->base.format = gbm_pvr_format_canonicalize(format);
   bo->bo = omap_bo_new(pvr->omap_dev,
                        PAGE_ALIGN(bo->base.stride * bo->base.height),
                        OMAP_BO_SCANOUT | OMAP_BO_WC);
   if (!bo->bo) {
      free(bo);
      return NULL;
   }
   bo->base.handle.u32 = omap_bo_handle(bo->bo);

   return &bo->base;
}

static void
gbm_pvr_bo_destroy(struct gbm_bo *_bo)
{
   struct gbm_pvr_bo *bo = gbm_pvr_bo(_bo);

   omap_bo_del(bo->bo);

   free(bo);
}

static void *
gbm_pvr_bo_map(struct gbm_bo *_bo,
              uint32_t x, uint32_t y,
              uint32_t width, uint32_t height,
              uint32_t flags, uint32_t *stride, void **map_data)
{
   struct gbm_pvr_bo *bo = gbm_pvr_bo(_bo);

   void *map = omap_bo_map(bo->bo);
   if (map) {
      *map_data = (char *)map + (bo->base.stride * y) + (x * 4);
      *stride = bo->base.stride;
      return *map_data;
   } else {
      errno = ENOSYS;
      return NULL;
   }
}

static void
gbm_pvr_bo_unmap(struct gbm_bo *_bo, void *map_data)
{
  /* nothing */
}

static void
gbm_pvr_surface_destroy(struct gbm_surface *_surf)
{
   struct gbm_pvr_surface *surf = gbm_pvr_surface(_surf);

   for (int i = 0; i < PVR_NUM_BACK_BUFFERS; i++) {
      if (surf->back_buffers[i]) {
         gbm_bo_destroy(surf->back_buffers[i]);
      }
   }

   free(surf);
}

static struct gbm_surface *
gbm_pvr_surface_create(struct gbm_device *gbm,
                       uint32_t width, uint32_t height,
                       uint32_t format, uint32_t flags,
                       const uint64_t *modifiers, const unsigned count)
{
   struct gbm_pvr_device *dri = gbm_pvr_device(gbm);
   struct gbm_pvr_surface *surf;

   if (modifiers) {
      fprintf(stderr, "gbm_pvr_surface_create: modifiers are not supported!\n");
      errno = ENOSYS;
      return NULL;
   }

   surf = calloc(1, sizeof *surf);
   if (surf == NULL) {
      errno = ENOMEM;
      return NULL;
   }

   surf->base.gbm = gbm;
   surf->base.width = width;
   surf->base.height = height;
   surf->base.format = gbm_pvr_format_canonicalize(format);
   surf->base.flags = flags;
   for (int i = 0; i < PVR_NUM_BACK_BUFFERS; i++) {
      surf->back_buffers[i] = gbm_pvr_bo_create(gbm, width, height, format, flags, NULL, 0);
      if (!surf->back_buffers[i]) {
         fprintf(stderr, "gbm_pvr_surface_create: gbm_bo_create() failed!\n");
         errno = ENOMEM;
         gbm_pvr_surface_destroy((struct gbm_surface *)surf);
         return NULL;
      }
   }

   return &surf->base;
}

static struct gbm_bo *
gbm_pvr_surface_lock_front_buffer(struct gbm_surface *_surf)
{
   struct gbm_pvr_surface *surf = gbm_pvr_surface(_surf);

   int index = (surf->current_back_buffer + 1) % PVR_NUM_BACK_BUFFERS;

   return surf->back_buffers[index];
}

static void
gbm_pvr_surface_release_buffer(struct gbm_surface *surface,
                               struct gbm_bo *bo)
{
}

static int
gbm_pvr_surface_has_free_buffers(struct gbm_surface *surface)
{
   return 0;
}

static void
gbm_pvr_device_destroy(struct gbm_device *gbm)
{
   struct gbm_pvr_device *pvr = gbm_pvr_device(gbm);

   omap_device_del(pvr->omap_dev);

   free(pvr);
}

extern void PVRDRMSetFD(int fd);

static struct gbm_device *
gbm_pvr_device_create(int fd)
{
   struct gbm_pvr_device *pvr;
   int ret;

   pvr = calloc(1, sizeof *pvr);
   if (!pvr)
      return NULL;

   pvr->base.fd = fd;
   pvr->base.destroy = gbm_pvr_device_destroy;
   pvr->base.is_format_supported = gbm_pvr_is_format_supported;
   pvr->base.get_format_modifier_plane_count = gbm_pvr_get_format_modifier_plane_count;
   pvr->base.bo_create = gbm_pvr_bo_create;
   pvr->base.bo_destroy = gbm_pvr_bo_destroy;
   pvr->base.bo_import = gbm_pvr_bo_import;
   pvr->base.bo_map = gbm_pvr_bo_map;
   pvr->base.bo_unmap = gbm_pvr_bo_unmap;
   pvr->base.bo_write = gbm_pvr_bo_write;
   pvr->base.bo_get_fd = gbm_pvr_bo_get_fd;
   pvr->base.bo_get_planes = gbm_pvr_bo_get_planes;
   pvr->base.bo_get_handle = gbm_pvr_bo_get_handle_for_plane;
   pvr->base.bo_get_stride = gbm_pvr_bo_get_stride;
   pvr->base.bo_get_offset = gbm_pvr_bo_get_offset;
   pvr->base.bo_get_modifier = gbm_pvr_bo_get_modifier;
   pvr->base.surface_create = gbm_pvr_surface_create;
   pvr->base.surface_destroy = gbm_pvr_surface_destroy;
   pvr->base.surface_lock_front_buffer = gbm_pvr_surface_lock_front_buffer;
   pvr->base.surface_release_buffer = gbm_pvr_surface_release_buffer;
   pvr->base.surface_has_free_buffers = gbm_pvr_surface_has_free_buffers;
   pvr->omap_dev = omap_device_new(fd);

   PVRDRMSetFD(fd);

   return &pvr->base;
}

struct gbm_backend gbm_backend = {
   .backend_name = "pvr",
   .create_device = gbm_pvr_device_create,
};
