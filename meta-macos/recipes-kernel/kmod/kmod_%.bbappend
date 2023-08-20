DEPENDS:class-native += "libelf-native"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:class-native = " file://fix-macos.patch"
