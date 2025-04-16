SUMMARY = "Audio decoder for MPEG-1 Layer 1/2/3"
DESCRIPTION = "The core of mpg123 is an MPEG-1 Layer 1/2/3 decoding library, which can be used by other programs. \
mpg123 also comes with a command-line tool which can playback using ALSA, PulseAudio, OSS, and several other APIs, \
and also can write the decoded audio to WAV."
HOMEPAGE = "http://mpg123.de/"
BUGTRACKER = "http://sourceforge.net/p/mpg123/bugs/"
SECTION = "multimedia"

LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=e7b9c15fcfb986abb4cc5e8400a24169"

SRC_URI = "https://www.mpg123.de/download/mpg123-${PV}.tar.bz2"
SRC_URI[sha256sum] = "87b2c17fe0c979d3ef38eeceff6362b35b28ac8589fbf1854b5be75c9ab6557c"

UPSTREAM_CHECK_REGEX = "mpg123-(?P<pver>\d+(\.\d+)+)\.tar"

inherit autotools pkgconfig

EXTRA_OECONF="--with-cpu=generic_nofpu --enable-network=no"
EXTRA_OECONF:arm="--with-cpu=arm_nofpu --with-optimization=4 --enable-network=no"
EXTRA_OECONF:arm6="--with-cpu=arm_nofpu --with-optimization=4 --enable-network=no"
EXTRA_OECONF:armv7a-hf="--with-cpu=neon --with-optimization=4 --enable-network=no"

S = "${WORKDIR}/mpg123-${PV}"

#MobiAqua: do not run parallel
PARALLEL_MAKE = ""

INSANE_SKIP:${PN} += "installed-vs-shipped"
