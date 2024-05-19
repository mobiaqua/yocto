FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://silence-gcc11-12-warning.patch \
           "
