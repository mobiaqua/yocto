DESCRIPTION = "Trigger exit from QEMU with specific error code."
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

COMPATIBLE_MACHINE = "emu*"

SRC_URI = "file://qemu-exit.c"

S = "${WORKDIR}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile() {
	${CC} -o qemu-exit qemu-exit.c ${CFLAGS} ${LDFLAGS}
}

do_install() {
	install -d ${D}${sbindir}
	install -m 0755 qemu-exit ${D}${sbindir}
}
