UMMARY = "Fluidsynth is a software synthesizer"
HOMEPAGE = "http://www.fluidsynth.org/"
SECTION = "libs/multimedia"
LICENSE = "LGPL-2.0+"
LIC_FILES_CHKSUM = "file://COPYING;md5=e198e9aac94943d0ec29a7dae8c29416"

DEPENDS = "alsa-lib ncurses glib-2.0"

# MobiAqua: older version with autotools
SRC_URI = "${SOURCEFORGE_MIRROR}/project/${BPN}/${BP}/${BP}.tar.gz \
           file://ac_lib_prog_gnu.m4.patch \
           file://0001-Use-ARM-NEON-accelaration-for-float-multithreaded-se.patch \
          "
SRC_URI[md5sum] = "ae5aca6de824b4173667cbd3a310b263"
SRC_URI[sha256sum] = "50853391d9ebeda9b4db787efb23f98b1e26b7296dd2bb5d0d96b5bccee2171c"

inherit autotools-brokensep pkgconfig lib_package

do_configure_prepend () {
	rm -f ${S}/m4/l*
}
