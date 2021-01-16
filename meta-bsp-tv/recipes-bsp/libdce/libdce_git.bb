DEPENDS = "libdce-firmware libdrm virtual/kernel libmmrpc"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://libdce.c;beginline=1;endline=31;md5=0a398cf815b8b5f31f552266cd453dae"

inherit autotools lib_package pkgconfig

PV = "1.0"
PR = "r0"
PR_append = "+gitr-${SRCREV}"

SRCREV = "8fef2c7a13bf8902e31a4beb62f6e2048f506305"
SRC_URI = "git://github.com/mobiaqua/libdce.git;protocol=git"

S = "${WORKDIR}/git"

WARN_QA_remove = "ldflags"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS_append = "${@['',' -O0 -g3 -DDCE_DEBUG_ENABLE=1 -DDCE_DEBUG_LEVEL=1'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS_append = "${@['',' -DDCE_DEBUG_ENABLE=0 -DDCE_DEBUG_LEVEL=0'][d.getVar('BUILD_DEBUG') == '0']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
