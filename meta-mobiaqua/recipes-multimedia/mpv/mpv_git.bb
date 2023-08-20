SUMMARY = "Open Source multimedia player"
DESCRIPTION = "mpv is a fork of mplayer2 and MPlayer. It shares some features with the former projects while introducing many more."
SECTION = "multimedia"
HOMEPAGE = "http://www.mpv.io/"

DEPENDS = " \
    mpv-config \
    zlib \
    ffmpeg \
    jpeg \
    libass \
    alsa-lib \
    lua5.1 \
    libdrm \
    virtual/egl \
    virtual/libgbm \
"

PV = "0.36+git"
PR = "r1"

DEPENDS:append:nuc = " virtual/libgl libva"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV_mpv = "f4210f84906c3b00a65fba198c8127b6757b9350"
SRC_URI = "git://github.com/mpv-player/mpv;name=mpv;branch=master;protocol=https \
           file://disable-vt-switcher.patch \
           https://waf.io/waf-2.0.26;name=waf;subdir=git \
           "
SRC_URI[waf.sha256sum] = "dcec3e179f9c33a66544f1b3d7d91f20f6373530510fa6a858cddb6bfdcde14b"

S = "${WORKDIR}/git"

inherit waf pkgconfig

FULL_OPTIMIZATION:append = " -fexpensive-optimizations -O4 -ffast-math"
FULL_OPTIMIZATION:append:panda = " -mvectorize-with-neon-quad"
FULL_OPTIMIZATION:append:beagle = " -mvectorize-with-neon-quad"

SIMPLE_TARGET_SYS = "${@'${TARGET_SYS}'.replace('${TARGET_VENDOR}', '')}"

EXTRA_OECONF = " \
    --prefix=${prefix} \
    --target=${SIMPLE_TARGET_SYS} \
    --confdir=${sysconfdir}/mpv \
    --datadir=${datadir} \
    --disable-manpage-build \
    --disable-libbluray \
    --disable-dvdnav \
    --disable-cdda \
    --disable-uchardet \
    --disable-rubberband \
    --disable-lcms2 \
    --disable-vapoursynth \
    --enable-lua \
    --enable-egl \
    --enable-drm \
    --enable-gbm \
    --enable-egl-drm \
"

EXTRA_OECONF:append:nuc = " --enable-gl --enable-vaapi --enable-vaapi-drm"

do_configure:append() {
    sed -i -e 's#${WORKDIR}#<WORKDIR>#g' ${B}/config.h
}

link_waf() {
    ln -s waf-2.0.26 ${S}/waf
}
do_unpack[postfuncs] += "link_waf"

FILES:${PN} += " \
    ${datadir}/icons \
    ${datadir}/zsh \
    ${datadir}/bash-completion \
    ${datadir}/metainfo \
    "
EXCLUDE_FROM_WORLD = "${@bb.utils.contains("LICENSE_FLAGS_ACCEPTED", "commercial", "0", "1", d)}"

INSANE_SKIP:${PN} = "mime-xdg"
