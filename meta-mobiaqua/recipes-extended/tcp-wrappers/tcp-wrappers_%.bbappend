DEPENDS:remove = "libnsl2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-Remove-include.patch"
