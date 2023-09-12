DEPENDS:append:panda = " libdce libdrm virtual/libgbm virtual/egl virtual/libgles2"
DEPENDS:append:beagle = " libdce libdrm virtual/libgbm virtual/egl virtual/libgles2"
DEPENDS:append:beagle64 = " libdrm virtual/libgbm virtual/egl virtual/libgles2"

FULL_OPTIMIZATION:append:panda = " -mvectorize-with-neon-quad"
FULL_OPTIMIZATION:append:beagle = " -mvectorize-with-neon-quad"

do_configure:prepend:panda() {
	export DCE_CFLAGS=`pkg-config --cflags libdce`
	export DCE_LIBS=`pkg-config --libs libdce`
	export DRM_CFLAGS=`pkg-config --cflags libdrm`
	export DRM_LIBS=`pkg-config --libs libdrm`
	export GBM_CFLAGS=`pkg-config --cflags gbm`
	export GBM_LIBS=`pkg-config --libs gbm`
	export EGL_CFLAGS=`pkg-config --cflags egl`
	export EGL_LIBS="`pkg-config --libs egl` -lGLESv2"
}

do_configure:prepend:beagle() {
	export DCE_CFLAGS=`pkg-config --cflags libdce`
	export DCE_LIBS=`pkg-config --libs libdce`
	export DRM_CFLAGS=`pkg-config --cflags libdrm`
	export DRM_LIBS=`pkg-config --libs libdrm`
	export GBM_CFLAGS=`pkg-config --cflags gbm`
	export GBM_LIBS=`pkg-config --libs gbm`
	export EGL_CFLAGS=`pkg-config --cflags egl`
	export EGL_LIBS="`pkg-config --libs egl` -lGLESv2"
}

do_configure:prepend:beagle64() {
	export DRM_CFLAGS=`pkg-config --cflags libdrm`
	export DRM_LIBS=`pkg-config --libs libdrm`
	export GBM_CFLAGS=`pkg-config --cflags gbm`
	export GBM_LIBS=`pkg-config --libs gbm`
	export EGL_CFLAGS=`pkg-config --cflags egl`
	export EGL_LIBS="`pkg-config --libs egl` -lGLESv2"
}

EXTRA_CFLAGS:panda = " -DOMAP_DRM=1 -DOMAP_DCE=1"
EXTRA_CFLAGS:beagle = " -DOMAP_DRM=1 -DOMAP_DCE=1"
EXTRA_CFLAGS:beagle64 = " -DOMAP_DRM=0 -DOMAP_DCE=0"
