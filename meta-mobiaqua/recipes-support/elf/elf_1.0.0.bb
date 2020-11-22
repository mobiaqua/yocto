DESCRIPTION = "elf include"
LICENSE = "GPL"
SECTION = "base"
PRIORITY = "required"
PR = "r1"

LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://../elf.h;beginline=1;endline=19;md5=aa2bee2948e3607390c5476d992138b7"

SRC_URI = "file://elf.h"

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	install -d ${D}${includedir}/
	install -m 0644 ${WORKDIR}/elf.h ${D}${includedir}/
}

BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"
