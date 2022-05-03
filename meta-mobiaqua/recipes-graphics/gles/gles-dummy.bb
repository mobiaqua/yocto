DESCRIPTION = "Dummy EGL, GLES1, GLES2 libraries with includes."

LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

PR = "r0"
PV = "1.0"

SRC_URI = "\
	   file://includes \
	   file://libEGL_dummy.c \
	   file://libGLESv1_CM_dummy.c \
	   file://libGLESv2_dummy.c \
	   file://egl.pc \
	   file://glesv1_cm.pc \
	   file://glesv2.pc \
	   "

PROVIDES = "virtual/egl virtual/libgles virtual/libgles2"
DEPENDS = "libdrm virtual/libgbm pkgconfig-native"

DEFAULT_PREFERENCE = "10"

CLEANBROKEN = "1"

ASNEEDED = ""

do_configure() {
	install -d ${D}${includedir}
	cp -pR ${WORKDIR}/includes/* ${STAGING_DIR_TARGET}${includedir}/
}

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} -g ${WORKDIR}/libEGL_dummy.c -shared -o ${S}/libEGL.so
	${CC} ${CFLAGS} ${LDFLAGS} -g ${WORKDIR}/libGLESv1_CM_dummy.c -shared -o ${S}/libGLESv1_CM.so
	${CC} ${CFLAGS} ${LDFLAGS} -g ${WORKDIR}/libGLESv2_dummy.c -shared -o ${S}/libGLESv2.so
}

do_install() {
	install -d ${D}${libdir}

	install -m 0666 ${S}/libEGL.so ${D}${libdir}/
	ln -s libEGL.so ${D}${libdir}/libEGL.so.1

	install -m 0666 ${S}/libGLESv1_CM.so ${D}${libdir}/
	ln -s libGLESv1_CM.so ${D}${libdir}/libGLESv1_CM.so.1

	install -m 0666 ${S}/libGLESv2.so ${D}${libdir}/
	ln -s libGLESv2.so ${D}${libdir}/libGLESv2.so.2

	install -d ${D}${libdir}/pkgconfig
	for i in egl glesv1_cm glesv2
	do
		install -m 0666 ${WORKDIR}/${i}.pc ${D}${libdir}/pkgconfig/
	done

	install -d ${D}${includedir}
	cp -pPr ${WORKDIR}/includes/* ${D}${includedir}
}

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES:${PN} = "${libdir}/*.so*"
FILES:${PN}-dev = "${libdir}/pkgconfig ${includedir}"
FILES:${PN}-dbg = "${libdir}/.debug ${libdir}/gbm/.debug"
