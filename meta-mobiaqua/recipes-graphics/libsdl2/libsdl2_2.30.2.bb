SUMMARY = "Simple DirectMedia Layer"
DESCRIPTION = "Simple DirectMedia Layer is a cross-platform multimedia \
library designed to provide low level access to audio, keyboard, mouse, \
joystick, 3D hardware via OpenGL, and 2D video framebuffer."
HOMEPAGE = "http://www.libsdl.org"
BUGTRACKER = "http://bugzilla.libsdl.org/"

SECTION = "libs"

LICENSE = "Zlib & BSD-2-Clause"
LIC_FILES_CHKSUM = "\
    file://LICENSE.txt;md5=25231a5b96ccdd8f39eb53c07717be64 \
    file://src/hidapi/LICENSE.txt;md5=7c3949a631240cb6c31c50f3eb696077 \
    file://src/hidapi/LICENSE-bsd.txt;md5=b5fa085ce0926bb50d0621620a82361f \
    file://src/video/yuv2rgb/LICENSE;md5=79f8f3418d91531e05f0fc94ca67e071 \
"

# arm-neon adds MIT license
LICENSE:append = " ${@bb.utils.contains('PACKAGECONFIG', 'arm-neon', '& MIT', '', d)}"
LIC_FILES_CHKSUM:append = " ${@bb.utils.contains('PACKAGECONFIG', 'arm-neon', 'file://src/video/arm/pixman-arm-neon-asm.h;md5=9a9cc1e51abbf1da58f4d9528ec9d49b;beginline=1;endline=24', '', d)}"

# MobiAqua: added optimisations
FULL_OPTIMIZATION:append = " -fexpensive-optimizations -O4 -ffast-math"

PROVIDES = "virtual/libsdl2"

# MobiAqua: keep use autoconf instead cmake
DEFAULT_PREFERENCE = "10"

DEPENDS = "virtual/libgles2 virtual/libgbm alsa-lib"

SRC_URI = "http://www.libsdl.org/release/SDL2-${PV}.tar.gz"

SRC_URI[sha256sum] = "891d66ac8cae51361d3229e3336ebec1c407a8a2a063b61df14f5fdf3ab5ac31"

S = "${WORKDIR}/SDL2-${PV}"

inherit autotools lib_package binconfig pkgconfig

BINCONFIG = "${bindir}/sdl2-config"

CVE_PRODUCT = "simple_directmedia_layer sdl"

EXTRA_OECONF = "--disable-oss \
                --disable-esd \
                --disable-arts \
                --disable-diskaudio \
                --disable-nas \
                --disable-esd-shared \
                --disable-esdtest \
                --disable-video-dummy \
                --enable-pthreads \
                --enable-alsa \
                --enable-arm-neon \
                --enable-video-opengles \
                --enable-video-kmsdrm \
                --disable-rpath \
                --disable-sndio \
                --disable-fcitx \
                --disable-ibus \
                "

EXTRA_AUTORECONF += "--include=acinclude --exclude=autoheader"

do_configure:prepend() {
        # Remove old libtool macros.
        MACROS="libtool.m4 lt~obsolete.m4 ltoptions.m4 ltsugar.m4 ltversion.m4"
        for i in ${MACROS}; do
               rm -f ${S}/acinclude/$i
        done
        export SYSROOT=$PKG_CONFIG_SYSROOT_DIR
}

FILES:${PN}-dev += "${libdir}/cmake"

FILES:${PN} += "${datadir}/licenses/SDL2/LICENSE.txt"

BBCLASSEXTEND = "native nativesdk"

# MobiAqua: Added debug flow
DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
