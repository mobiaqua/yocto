/* -*- mode: c; indent-tabs-mode: t; c-basic-offset: 8; tab-width: 8 -*- */
/* vi: set ts=8 sw=8 sts=8: */
/*************************************************************************/ /*!
@File
@Title          PVR DRI interface definition
@Copyright      Copyright (c) Imagination Technologies Ltd. All Rights Reserved
@License        MIT

The contents of this file are subject to the MIT license as set out below.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/ /**************************************************************************/

#include <stdint.h>
#include <stdbool.h>

int PVRDRIGetDeviceTypeFromFd(int iFd) { return 0; }
bool PVRDRIIsFirstScreen(void *psScreenImpl) { return false; }
uint32_t PVRDRIPixFmtGetDepth(int eFmt) { return 0; }
uint32_t PVRDRIPixFmtGetBPP(int eFmt) { return 0; }
uint32_t PVRDRIPixFmtGetBlockSize(int eFmt) { return 0; }
void *PVRDRICreateScreenImpl(int iFd) { return 0; }
void PVRDRIDestroyScreenImpl(void *psScreenImpl) {}
int PVRDRIAPIVersion(int eAPI,
                     int eAPISub,
                     void *psScreenImpl) { return 0; }
void *PVRDRIEGLGetLibHandle(int eAPI,
                            void *psScreenImpl) { return 0; }
void *PVRDRIEGLGetProcAddress(int eAPI,
                              void *psScreenImpl,
                              const char *psProcName) { return 0; }
bool PVRDRIEGLFlushBuffers(int eAPI,
                           void *psScreenImpl,
                           void *psContextImpl,
                           void *psDrawableImpl,
                           bool bFlushAllSurfaces,
                           bool bSwapBuffers,
                           bool bWaitForHW) { return false; }
bool PVRDRIEGLFreeResources(void *psPVRScreenImpl) { return false; }
void PVRDRIEGLMarkRendersurfaceInvalid(int eAPI,
                                       void *psScreenImpl,
                                       void *psContextImpl) {}
void PVRDRIEGLSetFrontBufferCallback(int eAPI,
                                     void *psScreenImpl,
                                     void *psDrawableImpl,
                                     void *pfnCallback) {}
unsigned PVRDRICreateContextImpl(void **ppsContextImpl,
                                 int eAPI,
                                 int eAPISub,
                                 void *psScreenImpl,
                                 const void *psConfigInfo,
                                 unsigned uMajorVersion,
                                 unsigned uMinorVersion,
                                 uint32_t uFlags,
                                 bool bNotifyReset,
                                 unsigned uPriority,
                                 void *psSharedContextImpl) { return 0; }
void PVRDRIDestroyContextImpl(void *psContextImpl,
                              int eAPI,
                              void *psScreenImpl) {}
bool PVRDRIMakeCurrentGC(int eAPI,
                         void *psScreenImpl,
                         void *psContextImpl,
                         void *psWriteImpl,
                         void *psReadImpl) { return false; }
void PVRDRIMakeUnCurrentGC(int eAPI,
                           void *psScreenImpl) {}
unsigned PVRDRIGetImageSource(int eAPI,
                              void *psScreenImpl,
                              void *psContextImpl,
                              uint32_t uiTarget,
                              uintptr_t uiBuffer,
                              uint32_t uiLevel,
                              void *psEGLImage) { return 0; }
bool PVRDRI2BindTexImage(int eAPI,
                         void *psScreenImpl,
                         void *psContextImpl,
                         void *psDrawableImpl) { return false; }
void PVRDRI2ReleaseTexImage(int eAPI,
                            void *psScreenImpl,
                            void *psContextImpl,
                            void *psDrawableImpl) {}
void *PVRDRICreateDrawableImpl(void *psPVRDrawable) { return 0; }
void PVRDRIDestroyDrawableImpl(void *psScreenImpl) {}
bool PVREGLDrawableCreate(void *psScreenImpl,
                          void *psDrawableImpl) { return false; }
bool PVREGLDrawableRecreate(void *psScreenImpl,
                            void *psDrawableImpl)  { return false; }
bool PVREGLDrawableDestroy(void *psScreenImpl,
                           void *psDrawableImpl) { return false; }
void PVREGLDrawableDestroyConfig(void *psDrawableImpl) {}
void *PVRDRIBufferCreate(void *psScreenImpl,
                         int iWidth,
                         int iHeight,
                         unsigned int uiBpp,
                         unsigned int uiUseFlags,
                         unsigned int *puiStride) { return 0; }
void *PVRDRIBufferCreateFromNames(void *psScreenImpl,
                                  int iWidth,
                                  int iHeight,
                                  unsigned uiNumPlanes,
                                  const int *piName,
                                  const int *piStride,
                                  const int *piOffset,
                                  const unsigned int *puiWidthShift,
                                  const unsigned int *puiHeightShift) { return 0; }
void *PVRDRIBufferCreateFromName(void *psScreenImpl,
                                 int iName,
                                 int iWidth,
                                 int iHeight,
                                 int iStride,
                                 int iOffset) { return 0; }
void *PVRDRIBufferCreateFromFds(void *psScreenImpl,
                                int iWidth,
                                int iHeight,
                                unsigned uiNumPlanes,
                                const int *piFd,
                                const int *piStride,
                                const int *piOffset,
                                const unsigned int *puiWidthShift,
                                const unsigned int *puiHeightShift) { return 0; }
void PVRDRIBufferDestroy(void *psBuffer) {}
int PVRDRIBufferGetFd(void *psBuffer) { return 0; }
int PVRDRIBufferGetHandle(void *psBuffer) { return 0; }
int PVRDRIBufferGetName(void *psBuffer) { return 0; }
void *PVRDRIEGLImageCreate(void) { return 0; }
void *PVRDRIEGLImageCreateFromBuffer(int iWidth,
                                     int iHeight,
                                     int iStride,
                                     int ePixelFormat,
                                     int eColourSpace,
                                     int eChromaUInterp,
                                     int eChromaVInterp,
                                     void *psBuffer) { return 0; }
void *PVRDRIEGLImageDup(void *psIn) { return 0; }
void PVRDRIEGLImageSetCallbackData(void *psEGLImage, void *image) {}
void PVRDRIEGLImageDestroyExternal(void *psScreenImpl,
                                   void *psEGLImage,
                                   int eglImageType) {}
void PVRDRIEGLImageFree(void *psEGLImage) {}
void PVRDRIEGLImageGetAttribs(void *psEGLImage, void *psAttribs) {}
void *PVRDRICreateFenceImpl(int eAPI,
                            void *psScreenImpl,
                            void *psContextImpl) { return 0; }
void PVRDRIDestroyFenceImpl(void *psDRIFence) {}
bool PVRDRIClientWaitSyncImpl(int eAPI,
                              void *psContextImpl,
                              void *psDRIFence,
                              bool bFlushCommands,
                              bool bTimeout,
                              uint64_t uiTimeout) { return false; }
bool PVRDRIServerWaitSyncImpl(int eAPI,
                              void *psContextImpl,
                              void *psDRIFence) { return false; }
void PVRDRIDestroyFencesImpl(void *psScreenImpl) {}
bool PVRDRIEGLDrawableConfigFromGLMode(void *psPVRDrawable,
                                       void *psConfigInfo,
                                       int supportedAPIs,
                                       int ePixFmt) { return false; }
void PVRDRIRegisterCallbacks(void *callbacks) {}
bool PVRDRIMesaFormatSupported(unsigned fmt) { return false; }
unsigned PVRDRIDepthStencilBitArraySize(void) { return 0; }
const uint8_t *PVRDRIDepthBitsArray(void) { return 0; }
const uint8_t *PVRDRIStencilBitsArray(void) { return 0; }
unsigned PVRDRIMSAABitArraySize(void) { return 0; }
const uint8_t *PVRDRIMSAABitsArray(void) { return 0; }
uint32_t PVRDRIMaxPBufferWidth(void) { return 0; }
uint32_t PVRDRIMaxPBufferHeight(void) { return 0; }
unsigned PVRDRIGetNumAPIFuncs(int eAPI) { return 0; }
const char *PVRDRIGetAPIFunc(int eAPI, unsigned index) { return 0; }
