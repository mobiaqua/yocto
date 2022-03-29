LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://gbm.c;beginline=1;endline=25;md5=25104d6bc2ac7998715c25b43c0b3208"

PV = "21.2.0"
PR = "r0"

DEPENDS = "udev libdrm"

PROVIDES += "virtual/libgbm"

inherit autotools lib_package pkgconfig

SRCREV = "e1f9ff191c77be0d6936ff5d32e2b62faf9935a8"

SRC_URI = "git://github.com/mobiaqua/libgbm.git;protocol=git \
          "

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
