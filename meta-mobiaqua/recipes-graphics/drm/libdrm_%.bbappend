FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:panda = " file://add-deps.patch"
SRC_URI:append:beagle = " file://add-deps.patch"

PACKAGECONFIG:panda = "omap"
PACKAGECONFIG:beagle = "omap"
PACKAGECONFIG:beagle64 = ""
PACKAGECONFIG:nuc = "intel"

# MobiAqua: added debug flags
RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
MESON_BUILDTYPE = "${@['release','debug'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"
