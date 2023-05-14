SUMMARY = "An open source MPEG-4 and MPEG-2 AAC decoding library"
HOMEPAGE = "http://www.audiocoding.com/faad2.html"
SECTION = "libs"
LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://COPYING;md5=381c8cbe277a7bc1ee2ae6083a04c958"

# MobiAqua: disabled
#LICENSE_FLAGS = "commercial"

# MobiAqua: added optimisations
FULL_OPTIMIZATION:append = " -fexpensive-optimizations -O4 -ffast-math"
FULL_OPTIMIZATION:append:panda = " -mvectorize-with-neon-quad"
FULL_OPTIMIZATION:append:beagle = " -mvectorize-with-neon-quad"

# MobiAqua: disabled
#PV .= "+git${SRCPV}"

SRC_URI = "git://github.com/knik0/faad2.git;branch=master;protocol=https"
SRCREV = "3918dee56063500d0aa23d6c3c94b211ac471a8c"

S = "${WORKDIR}/git"

inherit autotools lib_package

# MobiAqua: process version in missing header
do_configure:prepend() {
    cp ${S}/include/faad.h.in ${S}/include/faad.h
    sed -i 's|@VERSION@|${PV}|g' ${S}/include/faad.h
}
