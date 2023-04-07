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
*****************************************************************************
**
** Functions: InitialiseServices, DeInitialiseServices, WaitForOpsComplete
** are subject to the MIT license as set out below.
**
** Copyright (c) Imagination Technologies Ltd. All Rights Reserved
** Copyright (c) 2017 Texas Instruments Incorporated.
**
** Permission is hereby granted, free of charge, to any person obtaining a copy
** of this software and associated documentation files (the "Software"), to deal
** in the Software without restriction, including without limitation the rights
** to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
** copies of the Software, and to permit persons to whom the Software is
** furnished to do so, subject to the following conditions:
**
** The above copyright notice and this permission notice shall be included in
** all copies or substantial portions of the Software.
**
** THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
** IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
** FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
** AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
** LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
** OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
** THE SOFTWARE.
**
****************************************************************************/

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <unistd.h>
#include <dlfcn.h>

#include "pvrws_GBM.h"

#define WSEGL_UNUSED(x) (void)x;

#define SGX_GENERAL_HEAP_ID 0

static int refCount = 0;

typedef PVRSRV_ERROR IMG_CALLCONV PVRSRVConnect_Type(PVRSRV_CONNECTION **ppsConnection, IMG_UINT32 ui32SrvFlags);
typedef PVRSRV_ERROR IMG_CALLCONV PVRSRVDisconnect_Type(IMG_HANDLE hConnection);
typedef PVRSRV_ERROR IMG_CALLCONV PVRSRVEnumerateDevices_Type(IMG_CONST PVRSRV_CONNECTION *psConnection,
                                                              IMG_UINT32 *puiNumDevices,
                                                              PVRSRV_DEVICE_IDENTIFIER *puiDevIDs);
typedef PVRSRV_ERROR IMG_CALLCONV PVRSRVAcquireDeviceData_Type(IMG_CONST PVRSRV_CONNECTION *psConnection,
                                                               IMG_UINT32 uiDevIndex,
                                                               PVRSRV_DEV_DATA *psDevData,
                                                               PVRSRV_DEVICE_TYPE eDeviceType);
typedef PVRSRV_ERROR IMG_CALLCONV PVRSRVGetMiscInfo_Type(IMG_HANDLE hConnection, PVRSRV_MISC_INFO *psMiscInfo);
typedef PVRSRV_ERROR IMG_CALLCONV PVRSRVReleaseMiscInfo_Type(IMG_HANDLE hConnection, PVRSRV_MISC_INFO *psMiscInfo);
typedef PVRSRV_ERROR IMG_CALLCONV PVRSRVCreateDeviceMemContext_Type(IMG_CONST PVRSRV_DEV_DATA *psDevData,
                                                                    IMG_HANDLE *phDevMemContext,
                                                                    IMG_UINT32 *pui32SharedHeapCount,
                                                                    PVRSRV_HEAP_INFO *psHeapInfo);
typedef PVRSRV_ERROR IMG_CALLCONV PVRSRVDestroyDeviceMemContext_Type(IMG_CONST PVRSRV_DEV_DATA *psDevData,
                                                                     IMG_HANDLE hDevMemContext);
typedef PVRSRV_ERROR PVRSRVEventObjectWait_Type(const PVRSRV_CONNECTION *psConnection, IMG_HANDLE hOSEvent);
typedef PVRSRV_ERROR PVRSRVMapFullDmaBuf_Type(const PVRSRV_DEV_DATA *psDevData,
                                              const IMG_HANDLE hDevMemHeap,
                                              const IMG_UINT32 ui32Attribs,
                                              const IMG_INT iDmaBufFD,
                                              PVRSRV_CLIENT_MEM_INFO **ppsMemInfo);
typedef PVRSRV_ERROR PVRSRVUnmapDmaBuf_Type(const PVRSRV_DEV_DATA *psDevData, PVRSRV_CLIENT_MEM_INFO *psMemInfo);
typedef const IMG_CHAR *PVRSRVGetErrorString_Type(PVRSRV_ERROR eError);

static PVRSRVConnect_Type *PVRSRVConnectFunc;
static PVRSRVDisconnect_Type *PVRSRVDisconnectFunc;
static PVRSRVEnumerateDevices_Type *PVRSRVEnumerateDevicesFunc;
static PVRSRVAcquireDeviceData_Type *PVRSRVAcquireDeviceDataFunc;
static PVRSRVGetMiscInfo_Type *PVRSRVGetMiscInfoFunc;
static PVRSRVReleaseMiscInfo_Type *PVRSRVReleaseMiscInfoFunc;
static PVRSRVCreateDeviceMemContext_Type *PVRSRVCreateDeviceMemContextFunc;
static PVRSRVDestroyDeviceMemContext_Type *PVRSRVDestroyDeviceMemContextFunc;
static PVRSRVEventObjectWait_Type *PVRSRVEventObjectWaitFunc;
static PVRSRVMapFullDmaBuf_Type *PVRSRVMapFullDmaBufFunc;
static PVRSRVUnmapDmaBuf_Type *PVRSRVUnmapDmaBufFunc;
static PVRSRVGetErrorString_Type *PVRSRVGetErrorStringFunc;

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

static bool InitialiseServices(struct GBMDisplay *display)
{
    PVRSRV_ERROR err;
    PVRSRV_DEVICE_IDENTIFIER devIds[PVRSRV_MAX_DEVICES];
    IMG_UINT32 numDevices;
    PVRSRV_HEAP_INFO heapInfo[PVRSRV_MAX_CLIENT_HEAPS];
    IMG_UINT32 heapCount;
    int i;

    err = PVRSRVConnectFunc(&display->services, 0);
    if (err != PVRSRV_OK)
    {
        fprintf(stderr, "PVRSRVConnect failed: %s\n", PVRSRVGetErrorStringFunc(err));
        return 0;
    }

    err = PVRSRVEnumerateDevicesFunc(display->services, &numDevices, devIds);
    if (err != PVRSRV_OK)
    {
        fprintf(stderr, "PVRSRVEnumerateDevices failed: %s\n", PVRSRVGetErrorStringFunc(err));
        goto DISCONNECT;
    }

    if (numDevices == 0)
    {
        fprintf(stderr, "PVRSRVEnumerateDevices failed to find devices\n");
        goto DISCONNECT;
    }
    for (i = 0; i < numDevices; i++)
    {
        if (devIds[i].eDeviceClass == PVRSRV_DEVICE_CLASS_3D)
            break;
    }
    if (i == numDevices)
    {
        fprintf(stderr, "PVRSRVEnumerateDevices failed to find 3D device\n");
        goto DISCONNECT;
    }

    err = PVRSRVAcquireDeviceDataFunc(display->services, devIds[i].ui32DeviceIndex, &display->devData, PVRSRV_DEVICE_TYPE_UNKNOWN);
    if (err != PVRSRV_OK)
    {
        fprintf(stderr, "PVRSRVAcquireDeviceData failed: %s\n", PVRSRVGetErrorStringFunc(err));
        goto DISCONNECT;
    }

    err = PVRSRVCreateDeviceMemContextFunc(&display->devData, &display->devMemContext, &heapCount, heapInfo);
    if (err != PVRSRV_OK)
    {
        fprintf(stderr, "PVRSRVCreateDeviceMemContext failed: %s\n", PVRSRVGetErrorStringFunc(err));
        goto DESTROY_CONTEXT;
    }

    for (i = 0; i < heapCount; i++)
    {
        if (HEAP_IDX(heapInfo[i].ui32HeapID) == SGX_GENERAL_HEAP_ID)
        {
            display->mappingHeap = heapInfo[i].hDevMemHeap;
            break;
        }
    }
    if (i == heapCount)
    {
        fprintf(stderr, "InitialiseServices: failed to find heap %u\n", SGX_GENERAL_HEAP_ID);
        goto DISCONNECT;
    }

    display->miscInfo.ui32StateRequest = PVRSRV_MISC_INFO_GLOBALEVENTOBJECT_PRESENT;
    err = PVRSRVGetMiscInfoFunc((IMG_HANDLE)display->services, &display->miscInfo);
    if (err != PVRSRV_OK)
    {
        fprintf(stderr, "PVRSRVGetMiscInfo failed: %s\n", PVRSRVGetErrorStringFunc(err));
        goto DESTROY_CONTEXT;
    }

    return true;

DESTROY_CONTEXT:
    PVRSRVDestroyDeviceMemContextFunc(&display->devData, display->devMemContext);
DISCONNECT:
    PVRSRVDisconnectFunc((IMG_HANDLE)display->services);
    return false;
}

static void DeInitialiseServices(struct GBMDisplay *display)
{
    PVRSRVReleaseMiscInfoFunc((IMG_HANDLE)display->services, &display->miscInfo);
    PVRSRVDestroyDeviceMemContextFunc(&display->devData, display->devMemContext);
    PVRSRVDisconnectFunc((IMG_HANDLE)display->services);
}

static inline bool unsigned_greater_equal(unsigned a, unsigned b)
{
    return (a - b) < INT_MAX;
}

static void WaitForOpsComplete(struct GBMDisplay *display, const PVRSRV_CLIENT_SYNC_INFO *syncInfo)
{
    PVRSRV_SYNC_DATA *sync = syncInfo->psSyncData;

    if (!sync)
        return;

    const IMG_UINT32 wops_pending = sync->ui32WriteOpsPending;
    const IMG_UINT32 rops_pending = sync->ui32ReadOpsPending;
    const IMG_UINT32 rops2_pending = sync->ui32ReadOps2Pending;

    for (;;)
    {
        IMG_UINT32 wops_complete = sync->ui32WriteOpsComplete;
        IMG_UINT32 rops_complete = sync->ui32ReadOpsComplete;
        IMG_UINT32 rops2_complete = sync->ui32ReadOps2Complete;

        if (unsigned_greater_equal(wops_complete, wops_pending) &&
            unsigned_greater_equal(rops_complete, rops_pending) &&
            unsigned_greater_equal(rops2_complete, rops2_pending))
        {
            break;
        }

        PVRSRVEventObjectWaitFunc(display->services, display->miscInfo.hOSGlobalEvent);
    }
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

    if (!InitialiseServices(display))
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

    DeInitialiseServices(display);

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
    PVRSRV_ERROR err;

    if (!nativeWindow)
        return WSEGL_BAD_NATIVE_WINDOW;

    struct GBMDrawable *drawable = calloc(sizeof(struct GBMDrawable), 1);
    if (!drawable)
        return WSEGL_OUT_OF_MEMORY;

    drawable->type = GBM_DRAWABLE_WINDOW;
    drawable->display = (struct GBMDisplay *)display;
    drawable->numBackBuffers = PVR_NUM_BACK_BUFFERS;
    drawable->backBuffers = calloc(sizeof(struct GBMBuffer), drawable->numBackBuffers);
    drawable->handle.window = (struct gbm_pvr_surface *)nativeWindow;
    drawable->width = drawable->handle.window->base.v0.width;
    drawable->height = drawable->handle.window->base.v0.height;

    for (int i = 0; i < PVR_NUM_BACK_BUFFERS; i++)
    {
        int dmaFd = gbm_bo_get_fd((struct gbm_bo *)drawable->handle.window->back_buffers[i]);
        if (dmaFd == -1)
            goto FAIL;
        void *mmap = gbm_bo_map((struct gbm_bo *)drawable->handle.window->back_buffers[i],
                                0, 0, drawable->width, drawable->height, 0,
                                &drawable->stride,
                                &drawable->backBuffers[i].mmap);
        if (!mmap)
        {
            close(dmaFd);
            goto FAIL;
        }
        err = PVRSRVMapFullDmaBufFunc(&drawable->display->devData,
                                      drawable->display->mappingHeap,
                                      PVRSRV_MAP_NOUSERVIRTUAL,
                                      dmaFd,
                                      &drawable->backBuffers[i].memInfo);
        close(dmaFd);
        if (err != PVRSRV_OK)
            goto FAIL;
    }

    *_drawable = drawable;
    *rotationAngle = WSEGL_ROTATE_0;

    return WSEGL_SUCCESS;

FAIL:

    for (int i = 0; i < PVR_NUM_BACK_BUFFERS; i++)
    {
        if (drawable->backBuffers[i].memInfo)
        {
            WaitForOpsComplete(display, drawable->backBuffers[i].memInfo->psClientSyncInfo);
            PVRSRVUnmapDmaBufFunc(&drawable->display->devData, drawable->backBuffers[i].memInfo);
            if (drawable->backBuffers[i].mmap)
                gbm_bo_unmap((struct gbm_bo *)drawable->handle.window->back_buffers[i], drawable->backBuffers[i].mmap);
        }
    }

    free(drawable->backBuffers);
    free(drawable);

    return WSEGL_OUT_OF_MEMORY;
}

/* Create the WSEGL drawable version of a native pixmap */
static WSEGLError wseglCreatePixmapDrawable(WSEGLDisplayHandle display, WSEGLConfig *config,
     WSEGLDrawableHandle *_drawable, NativePixmapType nativePixmap, WSEGLRotationAngle *rotationAngle)
{
    WSEGL_UNUSED(config);
    PVRSRV_ERROR err;

    if (!nativePixmap)
        return WSEGL_BAD_NATIVE_PIXMAP;

    struct GBMDrawable *drawable = calloc(sizeof(struct GBMDrawable), 1);
    if (!drawable)
        return WSEGL_OUT_OF_MEMORY;

    drawable->type = GBM_DRAWABLE_PIXMAP;
    drawable->display = (struct GBMDisplay *)display;
    drawable->numBackBuffers = 1;
    drawable->backBuffers = calloc(sizeof(struct GBMBuffer), drawable->numBackBuffers);
    drawable->handle.pixmap = (struct gbm_pvr_bo *)nativePixmap;
    drawable->width = drawable->handle.pixmap->base.v0.width;
    drawable->height = drawable->handle.pixmap->base.v0.height;

    int dmaFd = gbm_bo_get_fd((struct gbm_bo *)drawable->handle.pixmap);
    if (dmaFd == -1)
        goto FAIL;
    void *mmap = gbm_bo_map((struct gbm_bo *)drawable->handle.pixmap,
                            0, 0, drawable->width, drawable->height, 0,
                            &drawable->stride,
                            &drawable->backBuffers[0].mmap);
    if (!mmap)
    {
        close(dmaFd);
        goto FAIL;
    }
    err = PVRSRVMapFullDmaBufFunc(&drawable->display->devData,
                                  drawable->display->mappingHeap,
                                  PVRSRV_MAP_NOUSERVIRTUAL,
                                  dmaFd,
                                  &drawable->backBuffers[0].memInfo);

    close(dmaFd);
    if (err != PVRSRV_OK)
        goto FAIL;

    *_drawable = drawable;
    *rotationAngle = WSEGL_ROTATE_0;

    return WSEGL_SUCCESS;

FAIL:

    if (drawable->backBuffers[0].memInfo)
    {
        WaitForOpsComplete(display, drawable->backBuffers[0].memInfo->psClientSyncInfo);
        PVRSRVUnmapDmaBufFunc(&drawable->display->devData, drawable->backBuffers[0].memInfo);
        if (drawable->backBuffers[0].mmap)
            gbm_bo_unmap((struct gbm_bo *)drawable->handle.pixmap, drawable->backBuffers[0].mmap);
    }

    free(drawable->backBuffers);
    free(drawable);

    return WSEGL_OUT_OF_MEMORY;
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
        if (drawable->backBuffers[i].memInfo)
        {
            WaitForOpsComplete(display, drawable->backBuffers[i].memInfo->psClientSyncInfo);
            PVRSRVUnmapDmaBuf(&drawable->display->devData, drawable->backBuffers[i].memInfo);
            if (drawable->backBuffers[i].mmap)
                if (drawable->type == GBM_DRAWABLE_WINDOW)
                    gbm_bo_unmap((struct gbm_bo *)drawable->handle.window->back_buffers[i], drawable->backBuffers[i].mmap);
                else
                    gbm_bo_unmap((struct gbm_bo *)drawable->handle.pixmap, drawable->backBuffers[i].mmap);
        }
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

    return WSEGL_BAD_NATIVE_PIXMAP;
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

    return WSEGL_BAD_NATIVE_PIXMAP;
}

static WSEGLError wseglGetDrawableParameters(WSEGLDrawableHandle _drawable,
     WSEGLDrawableParams *sourceParams,
     WSEGLDrawableParams *renderParams)
{
    struct GBMDrawable *drawable = (struct GBMDrawable *)_drawable;
    PVRSRV_CLIENT_MEM_INFO *source, *render;
    WSEGLPixelFormat pixelFormat;
    int sourceIdx, renderIdx;

    sourceIdx = renderIdx = 0;
    if (drawable->type == GBM_DRAWABLE_WINDOW)
    {
        renderIdx = drawable->handle.window->current_back_buffer;
        sourceIdx = (drawable->handle.window->current_back_buffer + 1) % PVR_NUM_BACK_BUFFERS;
    }

    source = drawable->backBuffers[sourceIdx].memInfo;
    sourceParams->ui32Width = drawable->width;
    sourceParams->ui32Height = drawable->height;
    sourceParams->ui32Stride = drawable->stride / 4;
    sourceParams->ePixelFormat = WSEGL_PIXELFORMAT_ARGB8888;
    sourceParams->eRotationAngle = WSEGL_ROTATE_0;
    sourceParams->pvLinearAddress = source->pvLinAddr;
    sourceParams->ui32HWAddress = source->sDevVAddr.uiAddr;
    sourceParams->hPrivateData = source;

    render = drawable->backBuffers[renderIdx].memInfo;
    renderParams->ui32Width = drawable->width;
    renderParams->ui32Height = drawable->height;
    renderParams->ui32Stride = drawable->stride / 4;
    renderParams->ePixelFormat = WSEGL_PIXELFORMAT_ARGB8888;
    renderParams->eRotationAngle = WSEGL_ROTATE_0;
    renderParams->pvLinearAddress = render->pvLinAddr;
    renderParams->ui32HWAddress = render->sDevVAddr.uiAddr;
    renderParams->hPrivateData = render;

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
    unsigned *handle;

    if ((handle = dlopen("libsrv_init.so", RTLD_LAZY | RTLD_GLOBAL)) == NULL)
    {
        return NULL;
    }

    PVRSRVConnectFunc = dlsym(handle, "PVRSRVConnect");
    PVRSRVDisconnectFunc = dlsym(handle, "PVRSRVDisconnect");
    PVRSRVEnumerateDevicesFunc = dlsym(handle, "PVRSRVEnumerateDevices");
    PVRSRVAcquireDeviceDataFunc = dlsym(handle, "PVRSRVAcquireDeviceData");
    PVRSRVGetMiscInfoFunc = dlsym(handle, "PVRSRVGetMiscInfo");
    PVRSRVReleaseMiscInfoFunc = dlsym(handle, "PVRSRVReleaseMiscInfo");
    PVRSRVCreateDeviceMemContextFunc = dlsym(handle, "PVRSRVCreateDeviceMemContext");
    PVRSRVDestroyDeviceMemContextFunc = dlsym(handle, "PVRSRVDestroyDeviceMemContext");
    PVRSRVEventObjectWaitFunc = dlsym(handle, "PVRSRVEventObjectWait");
    PVRSRVMapFullDmaBufFunc = dlsym(handle, "PVRSRVMapFullDmaBuf");
    PVRSRVUnmapDmaBufFunc = dlsym(handle, "PVRSRVUnmapDmaBuf");
    PVRSRVGetErrorStringFunc = dlsym(handle, "PVRSRVGetErrorString");

    dlclose(handle);

    return &wseglFunctions;
}
