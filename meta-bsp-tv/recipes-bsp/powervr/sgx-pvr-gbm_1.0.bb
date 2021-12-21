DESCRIPTION = "GBM plugin for SGX user space libs."
LICENSE = "MIT|GPLv2"

ERROR_QA_remove = "license-checksum"

DEPENDS = "libdrm libgbm pkgconfig-native virtual/egl"

SRC_URI = "\
	   file://LICENSE.GPL \
	   file://MIT-COPYING \
	   file://gbm_pvr.c \
	   file://gbm_pvrint.h \
	   file://img_defs.h \
	   file://img_types.h \
	   file://pdumpdefs.h \
	   file://pvrws_GBM.c \
	   file://pvrws_GBM.h \
	   file://services.h \
	   file://servicesext.h \
"

S = "${WORKDIR}"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"

CLEANBROKEN = "1"

INSANE_SKIP_${PN} += "dev-elf dev-deps"

do_configure[noexec] = "1"

do_compile() {
	export DRM_CFLAGS=`pkg-config --cflags libdrm`
	export DRM_LIBS=`pkg-config --libs libdrm`
	export GBM_CFLAGS=`pkg-config --cflags gbm`
	export GBM_LIBS=`pkg-config --libs gbm`

	if [ "${DEBUG_BUILD}" = "yes" ]; then
		${CC} ${CFLAGS} ${LDFLAGS} ${GBM_CFLAGS} ${GBM_LIBS} ${DRM_CFLAGS} ${DRM_LIBS} -O0 -g3 gbm_pvr.c -fPIC -shared -o gbm_pvr.so
		${CC} ${CFLAGS} ${LDFLAGS} ${GBM_CFLAGS} ${GBM_LIBS} ${DRM_CFLAGS} ${DRM_LIBS} -O0 -g3 pvrws_GBM.c -fPIC -shared -o libpvrws_GBM.so
	else
		${CC} ${CFLAGS} ${LDFLAGS} ${GBM_CFLAGS} ${GBM_LIBS} ${DRM_CFLAGS} ${DRM_LIBS} -DLINUX -g gbm_pvr.c -fPIC -shared -o gbm_pvr.so
		${CC} ${CFLAGS} ${LDFLAGS} ${GBM_CFLAGS} ${GBM_LIBS} ${DRM_CFLAGS} ${DRM_LIBS} -DLINUX -g pvrws_GBM.c -fPIC -shared -o libpvrws_GBM.so
	fi
}

do_install() {
	install -d ${D}${libdir}/gbm
	install -m 0644 gbm_pvr.so ${D}${libdir}/gbm
	install -m 0644 libpvrws_GBM.so ${D}${libdir}
}

PACKAGES = "${PN} ${PN}-dbg"

FILES_${PN} = "${libdir}/*.so ${libdir}/gbm/*.so"
FILES_${PN}-dbg = "${libdir}/.debug ${libdir}/gbm/.debug"
