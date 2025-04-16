require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot-tools.inc

DEFAULT_PREFERENCE = "10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

SRCREV = "303f8fed261020c1cb7da32dad63b610bf6873dd"

SRC_URI = "git://source.denx.de/u-boot/u-boot.git;protocol=https;branch=master"

SRC_URI += "file://avoid-python.patch"
