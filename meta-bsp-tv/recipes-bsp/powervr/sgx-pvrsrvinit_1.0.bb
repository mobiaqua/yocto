DESCRIPTION = "Program to init SGX services."
LICENSE = "MIT"

ERROR_QA_remove = "license-checksum"

DEPENDS = "sgx-libs"

SRC_URI = "file://pvrsrvinit.c"

S = "${WORKDIR}"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile() {
	${CC} -o pvrsrvinit pvrsrvinit.c ${CFLAGS} ${LDFLAGS} -g -lsrv_init
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 pvrsrvinit ${D}${bindir}
}
