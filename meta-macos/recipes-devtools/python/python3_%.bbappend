FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS:remove:class-native = "libtirpc libnsl2"

SRC_URI += "file://added-lib-pc-config.patch"
