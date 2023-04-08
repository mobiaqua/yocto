DESCRIPTION = "PVR DRI support stub library."

LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

PR = "r0"
PV = "1.0"

SRC_URI = "file://pvr_dri_support_stub.c"

CLEANBROKEN = "1"

do_configure() {
}

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} -g ${WORKDIR}/pvr_dri_support_stub.c -shared -o ${S}/libpvr_dri_support.so
}

do_install() {
	install -d ${D}${libdir}
	install -m 0666 ${S}/libpvr_dri_support.so ${D}${libdir}/
}

PACKAGES = "${PN} ${PN}-dbg"

FILES:${PN} = "${libdir}/*.so"
FILES:${PN}-dbg = "${libdir}/.debug"
