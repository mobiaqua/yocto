FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-macos.patch"

DEPENDS_remove = "python3 python3-native"

DEPENDS += "libelf-native"
