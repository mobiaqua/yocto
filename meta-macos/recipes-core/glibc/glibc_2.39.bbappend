FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
           file://fix-case.patch \
           file://skip-locales.patch \
           "
