FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://add-deps.patch"

PACKAGECONFIG ??= "omap"

# MobiAqua: added debug flags
RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
