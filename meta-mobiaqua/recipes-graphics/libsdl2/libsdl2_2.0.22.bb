SUMMARY = "Simple DirectMedia Layer"
DESCRIPTION = "Simple DirectMedia Layer is a cross-platform multimedia \
library designed to provide low level access to audio, keyboard, mouse, \
joystick, 3D hardware via OpenGL, and 2D video framebuffer."
HOMEPAGE = "http://www.libsdl.org"
BUGTRACKER = "http://bugzilla.libsdl.org/"

SECTION = "libs"

LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=68a088513da90254b2fbe664f42af315"

# arm-neon adds MIT license
LICENSE:append = " ${@bb.utils.contains('PACKAGECONFIG', 'arm-neon', '& MIT', '', d)}"
LIC_FILES_CHKSUM:append = " ${@bb.utils.contains('PACKAGECONFIG', 'arm-neon', 'file://src/video/arm/pixman-arm-neon-asm.h;md5=9a9cc1e51abbf1da58f4d9528ec9d49b;beginline=1;endline=24', '', d)}"

PROVIDES = "virtual/libsdl2"

DEFAULT_PREFERENCE = "10"

DEPENDS = "virtual/libgles2 virtual/libgbm alsa-lib"

SRC_URI = "http://www.libsdl.org/release/SDL2-${PV}.tar.gz \
"

SRC_URI[sha256sum] = "fe7cbf3127882e3fc7259a75a0cb585620272c51745d3852ab9dd87960697f2e"

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
