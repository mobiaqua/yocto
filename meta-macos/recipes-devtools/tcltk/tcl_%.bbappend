FILESEXTRAPATHS:append := "${THISDIR}/${PN}:"

BASE_SRC_URI += "file://macos-detect.patch"

CACHED_CONFIGUREVARS = "tcl_cv_sys_version=Linux"
CACHED_CONFIGUREVARS:darwin:class-native = "tcl_cv_sys_version=Darwin"
