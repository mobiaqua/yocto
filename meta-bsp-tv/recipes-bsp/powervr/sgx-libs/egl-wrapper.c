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

#define EGLAPI
#define EGLAPIENTRY

typedef int EGLNativeDisplayType;
typedef void *EGLNativePixmapType;
typedef void *EGLNativeWindowType;

typedef int EGLint;
typedef unsigned int EGLBoolean;
typedef unsigned int EGLenum;
typedef void *EGLConfig;
typedef void *EGLContext;
typedef void *EGLDisplay;
typedef void *EGLSurface;
typedef void *EGLClientBuffer;

EGLAPI EGLint EGLAPIENTRY IMGeglGetError(void);
EGLAPI EGLint EGLAPIENTRY eglGetError(void)
{
    return IMGeglGetError();
}

EGLAPI EGLDisplay EGLAPIENTRY IMGeglGetDisplay(EGLNativeDisplayType display_id);
EGLAPI EGLDisplay EGLAPIENTRY eglGetDisplay(EGLNativeDisplayType display_id)
{
    return IMGeglGetDisplay(display_id);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglInitialize(EGLDisplay dpy, EGLint *major, EGLint *minor);
EGLAPI EGLBoolean EGLAPIENTRY eglInitialize(EGLDisplay dpy, EGLint *major, EGLint *minor)
{
    return IMGeglInitialize(dpy, major, minor);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglTerminate(EGLDisplay dpy);
EGLAPI EGLBoolean EGLAPIENTRY eglTerminate(EGLDisplay dpy)
{
    return IMGeglTerminate(dpy);
}

EGLAPI const char * EGLAPIENTRY IMGeglQueryString(EGLDisplay dpy, EGLint name);
EGLAPI const char * EGLAPIENTRY eglQueryString(EGLDisplay dpy, EGLint name)
{
    return IMGeglQueryString(dpy, name);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglGetConfigs(EGLDisplay dpy, EGLConfig *configs, EGLint config_size, EGLint *num_config);
EGLAPI EGLBoolean EGLAPIENTRY eglGetConfigs(EGLDisplay dpy, EGLConfig *configs, EGLint config_size, EGLint *num_config)
{
    return IMGeglGetConfigs(dpy, configs, config_size, num_config);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglChooseConfig(EGLDisplay dpy, const EGLint *attrib_list, EGLConfig *configs, EGLint config_size, EGLint *num_config);
EGLAPI EGLBoolean EGLAPIENTRY eglChooseConfig(EGLDisplay dpy, const EGLint *attrib_list, EGLConfig *configs, EGLint config_size, EGLint *num_config)
{
    return IMGeglChooseConfig(dpy, attrib_list, configs, config_size, num_config);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglGetConfigAttrib(EGLDisplay dpy, EGLConfig config, EGLint attribute, EGLint *value);
EGLAPI EGLBoolean EGLAPIENTRY eglGetConfigAttrib(EGLDisplay dpy, EGLConfig config, EGLint attribute, EGLint *value)
{
    return IMGeglGetConfigAttrib(dpy, config, attribute, value);
}

EGLAPI EGLSurface EGLAPIENTRY IMGeglCreateWindowSurface(EGLDisplay dpy, EGLConfig config, EGLNativeWindowType win, const EGLint *attrib_list);
EGLAPI EGLSurface EGLAPIENTRY eglCreateWindowSurface(EGLDisplay dpy, EGLConfig config, EGLNativeWindowType win, const EGLint *attrib_list)
{
    return IMGeglCreateWindowSurface(dpy, config, win, attrib_list);
}

EGLAPI EGLSurface EGLAPIENTRY IMGeglCreatePbufferSurface(EGLDisplay dpy, EGLConfig config, const EGLint *attrib_list);
EGLAPI EGLSurface EGLAPIENTRY eglCreatePbufferSurface(EGLDisplay dpy, EGLConfig config, const EGLint *attrib_list)
{
    return IMGeglCreatePbufferSurface(dpy, config, attrib_list);
}

EGLAPI EGLSurface EGLAPIENTRY IMGeglCreatePixmapSurface(EGLDisplay dpy, EGLConfig config, EGLNativePixmapType pixmap, const EGLint *attrib_list);
EGLAPI EGLSurface EGLAPIENTRY eglCreatePixmapSurface(EGLDisplay dpy, EGLConfig config, EGLNativePixmapType pixmap, const EGLint *attrib_list)
{
    return IMGeglCreatePixmapSurface(dpy, config, pixmap, attrib_list);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglDestroySurface(EGLDisplay dpy, EGLSurface surface);
EGLAPI EGLBoolean EGLAPIENTRY eglDestroySurface(EGLDisplay dpy, EGLSurface surface)
{
    return IMGeglDestroySurface(dpy, surface);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglQuerySurface(EGLDisplay dpy, EGLSurface surface, EGLint attribute, EGLint *value);
EGLAPI EGLBoolean EGLAPIENTRY eglQuerySurface(EGLDisplay dpy, EGLSurface surface, EGLint attribute, EGLint *value)
{
    return IMGeglQuerySurface(dpy, surface, attribute, value);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglBindAPI(EGLenum api);
EGLAPI EGLBoolean EGLAPIENTRY eglBindAPI(EGLenum api)
{
    return IMGeglBindAPI(api);
}

EGLAPI EGLenum EGLAPIENTRY IMGeglQueryAPI(void);
EGLAPI EGLenum EGLAPIENTRY eglQueryAPI(void)
{
    return IMGeglQueryAPI();
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglWaitClient(void);
EGLAPI EGLBoolean EGLAPIENTRY eglWaitClient(void)
{
    return IMGeglWaitClient();
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglReleaseThread(void);
EGLAPI EGLBoolean EGLAPIENTRY eglReleaseThread(void)
{
    return IMGeglReleaseThread();
}

EGLAPI EGLSurface EGLAPIENTRY IMGeglCreatePbufferFromClientBuffer(EGLDisplay dpy, EGLenum buftype, EGLClientBuffer buffer,
                                                                  EGLConfig config, const EGLint *attrib_list);
EGLAPI EGLSurface EGLAPIENTRY eglCreatePbufferFromClientBuffer(EGLDisplay dpy, EGLenum buftype, EGLClientBuffer buffer,
                                                               EGLConfig config, const EGLint *attrib_list)
{
    return IMGeglCreatePbufferFromClientBuffer(dpy, buftype, buffer, config, attrib_list);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglSurfaceAttrib(EGLDisplay dpy, EGLSurface surface, EGLint attribute, EGLint value);
EGLAPI EGLBoolean EGLAPIENTRY eglSurfaceAttrib(EGLDisplay dpy, EGLSurface surface, EGLint attribute, EGLint value)
{
    return IMGeglSurfaceAttrib(dpy, surface, attribute, value);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglBindTexImage(EGLDisplay dpy, EGLSurface surface, EGLint buffer);
EGLAPI EGLBoolean EGLAPIENTRY eglBindTexImage(EGLDisplay dpy, EGLSurface surface, EGLint buffer)
{
    return IMGeglBindTexImage(dpy, surface, buffer);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglReleaseTexImage(EGLDisplay dpy, EGLSurface surface, EGLint buffer);
EGLAPI EGLBoolean EGLAPIENTRY eglReleaseTexImage(EGLDisplay dpy, EGLSurface surface, EGLint buffer)
{
    return IMGeglReleaseTexImage(dpy, surface, buffer);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglSwapInterval(EGLDisplay dpy, EGLint interval);
EGLAPI EGLBoolean EGLAPIENTRY eglSwapInterval(EGLDisplay dpy, EGLint interval)
{
    return IMGeglSwapInterval(dpy, interval);
}

EGLAPI EGLContext EGLAPIENTRY IMGeglCreateContext(EGLDisplay dpy, EGLConfig config, EGLContext share_context, const EGLint *attrib_list);
EGLAPI EGLContext EGLAPIENTRY eglCreateContext(EGLDisplay dpy, EGLConfig config, EGLContext share_context, const EGLint *attrib_list)
{
    return IMGeglCreateContext(dpy, config, share_context, attrib_list);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglDestroyContext(EGLDisplay dpy, EGLContext ctx);
EGLAPI EGLBoolean EGLAPIENTRY eglDestroyContext(EGLDisplay dpy, EGLContext ctx)
{
    return IMGeglDestroyContext(dpy, ctx);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglMakeCurrent(EGLDisplay dpy, EGLSurface draw, EGLSurface read, EGLContext ctx);
EGLAPI EGLBoolean EGLAPIENTRY eglMakeCurrent(EGLDisplay dpy, EGLSurface draw, EGLSurface read, EGLContext ctx)
{
    return IMGeglMakeCurrent(dpy, draw, read, ctx);
}

EGLAPI EGLContext EGLAPIENTRY IMGeglGetCurrentContext(void);
EGLAPI EGLContext EGLAPIENTRY eglGetCurrentContext(void)
{
    return IMGeglGetCurrentContext();
}

EGLAPI EGLSurface EGLAPIENTRY IMGeglGetCurrentSurface(EGLint readdraw);
EGLAPI EGLSurface EGLAPIENTRY eglGetCurrentSurface(EGLint readdraw)
{
    return IMGeglGetCurrentSurface(readdraw);
}

EGLAPI EGLDisplay EGLAPIENTRY IMGeglGetCurrentDisplay(void);
EGLAPI EGLDisplay EGLAPIENTRY eglGetCurrentDisplay(void)
{
    IMGeglGetCurrentDisplay();
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglQueryContext(EGLDisplay dpy, EGLContext ctx, EGLint attribute, EGLint *value);
EGLAPI EGLBoolean EGLAPIENTRY eglQueryContext(EGLDisplay dpy, EGLContext ctx, EGLint attribute, EGLint *value)
{
    return IMGeglQueryContext(dpy, ctx, attribute, value);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglWaitGL(void);
EGLAPI EGLBoolean EGLAPIENTRY eglWaitGL(void)
{
    return IMGeglWaitGL();
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglWaitNative(EGLint engine);
EGLAPI EGLBoolean EGLAPIENTRY eglWaitNative(EGLint engine)
{
    return IMGeglWaitNative(engine);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglSwapBuffers(EGLDisplay dpy, EGLSurface surface);
EGLAPI EGLBoolean EGLAPIENTRY eglSwapBuffers(EGLDisplay dpy, EGLSurface surface)
{
    return IMGeglSwapBuffers(dpy, surface);
}

EGLAPI EGLBoolean EGLAPIENTRY IMGeglCopyBuffers(EGLDisplay dpy, EGLSurface surface, EGLNativePixmapType target);
EGLAPI EGLBoolean EGLAPIENTRY eglCopyBuffers(EGLDisplay dpy, EGLSurface surface, EGLNativePixmapType target)
{
    return IMGeglCopyBuffers(dpy, surface, target);
}

typedef void (*__eglMustCastToProperFunctionPointerType)(void);

EGLAPI __eglMustCastToProperFunctionPointerType EGLAPIENTRY IMGeglGetProcAddress(const char *procname);
EGLAPI __eglMustCastToProperFunctionPointerType EGLAPIENTRY eglGetProcAddress(const char *procname)
{
    return IMGeglGetProcAddress(procname);
}
