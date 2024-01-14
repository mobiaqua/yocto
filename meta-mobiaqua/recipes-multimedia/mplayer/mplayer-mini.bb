DESCRIPTION = "Open Source multimedia player."
SECTION = "multimedia"
PRIORITY = "optional"
HOMEPAGE = "http://www.mplayerhq.hu/"
DEPENDS = "ffmpeg zlib freetype fontconfig alsa-lib libmpg123 ncurses"
RDEPENDS:${PN} = "mplayer-common glibc-gconv-cp1250 ttf-dejavu-sans"

LICENSE = "GPL-2.0-or-later"
ERROR_QA:remove = "license-checksum"

RCONFLICTS:${PN} = "mplayer"

SRCREV = "60417c109a5838395eda5369a5fc4779abb9bc8d"
SRC_URI = "git://github.com/mobiaqua/mplayer-mini.git;protocol=https;branch=master"

PV = "1.0+git"
PR = "r1"

S = "${WORKDIR}/git/src"

FILES:${PN} = "${bindir}/mplayer"

inherit autotools pkgconfig

EXTRA_OECONF = " \
	--prefix=${prefix} \
	--mandir=${mandir} \
	--target=${TARGET_SYS} \
	--ar=${TARGET_PREFIX}ar \
"

do_configure() {
	cd ${S}
	sed -i 's|/usr/include|${STAGING_INCDIR}|g' ${S}/configure
	sed -i 's|/usr/lib|${STAGING_LIBDIR}|g' ${S}/configure
	sed -i 's|/usr/\S*include[\w/]*||g' ${S}/configure
	sed -i 's|/usr/\S*lib[\w/]*||g' ${S}/configure

	./configure ${EXTRA_OECONF}
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
