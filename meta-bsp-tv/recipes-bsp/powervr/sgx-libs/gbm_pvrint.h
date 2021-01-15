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

#ifndef _GBM_PVR_INTERNAL_H_
#define _GBM_PVR_INTERNAL_H_

#include <gbmint.h>

#define PVR_NUM_BACK_BUFFERS 3

#define ALIGN(val, align) (((val) + (align) - 1) & ~((align) - 1))

#define PAGE_ALIGN(addr)  ALIGN(addr, 4096)

struct omap_device;
struct omap_bo;

struct gbm_pvr_device {
   struct gbm_device base;

   struct omap_device *omap_dev;
};

struct gbm_pvr_bo {
   struct gbm_bo base;

   struct omap_bo *bo;
};

struct gbm_pvr_surface {
   struct gbm_surface base;

   int current_back_buffer;
   struct gbm_bo *back_buffers[PVR_NUM_BACK_BUFFERS];
};

static inline struct gbm_pvr_device *
gbm_pvr_device(struct gbm_device *gbm)
{
   return (struct gbm_pvr_device *) gbm;
}

static inline struct gbm_pvr_bo *
gbm_pvr_bo(struct gbm_bo *bo)
{
   return (struct gbm_pvr_bo *) bo;
}

static inline struct gbm_pvr_surface *
gbm_pvr_surface(struct gbm_surface *surface)
{
   return (struct gbm_pvr_surface *) surface;
}

#endif
