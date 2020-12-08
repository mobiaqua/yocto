DESCRIPTION = "SGX540 (PowerVR) OpenGL ES 1.1, 2.0 libraries for OMAP4."

LICENSE = "Proprietary|MIT|LGPL-2.1"
ERROR_QA_remove = "license-checksum"

PR = "r0"
PV = "1.9.6"

SRCREV = "4df1d8556cf3b4a6d5f2bc156e8730714c294c28"

SRC_URI = "git://github.com/mobiaqua/pvr-omap4.git;protocol=git \
	   file://LICENSE.txt \
	   file://includes \
	   file://wayland-dummy.c \
	   file://egl-wrapper.c \
	   file://gbm_pvr.c \
	   file://gbm_pvrint.h \
	   file://pvrws_GBM.c \
	   file://pvrws_GBM.h \
	   "

COMPATIBLE_MACHINE = "board-tv"
PROVIDES += "virtual/egl virtual/libgles virtual/libgles2"
DEPENDS = "libdrm libgbm pkgconfig-native"

DEFAULT_PREFERENCE = "10"

S = "${WORKDIR}/git"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"

CLEANBROKEN = "1"

ASNEEDED = ""

do_configure() {
	install -d ${D}${includedir}
	cp -pR ${WORKDIR}/includes/* ${STAGING_DIR_TARGET}${includedir}/
	cp -p ${S}${libdir}/libpvr2d.so.1.9.6.0 ${STAGING_DIR_TARGET}${libdir}/libpvr2d.so
}

do_compile() {
	export DRM_CFLAGS=`pkg-config --cflags libdrm`
	export DRM_LIBS=`pkg-config --libs libdrm`
	export GBM_CFLAGS=`pkg-config --cflags gbm`
	export GBM_LIBS=`pkg-config --libs gbm`
	${CC} ${CFLAGS} ${LDFLAGS} -g ${WORKDIR}/wayland-dummy.c -shared -o ${S}/libwayland-server.so.0
	${CC} ${CFLAGS} ${LDFLAGS} -g ${WORKDIR}/egl-wrapper.c -shared -o ${S}/libEGL.so

	if [ "${DEBUG_BUILD}" = "yes" ]; then
		${CC} ${CFLAGS} ${LDFLAGS} ${GBM_CFLAGS} ${GBM_LIBS} ${DRM_CFLAGS} ${DRM_LIBS} -O0 -g3 -lpvr2d ${WORKDIR}/gbm_pvr.c \
			-fPIC -shared -o ${S}/gbm_pvr.so
		${CC} ${CFLAGS} ${LDFLAGS} ${GBM_CFLAGS} ${GBM_LIBS} ${DRM_CFLAGS} ${DRM_LIBS} -O0 -g3 -lpvr2d ${WORKDIR}/pvrws_GBM.c \
			-fPIC -shared -o ${S}/libpvrws_GBM.so
	else
		install -d ${S}/.debug
		${CC} ${CFLAGS} ${LDFLAGS} ${GBM_CFLAGS} ${GBM_LIBS} ${DRM_CFLAGS} ${DRM_LIBS} -g -lpvr2d ${WORKDIR}/gbm_pvr.c \
			-fPIC -shared -o ${S}/gbm_pvr.so
		${OBJCOPY} --only-keep-debug ${S}/gbm_pvr.so ${S}/.debug/gbm_pvr.so
		${OBJCOPY} --strip-debug ${S}/gbm_pvr.so
		${OBJCOPY} --add-gnu-debuglink ${S}/.debug/gbm_pvr.so ${S}/gbm_pvr.so

		${CC} ${CFLAGS} ${LDFLAGS} ${GBM_CFLAGS} ${GBM_LIBS} ${DRM_CFLAGS} ${DRM_LIBS} -g -lpvr2d ${WORKDIR}/pvrws_GBM.c \
			-fPIC -shared -o ${S}/libpvrws_GBM.so
		${OBJCOPY} --only-keep-debug ${S}/libpvrws_GBM.so ${S}/.debug/libpvrws_GBM.so
		${OBJCOPY} --strip-debug ${S}/libpvrws_GBM.so
		${OBJCOPY} --add-gnu-debuglink ${S}/.debug/libpvrws_GBM.so ${S}/libpvrws_GBM.so
	fi
}

do_install() {
	install -d ${D}${libdir}
	for i in GLESv1_CM GLESv2 IMGegl PVRScopeServices glslcompiler pvr2d srv_init srv_um usc
	do
		cp -p ${S}${libdir}/lib${i}.so.1.9.6.0 ${D}${libdir}/lib${i}.so
	done
	ln -s libGLESv1_CM.so ${D}${libdir}/libGLESv1_CM.so.1
	ln -s libGLESv2.so ${D}${libdir}/libGLESv2.so.2

	install -m 0666 ${S}/libwayland-server.so.0 ${D}${libdir}/

	install -m 0666 ${S}/libEGL.so ${D}${libdir}/
	ln -s libEGL.so ${D}${libdir}/libEGL.so.1

	install -m 0666 ${S}/libpvrws_GBM.so ${D}${libdir}/
	ln -s libpvrws_GBM.so ${D}${libdir}/libpvrws_KMS.so

	install -d ${D}${libdir}/gbm
	install -m 0666 ${S}/gbm_pvr.so ${D}${libdir}/gbm/gbm_pvr.so

	if [ "${DEBUG_BUILD}" = "no" ]; then
		install -d ${D}${libdir}/.debug
		install -m 0666 ${S}/.debug/libpvrws_GBM.so ${D}${libdir}/.debug/
		install -d ${D}${libdir}/gbm/.debug
		install -m 0666 ${S}/.debug/gbm_pvr.so ${D}${libdir}/gbm/.debug/
	fi

	install -d ${D}${libdir}/pkgconfig
	for i in egl glesv1_cm glesv2
	do
		cp -p ${S}${libdir}/pkgconfig/${i}.pc ${D}${libdir}/pkgconfig/
	done

	install -d ${D}/usr/share/doc/${PN}
	install -m 0666 ${WORKDIR}/LICENSE.txt ${D}/usr/share/doc/${PN}

	install -d ${D}${includedir}
	cp -pPr ${WORKDIR}/includes/* ${D}${includedir}
}

INSANE_SKIP_${PN} += "already-stripped dev-so useless-rpaths ldflags dev-deps"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES_${PN} = "${bindir} ${libdir}/*.so* ${libdir}/gbm/*.so* /usr/share/doc/"
FILES_${PN}-dev = "${libdir}/pkgconfig ${includedir}"
FILES_${PN}-dbg = "${libdir}/.debug ${libdir}/gbm/.debug"
