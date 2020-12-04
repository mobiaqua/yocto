LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://gbm.c;beginline=1;endline=25;md5=25104d6bc2ac7998715c25b43c0b3208"

PV = "20.3.0"
PR = "r0"

DEPENDS = "udev libdrm"

PROVIDES += "virtual/libgbm"

inherit autotools lib_package pkgconfig

SRCREV = "ea77e09baa125a679d869320c35fe7d0d4a9f49d"

SRC_URI = "git://github.com/mobiaqua/libgbm.git;protocol=git \
          "

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
