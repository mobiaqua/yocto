DESCRIPTION = "mpg123 is a fast and free console based real time MPEG \
Audio Player for Layer 1, 2 and 3."
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=1e86753638d3cf2512528b99079bc4f3"
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

SRC_URI[md5sum] = "294a6c30546504ec3d0deac2b2ea22be"
SRC_URI[sha256sum] = "90306848359c793fd43b9906e52201df18775742dc3c81c06ab67a806509890a"

S = "${WORKDIR}/mpg123-${PV}"

#MobiAqua: do not run parallel
PARALLEL_MAKE = ""

INSANE_SKIP_${PN} += "installed-vs-shipped"
