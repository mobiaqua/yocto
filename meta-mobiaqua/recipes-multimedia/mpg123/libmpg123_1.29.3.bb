DESCRIPTION = "mpg123 is a fast and free console based real time MPEG \
Audio Player for Layer 1, 2 and 3."
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=e7b9c15fcfb986abb4cc5e8400a24169"
DESCRIPTION = "multimedia"
HOMEPAGE = "http://www.mpg123.de"
RCONFLICTS_${PN} = "mpg321"
RREPLACES_${PN} = "mpg321"
PR = "r0"

SRC_URI = "http://www.mpg123.de/download/mpg123-${PV}.tar.bz2"

EXTRA_OECONF="--with-cpu=generic_nofpu --enable-network=no"
EXTRA_OECONF_arm="--with-cpu=arm_nofpu --with-optimization=4 --enable-network=no"
EXTRA_OECONF_arm6="--with-cpu=arm_nofpu --with-optimization=4 --enable-network=no"
EXTRA_OECONF_armv7a-hf="--with-cpu=neon --with-optimization=4 --enable-network=no"

inherit autotools pkgconfig

SRC_URI[sha256sum] = "963885d8cc77262f28b77187c7d189e32195e64244de2530b798ddf32183e847"

S = "${WORKDIR}/mpg123-${PV}"

#MobiAqua: do not run parallel
PARALLEL_MAKE = ""

INSANE_SKIP_${PN} += "installed-vs-shipped"
