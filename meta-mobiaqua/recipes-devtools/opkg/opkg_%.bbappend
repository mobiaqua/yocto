FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-macos.patch"

PACKAGECONFIG_remove = "libsolv"
