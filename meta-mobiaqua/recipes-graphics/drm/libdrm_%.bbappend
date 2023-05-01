FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://add-deps.patch"

PACKAGECONFIG ??= "omap"

# MobiAqua: added debug flags
RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
MESON_BUILDTYPE = "${@['release','debug'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"
