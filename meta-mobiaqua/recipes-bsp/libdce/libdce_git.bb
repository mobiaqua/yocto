DEPENDS = "libdrm virtual/kernel libmmrpc"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://libdce.c;beginline=1;endline=31;md5=0a398cf815b8b5f31f552266cd453dae"

inherit autotools lib_package pkgconfig

PV = "1.0"
PR = "r0"
PR:append = "+gitr-${SRCREV}"

SRCREV = "792af9096b02af661703971f1e8116cee0d9201d"
SRC_URI = "git://github.com/mobiaqua/libdce.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

WARN_QA:remove = "ldflags"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS:append = "${@['',' -O0 -g3 -DDCE_DEBUG_ENABLE=1 -DDCE_DEBUG_LEVEL=1'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS:append = "${@['',' -DDCE_DEBUG_ENABLE=0 -DDCE_DEBUG_LEVEL=0'][d.getVar('BUILD_DEBUG') == '0']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
