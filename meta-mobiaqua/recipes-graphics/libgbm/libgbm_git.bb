LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://gbm.c;beginline=1;endline=25;md5=25104d6bc2ac7998715c25b43c0b3208"

PV = "22.2.0"
PR = "r0"

DEPENDS = "udev libdrm"

DEFAULT_PREFERENCE = "10"

PROVIDES += "virtual/libgbm"

inherit autotools lib_package pkgconfig

SRCREV = "12f869f1a72154a432d6105a3b59fe10191061d4"

SRC_URI = "git://github.com/mobiaqua/libgbm.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
