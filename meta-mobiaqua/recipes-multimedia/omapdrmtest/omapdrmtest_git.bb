DEPENDS = "libdce libdrm ffmpeg virtual/libgbm virtual/egl"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://viddec3test.c;beginline=1;endline=16;md5=c391f44e40a29096285e3121923041df"

COMPATIBLE_MACHINE = "panda|beagle|igep0030"

inherit autotools pkgconfig

PV = "1.0"
PR = "r0"
PR:append = "+gitr-${SRCREV}"

SRCREV = "1fceb3ac4b0e7ed014000a10dfe6ca8729f9cd8d"
SRC_URI = "git://git.ti.com/glsdk/omapdrmtest.git;protocol=git;branch=master \
           file://0004-display-kmscube-align-width-on-128-bytes-to-please-Ducat.patch \
           file://0005-Hack-disp-kmscube-reduce-u-v-by-10.patch \
           file://drop-cube.patch \
           file://improve-display-connection.patch \
           file://bo-flags.patch \
           file://update_ffmpeg.patch \
"

S = "${WORKDIR}/git"

EXTRA_OECONF = "LDFLAGS='${LDFLAGS} -lm -lavcodec'"

ERROR_QA:remove = "ldflags"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
