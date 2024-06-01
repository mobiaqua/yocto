FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG:remove:class-native = "editline gdbm"

DEPENDS:remove:class-native = "libtirpc libnsl2 ncurses util-linux-libuuid"

SRC_URI += "file://added-lib-pc-config.patch"
