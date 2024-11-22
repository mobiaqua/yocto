DESCRIPTION = "elf include"
SECTION = "base"
PRIORITY = "required"
PR = "r1"

LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://../elf.h;beginline=1;endline=19;md5=635a92f535e7069de2e3df33267632b8"

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
