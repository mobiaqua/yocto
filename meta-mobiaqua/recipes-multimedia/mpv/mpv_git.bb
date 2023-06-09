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

PV = "0.35+git"
PR = "r1"

DEPENDS:append:nuc = " virtual/libgl libva"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV_mpv = "1c82d6ae7a31f8ef3a3f5120bd50480c312039cf"
SRC_URI = "git://github.com/mpv-player/mpv;name=mpv;branch=master;protocol=https \
           file://disable-vt-switcher.patch \
           https://waf.io/waf-2.0.25;name=waf;subdir=git \
           "
SRC_URI[waf.sha256sum] = "21199cd220ccf60434133e1fd2ab8c8e5217c3799199c82722543970dc8e38d5"

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
    ln -s waf-2.0.25 ${S}/waf
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
