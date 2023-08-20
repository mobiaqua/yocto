DESCRIPTION = "Tool to sign omap3 x-loader images"
LICENSE = "BSD-3-Clause"
ERROR_QA:remove = "license-checksum"

inherit native

SRC_URI = "file://signGP.c"

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} ${WORKDIR}/signGP.c -o signGP
}

NATIVE_INSTALL_WORKS = "1"
do_install() {
	install -d ${D}${bindir}/
	install -m 0755 signGP ${D}${bindir}/
}
