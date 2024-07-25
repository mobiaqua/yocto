SUMMARY = "The SoX resampler library"
DESCRIPTION = "The SoX Resampler library `libsoxr' performs one-dimensional sample-rate conversion -- it may be used, for example, to resample PCM-encoded audio."
HOMEPAGE = "https://sourceforge.net/projects/soxr/"

LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING.LGPL;md5=8c2e1ec1540fb3e0beb68361344cba7e"

SRCREV = "945b592b70470e29f917f4de89b4281fbbd540c0"
PV = "+git${SRCPV}"
SRC_URI = "git://github.com/chirlu/soxr.git;branch=master;protocol=https"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE += "-DCMAKE_CROSSCOMPILING=TRUE"
EXTRA_OECMAKE += "-DBUILD_TESTS=FALSE"
EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=Release"
EXTRA_OECMAKE += "-Wno-dev"
EXTRA_OECMAKE += "-Wnocast-function-type"
INSANE_SKIP:${PN} = "already-stripped"
