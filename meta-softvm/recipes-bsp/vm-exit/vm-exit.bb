DESCRIPTION = "Trigger exit from VM with specific error code."
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

COMPATIBLE_MACHINE = "vm-*"

SRC_URI = "file://vm-exit.c"

S = "${WORKDIR}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile() {
	${CC} -o vm-exit vm-exit.c ${CFLAGS} ${LDFLAGS}
}

do_install() {
	install -d ${D}${sbindir}
	install -m 0755 vm-exit ${D}${sbindir}
}
