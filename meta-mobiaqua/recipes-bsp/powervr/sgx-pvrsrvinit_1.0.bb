DESCRIPTION = "Program to init SGX services."
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

DEPENDS = ""

SRC_URI = "file://pvrsrvinit.c"

S = "${WORKDIR}"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile() {
	if [ "${DEBUG_BUILD}" = "yes" ]; then
		${CC} -o pvrsrvinit pvrsrvinit.c ${CFLAGS} ${LDFLAGS} -ldl -g3 -O0
	else
		${CC} -o pvrsrvinit pvrsrvinit.c ${CFLAGS} ${LDFLAGS} -ldl -g
	fi
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 pvrsrvinit ${D}${bindir}
}
