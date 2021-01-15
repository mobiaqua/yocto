/****************************************************************************
**
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser General Public
** License version 2.1 as published by the Free Software Foundation and
** appearing in the file LICENSE.LGPL included in the packaging of this
** file. Please review the following information to ensure the GNU Lesser
** General Public License version 2.1 requirements will be met:
** http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU General
** Public License version 3.0 as published by the Free Software Foundation
** and appearing in the file LICENSE.GPL included in the packaging of this
** file. Please review the following information to ensure the GNU General
** Public License version 3.0 requirements will be met:
** http://www.gnu.org/copyleft/gpl.html.
**
****************************************************************************/

#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#include "pvrws_GBM.h"

#define WSEGL_UNUSED(x) (void)x;

static int refCount = 0;

static WSEGLCaps const wseglDisplayCaps[] = {
    { WSEGL_CAP_WINDOWS_USE_HW_SYNC, 0 },
    { WSEGL_CAP_MIN_SWAP_INTERVAL, 1 },
    { WSEGL_CAP_MAX_SWAP_INTERVAL, 1 },
    { WSEGL_NO_CAPS, 0 }
};

/* Configuration information for the display */
static WSEGLConfig wseglDisplayConfigs[] = {
    { WSEGL_DRAWABLE_WINDOW, WSEGL_PIXELFORMAT_ARGB8888, WSEGL_FALSE, 0, 0, 0, WSEGL_OPAQUE, 0 },
    { WSEGL_DRAWABLE_PIXMAP, WSEGL_PIXELFORMAT_ARGB8888, WSEGL_FALSE, 0, 0, 0, WSEGL_OPAQUE, 0 },
    { WSEGL_NO_DRAWABLE, 0, 0, 0, 0, 0, 0, 0 }
};

static WSEGLError wseglIsDisplayValid(NativeDisplayType nativeDisplay)
{
    /* Bail out if the native display is incorrect */
    if (!nativeDisplay)
        return WSEGL_BAD_NATIVE_DISPLAY;

    return WSEGL_SUCCESS;
}

static WSEGLError wseglInitializeDisplay(NativeDisplayType nativeDisplay, WSEGLDisplayHandle *_display,
     const WSEGLCaps **caps, WSEGLConfig **configs)
{
    /* Bail out if the native display is incorrect */
    if (!nativeDisplay)
        return WSEGL_BAD_NATIVE_DISPLAY;

    if (refCount > 0)
        return WSEGL_CANNOT_INITIALISE;

    struct GBMDisplay *display = calloc(sizeof(struct GBMDisplay), 1);
    if (!display)
        return WSEGL_OUT_OF_MEMORY;

    if (PVR2DCreateDeviceContext(0, &display->pvr2dContextHandle, 0) != PVR2D_OK)
    {
        free(display);
        return WSEGL_CANNOT_INITIALISE;
    }

    *_display = display;
    *caps = wseglDisplayCaps;
    *configs = wseglDisplayConfigs;

    refCount++;

    return WSEGL_SUCCESS;
}

/* Close the WSEGL display */
static WSEGLError wseglCloseDisplay(WSEGLDisplayHandle _display)
{
    struct GBMDisplay *display = (struct GBMDisplay *)_display;
    if (!display)
        return WSEGL_SUCCESS;

    PVR2DDestroyDeviceContext(display->pvr2dContextHandle);

    refCount--;

    memset(display, 0, sizeof(struct GBMDisplay));

    return WSEGL_SUCCESS;
}

/* Create the WSEGL drawable version of a native window */
static WSEGLError wseglCreateWindowDrawable
    (WSEGLDisplayHandle display, WSEGLConfig *config,
     WSEGLDrawableHandle *_drawable, NativeWindowType nativeWindow,
     WSEGLRotationAngle *rotationAngle)
{
    WSEGL_UNUSED(config);

    if (!nativeWindow)
        return WSEGL_BAD_NATIVE_WINDOW;

    struct GBMDrawable *drawable = calloc(sizeof(struct GBMDrawable), 1);
    if (!drawable)
        return WSEGL_OUT_OF_MEMORY;

    drawable->type = GBM_DRAWABLE_WINDOW;
    drawable->display = (struct GBMDisplay *)display;
    drawable->backBuffers = calloc(sizeof(struct GBMBuffer), PVR_NUM_BACK_BUFFERS);
    drawable->numBackBuffers = PVR_NUM_BACK_BUFFERS;
    drawable->handle.window = (struct gbm_pvr_surface *)nativeWindow;
    drawable->width = drawable->handle.window->base.width;
    drawable->height = drawable->handle.window->base.height;
    drawable->stride = ALIGN(drawable->width, 32);

    for (int i = 0; i < PVR_NUM_BACK_BUFFERS; i++)
    {
        if (PVR2DImportGEM(drawable->display->pvr2dContextHandle,
                           drawable->handle.window->back_buffers[i]->handle.u32,
                           &drawable->backBuffers[i].pvr2dMemInfo))
        {
            free(drawable);
            return WSEGL_OUT_OF_MEMORY;
        }
    }

    *_drawable = drawable;
    *rotationAngle = WSEGL_ROTATE_0;

    return WSEGL_SUCCESS;
}

/* Create the WSEGL drawable version of a native pixmap */
static WSEGLError wseglCreatePixmapDrawable(WSEGLDisplayHandle display, WSEGLConfig *config,
     WSEGLDrawableHandle *_drawable, NativePixmapType nativePixmap, WSEGLRotationAngle *rotationAngle)
{
    WSEGL_UNUSED(config);

    if (!nativePixmap)
        return WSEGL_BAD_NATIVE_PIXMAP;

    struct GBMDrawable *drawable = calloc(sizeof(struct GBMDrawable), 1);
    if (!drawable)
        return WSEGL_OUT_OF_MEMORY;

    drawable->type = GBM_DRAWABLE_PIXMAP;
    drawable->display = (struct GBMDisplay *)display;
    drawable->backBuffers = calloc(sizeof(struct GBMBuffer), 1);
    drawable->numBackBuffers = 1;
    drawable->handle.pixmap = (struct gbm_pvr_bo *)nativePixmap;
    drawable->width = drawable->handle.pixmap->base.width;
    drawable->height = drawable->handle.pixmap->base.height;
    drawable->stride = ALIGN(drawable->width, 32);

    if (PVR2DImportGEM(drawable->display->pvr2dContextHandle,
                       drawable->handle.pixmap->base.handle.u32,
                       &drawable->backBuffers[0].pvr2dMemInfo))
    {
        free(drawable);
        return WSEGL_OUT_OF_MEMORY;
    }

    *_drawable = drawable;
    *rotationAngle = WSEGL_ROTATE_0;

    return WSEGL_SUCCESS;
}

/* Delete a specific drawable */
static WSEGLError wseglDeleteDrawable(WSEGLDrawableHandle _drawable)
{
    struct GBMDrawable *drawable = (struct GBMDrawable *)_drawable;
    if (!drawable)
        return WSEGL_SUCCESS;

    struct GBMDisplay *display = drawable->display;
    for (int i = 0; i < drawable->numBackBuffers; i++)
    {
        PVR2DMemFree(display->pvr2dContextHandle, drawable->backBuffers[i].pvr2dMemInfo);
    }
    free(drawable->backBuffers);
    free(_drawable);

    return WSEGL_SUCCESS;
}

/* Swap the contents of a drawable to the screen */
static WSEGLError wseglSwapDrawable(WSEGLDrawableHandle _drawable, unsigned long data)
{
    WSEGL_UNUSED(data);
    struct GBMDrawable *drawable = (struct GBMDrawable *)_drawable;

    if (drawable->type == GBM_DRAWABLE_WINDOW)
    {
        drawable->handle.window->current_back_buffer =
            (drawable->handle.window->current_back_buffer + 1) % PVR_NUM_BACK_BUFFERS;
    }
    else
        return WSEGL_BAD_DRAWABLE;

    return WSEGL_SUCCESS;
}

/* Set the swap interval of a window drawable */
static WSEGLError wseglSwapControlInterval(WSEGLDrawableHandle drawable, unsigned long interval)
{
    WSEGL_UNUSED(drawable);
    WSEGL_UNUSED(interval);

    return WSEGL_SUCCESS;
}

/* Flush native rendering requests on a drawable */
static WSEGLError wseglWaitNative(WSEGLDrawableHandle drawable, unsigned long engine)
{
    WSEGL_UNUSED(drawable);
    WSEGL_UNUSED(engine);

    return WSEGL_SUCCESS;
}

/* Copy color data from a drawable to a native pixmap */
static WSEGLError wseglCopyFromDrawable(WSEGLDrawableHandle _drawable, NativePixmapType nativePixmap)
{
    WSEGL_UNUSED(_drawable);
    WSEGL_UNUSED(nativePixmap);

    return WSEGL_SUCCESS;
}

/* Copy color data from a PBuffer to a native pixmap */
static WSEGLError wseglCopyFromPBuffer(void *address, unsigned long width, unsigned long height,
     unsigned long stride, WSEGLPixelFormat format, NativePixmapType nativePixmap)
{
    WSEGL_UNUSED(address);
    WSEGL_UNUSED(width);
    WSEGL_UNUSED(height);
    WSEGL_UNUSED(stride);
    WSEGL_UNUSED(format);
    WSEGL_UNUSED(nativePixmap);

    return WSEGL_SUCCESS;
}

static WSEGLError wseglGetDrawableParameters(WSEGLDrawableHandle _drawable,
     WSEGLDrawableParams *sourceParams,
     WSEGLDrawableParams *renderParams)
{
    struct GBMDrawable *drawable = (struct GBMDrawable *)_drawable;
    PVR2DMEMINFO *source, *render;
    WSEGLPixelFormat pixelFormat;
    int sourceIdx, renderIdx;

    sourceIdx = renderIdx = 0;
    if (drawable->type == GBM_DRAWABLE_WINDOW)
    {
        renderIdx = drawable->handle.window->current_back_buffer;
        sourceIdx = (drawable->handle.window->current_back_buffer + 1) % PVR_NUM_BACK_BUFFERS;
    }

    source = drawable->backBuffers[sourceIdx].pvr2dMemInfo;
    sourceParams->ui32Width = drawable->width;
    sourceParams->ui32Height = drawable->height;
    sourceParams->ui32Stride = drawable->stride;
    sourceParams->ePixelFormat = WSEGL_PIXELFORMAT_ARGB8888;
    sourceParams->eRotationAngle = WSEGL_ROTATE_0;
    sourceParams->pvLinearAddress = source->pBase;
    sourceParams->ui32HWAddress = source->ui32DevAddr;
    sourceParams->hPrivateData = source->hPrivateData;

    render = drawable->backBuffers[renderIdx].pvr2dMemInfo;
    renderParams->ui32Width = drawable->width;
    renderParams->ui32Height = drawable->height;
    renderParams->ui32Stride = drawable->stride;
    renderParams->ePixelFormat = WSEGL_PIXELFORMAT_ARGB8888;
    renderParams->eRotationAngle = WSEGL_ROTATE_0;
    renderParams->pvLinearAddress = render->pBase;
    renderParams->ui32HWAddress = render->ui32DevAddr;
    renderParams->hPrivateData = render->hPrivateData;

    return WSEGL_SUCCESS;
}

static WSEGLError wseglConnectDrawable(WSEGLDrawableHandle _drawable)
{
    WSEGL_UNUSED(_drawable);

    return WSEGL_SUCCESS;
}

static WSEGLError wseglDisconnectDrawable(WSEGLDrawableHandle _drawable)
{
    WSEGL_UNUSED(_drawable);

    return WSEGL_SUCCESS;
}

static WSEGLError wseglPresentDrawable(WSEGLDrawableHandle _drawable,
    unsigned int data, int x, int y, int width, int height)
{
    WSEGL_UNUSED(_drawable);
    WSEGL_UNUSED(data);
    WSEGL_UNUSED(x);
    WSEGL_UNUSED(y);
    WSEGL_UNUSED(width);
    WSEGL_UNUSED(height);

    return WSEGL_SUCCESS;
}

static WSEGL_FunctionTable const wseglFunctions = {
    WSEGL_VERSION,
    wseglIsDisplayValid,
    wseglInitializeDisplay,
    wseglCloseDisplay,
    wseglCreateWindowDrawable,
    wseglCreatePixmapDrawable,
    wseglDeleteDrawable,
    wseglSwapDrawable,
    wseglSwapControlInterval,
    wseglWaitNative,
    wseglCopyFromDrawable,
    wseglCopyFromPBuffer,
    wseglGetDrawableParameters,
    wseglConnectDrawable,
    wseglDisconnectDrawable,
    wseglPresentDrawable
};

const WSEGL_FunctionTable *WSEGL_GetFunctionTablePointer(void)
{
    return &wseglFunctions;
}
