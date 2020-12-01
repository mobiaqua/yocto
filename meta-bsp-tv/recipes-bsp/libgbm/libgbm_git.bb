LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://gbm.c;beginline=1;endline=25;md5=25104d6bc2ac7998715c25b43c0b3208"

PV = "1.0.0"
PR = "r0"

DEPENDS = "udev libdrm"

inherit autotools lib_package pkgconfig

SRCREV = "7c469a6d7a92ee702c5852d35564b3942878b5b2"

SRC_URI = "git://github.com/mobiaqua/libgbm.git;protocol=git \
          "

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
