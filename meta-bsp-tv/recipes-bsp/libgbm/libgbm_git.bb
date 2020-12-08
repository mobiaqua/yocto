LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://gbm.c;beginline=1;endline=25;md5=25104d6bc2ac7998715c25b43c0b3208"

PV = "20.3.0"
PR = "r0"

DEPENDS = "udev libdrm"

PROVIDES += "virtual/libgbm"

inherit autotools lib_package pkgconfig

SRCREV = "fff814ca7573abed1e61e5219c72cb102b02f47e"

SRC_URI = "git://github.com/mobiaqua/libgbm.git;protocol=git \
          "

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
