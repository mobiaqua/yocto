DESCRIPTION = "Parse =* arguments passed from /proc/cmdline"
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

SRC_URI = "file://cmdline-parse.c"

S = "${WORKDIR}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile() {
	${CC} -o cmdline-parse cmdline-parse.c ${CFLAGS} ${LDFLAGS}
}

do_install() {
	install -d ${D}${sbindir}
	install -m 0755 cmdline-parse ${D}${sbindir}
}
