DESCRIPTION = "ScummVM is a program which allows you to run certain classic graphical adventure games and Role-Playing games."
HOMEPAGE = "https://www.scummvm.org"
SECTION = "games"
PRIORITY = "optional"
LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"
DEPENDS = "libsdl2 libsdl2-net virtual/libgles2 libvorbis libogg zlib curl libmad mpeg2dec flac \
libjpeg-turbo libpng libtheora faad2 fluidsynth freetype liba52"

PR = "r0"

SRC_URI = "https://www.scummvm.org/frs/scummvm/${PV}/scummvm-${PV}.tar.bz2"

inherit autotools pkgconfig

FILES:${PN} = "${bindir}/scummvm ${datadir}/scummvm ${datadir}/pixmaps ${datadir}/icons ${datadir}/applications ${datadir}/metainfo"
FILES:${PN}-dbg = "${bindir}/.debug"
FILES:${PN}-doc = "${datadir}/doc"
FILES:${PN}-man = "${datadir}/man"
PACKAGES = "${PN} ${PN}-dbg ${PN}-doc ${PN}-man"

EXTRA_OECONF = "--host=${HOST_SYS} \
		--backend=sdl \
		--disable-alsa \
		--prefix=${prefix} \
		--enable-release \
		--enable-verbose-build \
		"

EXTRA_OEMAKE = "MANDIR=${mandir}"

do_configure() {
	${S}/configure ${EXTRA_OECONF}
}
