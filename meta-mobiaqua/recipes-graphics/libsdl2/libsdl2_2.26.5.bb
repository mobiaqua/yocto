SUMMARY = "Simple DirectMedia Layer"
DESCRIPTION = "Simple DirectMedia Layer is a cross-platform multimedia \
library designed to provide low level access to audio, keyboard, mouse, \
joystick, 3D hardware via OpenGL, and 2D video framebuffer."
HOMEPAGE = "http://www.libsdl.org"
BUGTRACKER = "http://bugzilla.libsdl.org/"

SECTION = "libs"

LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=31f575634fd56b27fc6b6cbe8dc9bd38"

# arm-neon adds MIT license
LICENSE:append = " ${@bb.utils.contains('PACKAGECONFIG', 'arm-neon', '& MIT', '', d)}"
LIC_FILES_CHKSUM:append = " ${@bb.utils.contains('PACKAGECONFIG', 'arm-neon', 'file://src/video/arm/pixman-arm-neon-asm.h;md5=9a9cc1e51abbf1da58f4d9528ec9d49b;beginline=1;endline=24', '', d)}"

# MobiAqua: added optimisations
FULL_OPTIMIZATION:append = " -fexpensive-optimizations -mvectorize-with-neon-quad -O4 -ffast-math"

PROVIDES = "virtual/libsdl2"

DEFAULT_PREFERENCE = "10"

DEPENDS = "virtual/libgles2 virtual/libgbm alsa-lib"

SRC_URI = "http://www.libsdl.org/release/SDL2-${PV}.tar.gz \
"

SRC_URI[sha256sum] = "ad8fea3da1be64c83c45b1d363a6b4ba8fd60f5bde3b23ec73855709ec5eabf7"

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

BBCLASSEXTEND = "native nativesdk"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
