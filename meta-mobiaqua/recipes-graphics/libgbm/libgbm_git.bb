LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://gbm.c;beginline=1;endline=25;md5=25104d6bc2ac7998715c25b43c0b3208"

PV = "22.4.0"
PR = "r0"

DEPENDS = "udev libdrm"

DEFAULT_PREFERENCE = "10"

PROVIDES += "virtual/libgbm"

inherit autotools lib_package pkgconfig

SRCREV = "f83489d7744cdc8d9de2e4fc5b809d0d5218e6e0"

SRC_URI = "git://github.com/mobiaqua/libgbm.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
