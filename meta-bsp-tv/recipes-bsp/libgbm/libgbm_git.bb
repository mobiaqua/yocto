LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://gbm.c;beginline=1;endline=25;md5=25104d6bc2ac7998715c25b43c0b3208"

PV = "20.3.0"
PR = "r0"

DEPENDS = "udev libdrm"

PROVIDES += "virtual/libgbm"

inherit autotools lib_package pkgconfig

SRCREV = "b797c147000216cade2af9101339a568c20c084a"

SRC_URI = "git://github.com/mobiaqua/libgbm.git;protocol=git \
          "

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
