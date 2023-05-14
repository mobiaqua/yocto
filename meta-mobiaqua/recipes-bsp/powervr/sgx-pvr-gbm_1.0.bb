DESCRIPTION = "GBM plugin for SGX user space libs."
LICENSE = "MIT|GPL-3.0-only|LGPL-2.0-only"

ERROR_QA:remove = "license-checksum"

DEPENDS = "libdrm pkgconfig-native virtual/libgbm virtual/egl"

COMPATIBLE_MACHINE = "panda|beagle|igep0030"

SRC_URI = "\
	   file://LICENSE.GPL \
	   file://MIT-COPYING \
	   file://gbm.h \
	   file://gbm_backend_abi.h \
	   file://gbm_pvr.c \
	   file://gbm_pvrint.h \
	   file://gbmint.h \
	   file://img_defs.h \
	   file://img_types.h \
	   file://pdumpdefs.h \
	   file://pvrws_GBM.c \
	   file://pvrws_GBM.h \
	   file://services.h \
	   file://servicesext.h \
	   file://wsegl.h \
"

S = "${WORKDIR}"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"

CLEANBROKEN = "1"

INSANE_SKIP:${PN} += "dev-elf dev-deps"

do_configure[noexec] = "1"

do_compile() {
	if [ "${DEBUG_BUILD}" = "yes" ]; then
		${CC} ${CFLAGS} `pkg-config --cflags libdrm libdrm_omap gbm` ${LDFLAGS} -O0 -g3 gbm_pvr.c -fPIC -shared `pkg-config --libs libdrm libdrm_omap gbm` -o pvr_gbm.so
		${CC} ${CFLAGS} `pkg-config --cflags libdrm libdrm_omap gbm` -DLINUX ${LDFLAGS} -O0 -g3 pvrws_GBM.c -fPIC -shared `pkg-config --libs libdrm_omap libdrm gbm` -o libpvrws_GBM.so
	else
		${CC} ${CFLAGS} `pkg-config --cflags libdrm libdrm_omap gbm` ${LDFLAGS} -g gbm_pvr.c -fPIC -shared `pkg-config --libs libdrm libdrm_omap gbm` -o pvr_gbm.so
		${CC} ${CFLAGS} `pkg-config --cflags libdrm libdrm_omap gbm` -DLINUX ${LDFLAGS} -g pvrws_GBM.c -fPIC -shared `pkg-config --libs libdrm libdrm_omap gbm` -o libpvrws_GBM.so
	fi
}

do_install() {
	install -d ${D}${libdir}/gbm
	install -m 0644 pvr_gbm.so ${D}${libdir}/gbm
	install -m 0644 libpvrws_GBM.so ${D}${libdir}
	install -d ${D}${sysconfdir}/profile.d
	echo "#export GBM_BACKEND=pvr" > ${D}${sysconfdir}/profile.d/pvr_gbm.sh
	chmod 755 ${D}${sysconfdir}/profile.d/pvr_gbm.sh
}

PACKAGES = "${PN} ${PN}-dbg"

FILES:${PN} = "${libdir}/*.so ${libdir}/gbm/*.so ${sysconfdir}/profile.d/*.sh"
FILES:${PN}-dbg = "${libdir}/.debug ${libdir}/gbm/.debug"
