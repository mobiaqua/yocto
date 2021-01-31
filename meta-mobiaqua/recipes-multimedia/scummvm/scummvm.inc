DESCRIPTION = "ScummVM is a program which allows you to run certain classic graphical adventure games and Role-Playing games."
HOMEPAGE = "https://www.scummvm.org"
SECTION = "games"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
DEPENDS = "libsdl2 libsdl2-net virtual/egl virtual/libgles2 libvorbis libogg zlib curl libmad mpeg2dec flac \
libjpeg-turbo libpng libtheora faad2 fluidsynth freetype"

PR = "r0"

SRC_URI = "https://www.scummvm.org/frs/scummvm/${PV}/scummvm-${PV}.tar.bz2"

inherit autotools pkgconfig

FILES_${PN} = "${bindir}/scummvm ${datadir}/scummvm ${datadir}/pixmaps ${datadir}/icons ${datadir}/applications ${datadir}/metainfo"
FILES_${PN}-dbg = "${bindir}/.debug"
FILES_${PN}-doc = "${datadir}/doc"
FILES_${PN}-man = "${datadir}/man"
PACKAGES = "${PN} ${PN}-dbg ${PN}-doc ${PN}-man"

EXTRA_OECONF = "--host=${HOST_SYS} \
		--backend=sdl \
		--disable-alsa \
		--prefix=${prefix} \
		--with-ogg-prefix=${STAGING_LIBDIR}/.. \
		--with-tremor-prefix=${STAGING_LIBDIR}/.. \
		--with-mpeg2-prefix=${STAGING_LIBDIR}/.. \
		--with-mad-prefix=${STAGING_LIBDIR}/.. \
		--enable-release \
		--disable-debug \
		--enable-verbose-build \
		"

EXTRA_OEMAKE = "MANDIR=${mandir}"

# Workaround, because some env variables are not recognised correctly
do_configure() {
	${S}/configure ${EXTRA_OECONF}
	sed -i "s/AS := as/AS := ${AS}/" ${B}/config.mk
	sed -i "s/AR := ar cru/AR := ${AR} cru/" ${B}/config.mk
	sed -i "s/STRIP := strip/STRIP := ${STRIP}/" ${B}/config.mk
	sed -i "s/RANLIB := ranlib/RANLIB := ${RANLIB}/" ${B}/config.mk
}
