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
#include <fcntl.h>
#include <drm.h>
#include <xf86drm.h>
#include <drm_fourcc.h>
#include <sys/mman.h>
#include <omap_drm.h>

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
   struct drm_mode_map_dumb mreq;

   mreq.handle = bo->base.v0.handle.u32;
   mreq.pad = 0;
   mreq.offset = 0;
   if (drmIoctl(bo->base.gbm->v0.fd, DRM_IOCTL_MODE_MAP_DUMB, &mreq) == -1)
      return -1;

   void *map = mmap(0, bo->size, PROT_READ | PROT_WRITE, MAP_SHARED, bo->base.gbm->v0.fd, mreq.offset);
   if (!map)
      return -1;

   memcpy(map, buf, count);

   if (munmap(map, bo->size) == -1)
      return -1;

   return 0;
}

static int
gbm_pvr_bo_get_fd(struct gbm_bo *_bo)
{
   struct gbm_pvr_bo *bo = gbm_pvr_bo(_bo);
   int fd;

   if (drmPrimeHandleToFD(bo->base.gbm->v0.fd, bo->base.v0.handle.u32, DRM_CLOEXEC, &fd))
      return -1;

   return fd;
}

static int
gbm_pvr_bo_get_planes(struct gbm_bo *_bo)
{
   return 1;
}

static union gbm_bo_handle
gbm_pvr_bo_get_handle_for_plane(struct gbm_bo *_bo, int plane)
{
   return _bo->v0.handle;
}

static uint32_t
gbm_pvr_bo_get_stride(struct gbm_bo *_bo, int plane)
{
   return _bo->v0.stride;
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
   struct gbm_pvr_device *pvr = gbm_pvr_device(gbm);
   struct gbm_pvr_bo *bo;
   struct gbm_import_fd_data *gbm_dmabuf;
   struct drm_prime_handle dreq;
   struct drm_omap_gem_info oreq;
   int ret, is_cursor, is_scanout;

   switch (type) {
   case GBM_BO_IMPORT_WL_BUFFER:
      fprintf(stderr, "gbm_pvr_bo_import: GBM_BO_IMPORT_WL_BUFFER is not implemented\n");
      errno = ENOSYS;
      return NULL;
   case GBM_BO_IMPORT_EGL_IMAGE:
      fprintf(stderr, "gbm_pvr_bo_import: GBM_BO_IMPORT_EGL_IMAGE is not implemented\n");
      errno = ENOSYS;
      return NULL;
   case GBM_BO_IMPORT_FD: {
      gbm_dmabuf = buffer;
      is_cursor = (usage & GBM_BO_USE_CURSOR) != 0 &&
         gbm_dmabuf->format == GBM_FORMAT_ARGB8888;
      is_scanout = (usage & GBM_BO_USE_SCANOUT) != 0 &&
         (gbm_dmabuf->format == GBM_FORMAT_XRGB8888 || gbm_dmabuf->format == GBM_FORMAT_ARGB8888);
      if (is_cursor || !is_scanout) {
         errno = EINVAL;
         return NULL;
      }
      bo = calloc(1, sizeof *bo);
      if (bo == NULL) {
         errno = ENOMEM;
         return NULL;
      }

      ret = drmPrimeFDToHandle(gbm->v0.fd, gbm_dmabuf->fd, &bo->base.v0.handle.u32);
      if (ret)  {
         free(bo);
         errno = ENOSYS;
         return NULL;
      }

      bo->base.gbm = gbm;
      bo->base.v0.width = gbm_dmabuf->width;
      bo->base.v0.height = gbm_dmabuf->height;
      bo->base.v0.stride = gbm_dmabuf->stride;
      bo->base.v0.format = gbm_pvr_format_canonicalize(gbm_dmabuf->format);
      bo->base.v0.handle.u32 = dreq.handle;

      oreq.handle = dreq.handle;
      ret = drmCommandWriteRead(gbm->v0.fd, DRM_OMAP_GEM_INFO, &oreq, sizeof(oreq));
      if (ret)  {
         free(bo);
         errno = ENOSYS;
         return NULL;
      }
      bo->size = oreq.size;

      return (struct gbm_bo *)bo;
   }
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
   struct gbm_pvr_bo *bo;
   int is_cursor, is_scanout;
   struct drm_mode_create_dumb dreq;

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
   if (is_cursor || !is_scanout) {
      errno = EINVAL;
      return NULL;
   }

   bo = calloc(1, sizeof *bo);
   if (bo == NULL) {
       errno = ENOMEM;
      return NULL;
   }

   bo->base.gbm = gbm;
   bo->base.v0.width = width;
   bo->base.v0.height = height;
   bo->base.v0.format = gbm_pvr_format_canonicalize(format);

   dreq.height = height;
   dreq.width = ALIGN(width, 32);
   dreq.bpp = 32;
   dreq.flags = 0;
   dreq.handle = 0;
   dreq.pitch = 0;
   dreq.size = 0;
   int ret = drmIoctl(bo->base.gbm->v0.fd, DRM_IOCTL_MODE_CREATE_DUMB, &dreq);
   if (ret == -1)  {
      free(bo);
      return NULL;
   }

   bo->base.v0.stride = dreq.pitch;
   bo->base.v0.handle.u32 = dreq.handle;
   bo->size = dreq.size;

   return &bo->base;
}

static void
gbm_pvr_bo_destroy(struct gbm_bo *_bo)
{
   struct gbm_pvr_bo *bo = gbm_pvr_bo(_bo);
   struct drm_mode_destroy_dumb dreq;

   if (bo->mmap)
      munmap(bo->mmap, bo->size);

   dreq.handle = bo->base.v0.handle.u32;
   drmIoctl(bo->base.gbm->v0.fd, DRM_IOCTL_MODE_DESTROY_DUMB, &dreq);

   free(bo);
}

static void *
gbm_pvr_bo_map(struct gbm_bo *_bo,
              uint32_t x, uint32_t y,
              uint32_t width, uint32_t height,
              uint32_t flags, uint32_t *stride, void **map_data)
{
   struct gbm_pvr_bo *bo = gbm_pvr_bo(_bo);
   struct drm_mode_map_dumb mreq;

   mreq.handle = bo->base.v0.handle.u32;
   mreq.pad = 0;
   mreq.offset = 0;
   if (drmIoctl(bo->base.gbm->v0.fd, DRM_IOCTL_MODE_MAP_DUMB, &mreq) == -1)
      return NULL;

   bo->mmap = mmap(0, bo->size, PROT_READ | PROT_WRITE, MAP_SHARED, bo->base.gbm->v0.fd, mreq.offset);
   if (!bo->mmap)
      return NULL;

   *map_data = (char *)bo->mmap + (bo->base.v0.stride * y) + (x * 4);
   *stride = bo->base.v0.stride;

   return *map_data;
}

static void
gbm_pvr_bo_unmap(struct gbm_bo *_bo, void *map_data)
{
   struct gbm_pvr_bo *bo = gbm_pvr_bo(_bo);

   munmap(bo->mmap, bo->size);
   bo->mmap = NULL;
}

static void
gbm_pvr_surface_destroy(struct gbm_surface *_surf)
{
   struct gbm_pvr_surface *surf = gbm_pvr_surface(_surf);

   for (int i = 0; i < PVR_NUM_BACK_BUFFERS; i++) {
      if (surf->back_buffers[i]) {
         gbm_pvr_bo_destroy((struct gbm_bo *)surf->back_buffers[i]);
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
   surf->base.v0.width = width;
   surf->base.v0.height = height;
   surf->base.v0.format = gbm_pvr_format_canonicalize(format);
   surf->base.v0.flags = flags;
   for (int i = 0; i < PVR_NUM_BACK_BUFFERS; i++) {
      surf->back_buffers[i] = (struct gbm_pvr_bo *)gbm_pvr_bo_create(gbm, width, height, format, flags, NULL, 0);
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

   return (struct gbm_bo *)surf->back_buffers[index];
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

   free(pvr);
}

static struct gbm_device *
gbm_pvr_device_create(int fd, uint32_t gbm_backend_version)
{
   struct gbm_pvr_device *pvr;
   drmVersionPtr version;
   uint64_t cap = 0;

   version = drmGetVersion(fd);
   if (!version || strcmp("omapdrm", version->name))
   {
      drmFreeVersion(version);
      return NULL;
   }
   drmFreeVersion(version);

   if (drmGetCap(fd, DRM_CAP_DUMB_BUFFER, &cap) || (cap == 0))
      return NULL;

   pvr = calloc(1, sizeof *pvr);
   if (!pvr)
      return NULL;

   pvr->base.v0.fd = fd;
   pvr->base.v0.backend_version = gbm_backend_version;
   pvr->base.v0.destroy = gbm_pvr_device_destroy;
   pvr->base.v0.is_format_supported = gbm_pvr_is_format_supported;
   pvr->base.v0.get_format_modifier_plane_count = gbm_pvr_get_format_modifier_plane_count;
   pvr->base.v0.bo_create = gbm_pvr_bo_create;
   pvr->base.v0.bo_destroy = gbm_pvr_bo_destroy;
   pvr->base.v0.bo_import = gbm_pvr_bo_import;
   pvr->base.v0.bo_map = gbm_pvr_bo_map;
   pvr->base.v0.bo_unmap = gbm_pvr_bo_unmap;
   pvr->base.v0.bo_write = gbm_pvr_bo_write;
   pvr->base.v0.bo_get_fd = gbm_pvr_bo_get_fd;
   pvr->base.v0.bo_get_planes = gbm_pvr_bo_get_planes;
   pvr->base.v0.bo_get_handle = gbm_pvr_bo_get_handle_for_plane;
   pvr->base.v0.bo_get_stride = gbm_pvr_bo_get_stride;
   pvr->base.v0.bo_get_offset = gbm_pvr_bo_get_offset;
   pvr->base.v0.bo_get_modifier = gbm_pvr_bo_get_modifier;
   pvr->base.v0.surface_create = gbm_pvr_surface_create;
   pvr->base.v0.surface_destroy = gbm_pvr_surface_destroy;
   pvr->base.v0.surface_lock_front_buffer = gbm_pvr_surface_lock_front_buffer;
   pvr->base.v0.surface_release_buffer = gbm_pvr_surface_release_buffer;
   pvr->base.v0.surface_has_free_buffers = gbm_pvr_surface_has_free_buffers;

   pvr->base.v0.name = "omapdrm";

   return &pvr->base;
}

static struct gbm_backend gbm_pvr_backend = {
   .v0.backend_version = GBM_BACKEND_ABI_VERSION,
   .v0.backend_name = "pvr",
   .v0.create_device = gbm_pvr_device_create,
};

struct gbm_backend *gbmint_get_backend(struct gbm_core *gbm_core) {
   return &gbm_pvr_backend;
}
