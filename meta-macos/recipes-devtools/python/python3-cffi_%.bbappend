inherit pkgconfig

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-python3-dep.patch"
