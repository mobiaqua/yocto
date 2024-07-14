SUMMARY = "Open Source multimedia player"
DESCRIPTION = "mpv is a fork of mplayer2 and MPlayer. It shares some features with the former projects while introducing many more."
SECTION = "multimedia"
HOMEPAGE = "http://www.mpv.io/"

DEPENDS = " \
    mpv-config \
    zlib \
    ffmpeg \
    libass \
    alsa-lib \
    lua5.1 \
    libdrm \
"

PV = "0.36+git"
PR = "r1"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "ad4b40772503f0751d8a51b2197533345f546b49"
SRC_URI = "git://github.com/mobiaqua/mpv-mini.git;protocol=https;branch=main"

S = "${WORKDIR}/git/src"

inherit autotools pkgconfig

FULL_OPTIMIZATION:append = " -fexpensive-optimizations -O4 -ffast-math"

EXTRA_OECONF = " \
    --prefix=${prefix} \
    --target=${TARGET_SYS} \
    --ar=${TARGET_PREFIX}ar \
    --confdir=${sysconfdir}/mpv \
    --datadir=${datadir} \
    --drm=yes \
"

do_configure() {
    cd ${S}
    sed -i -e "s,\$(SYSROOT)/usr/include,${STAGING_INCDIR},g" ${S}/configure
    ./configure ${EXTRA_OECONF}
}

do_compile () {
    cd ${S}
    oe_runmake
}

do_install () {
    cd ${S}
    oe_runmake install DESTDIR=${D}
}

FILES:${PN}-dbg = "${bindir}/.debug/mpv"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"

EXCLUDE_FROM_WORLD = "${@bb.utils.contains("LICENSE_FLAGS_ACCEPTED", "commercial", "0", "1", d)}"

INSANE_SKIP:${PN} = "mime-xdg"
INSANE_SKIP += "buildpaths"
