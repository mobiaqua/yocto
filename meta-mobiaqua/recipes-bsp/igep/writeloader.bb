DESCRIPTION = "Writes a loader binary to a OneNAND/NAND flash memory device"
HOMEPAGE = "http://www.isee.biz"
LICENSE = "GPL-2.0-only"
ERROR_QA:remove = "license-checksum"

PR = "r0"

SRC_URI = "file://writeloader.c file://Makefile"

do_compile() {
	oe_runmake
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/writeloader ${D}${bindir}/writeloader
}

S = "${WORKDIR}"
