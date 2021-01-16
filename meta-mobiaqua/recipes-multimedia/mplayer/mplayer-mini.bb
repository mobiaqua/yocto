DESCRIPTION = "Open Source multimedia player."
SECTION = "multimedia"
PRIORITY = "optional"
HOMEPAGE = "http://www.mplayerhq.hu/"
DEPENDS = "ffmpeg zlib freetype fontconfig alsa-lib libmpg123 ncurses"
DEPENDS_append_board-tv = " libdce libdrm libgbm virtual/egl"
RDEPENDS_${PN} = "mplayer-common glibc-gconv-cp1250 ttf-dejavu-sans"

DEPENDS_append_board-tv = " ${@['','gdb-cross-arm'][d.getVar('BUILD_DEBUG') == '1']}"

LICENSE = "GPLv2+"
ERROR_QA_remove = "license-checksum"

RCONFLICTS_${PN} = "mplayer"

SRCREV = "5392547cb7312a2517c653284fabee4acd66abd1"
SRC_URI = "git://github.com/mobiaqua/mplayer-mini.git;protocol=git"

PV = "1.0+git"
PR = "r1"

S = "${WORKDIR}/git/src"

FILES_${PN} = "${bindir}/mplayer"

inherit autotools pkgconfig

EXTRA_OECONF = " \
	--prefix=${prefix} \
	--mandir=${mandir} \
	--target=${TARGET_SYS} \
	--ar=${TARGET_PREFIX}ar \
"

FULL_OPTIMIZATION_append = " -fexpensive-optimizations -mvectorize-with-neon-quad -O4 -ffast-math"

do_configure_prepend_board-tv() {
	export DCE_CFLAGS=`pkg-config --cflags libdce`
	export DCE_LIBS=`pkg-config --libs libdce`
	export DRM_CFLAGS=`pkg-config --cflags libdrm`
	export DRM_LIBS=`pkg-config --libs libdrm`
	export GBM_CFLAGS=`pkg-config --cflags gbm`
	export GBM_LIBS=`pkg-config --libs gbm`
	export EGL_CFLAGS=`pkg-config --cflags egl`
	export EGL_LIBS=`pkg-config --libs egl`
}

do_configure() {
	cd ${S}
	sed -i 's|/usr/include|${STAGING_INCDIR}|g' ${S}/configure
	sed -i 's|/usr/lib|${STAGING_LIBDIR}|g' ${S}/configure
	sed -i 's|/usr/\S*include[\w/]*||g' ${S}/configure
	sed -i 's|/usr/\S*lib[\w/]*||g' ${S}/configure

	./configure ${EXTRA_OECONF} \
		--extra-libs="${DCE_LIBS} ${DRM_LIBS} ${GBM_LIBS} ${EGL_LIBS} -lGLESv2" \
		--extra-cflags="${DCE_CFLAGS} ${DRM_CFLAGS} ${GBM_CFLAGS} ${EGL_CFLAGS}"
}

do_compile () {
	cd ${S}
	oe_runmake
}

do_install () {
	cd ${S}
	oe_runmake install DESTDIR=${D}
}

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
