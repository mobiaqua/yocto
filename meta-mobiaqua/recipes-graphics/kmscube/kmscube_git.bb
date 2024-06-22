LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://kmscube.c;beginline=1;endline=24;md5=6a9937e46939d8c76bd08e680c30a963"

DEPENDS = "libdrm virtual/libgbm virtual/egl virtual/libgles2"

inherit autotools pkgconfig

PV = "1.0"
PR = "r0"
PR:append = "+gitr-${SRCREV}"

DEFAULT_PREFERENCE = "10"

SRCREV = "a9d05233fe7e5129fc8f625443bcf19d4f1c47df"
SRC_URI = "git://github.com/mobiaqua/kmscube.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
