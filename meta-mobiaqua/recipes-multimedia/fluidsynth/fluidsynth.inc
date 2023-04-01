SUMMARY = "Fluidsynth is a software synthesizer"
HOMEPAGE = "http://www.fluidsynth.org/"
SECTION = "libs/multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc178bcd425090939a8b634d1d6a9594"

SRC_URI = "git://github.com/FluidSynth/fluidsynth.git;protocol=https;branch=master"
SRCREV = "3cf3f447188fabf5431576a6684e94384c0f2a36"
S = "${WORKDIR}/git"
PV = "2.3.1"

inherit cmake pkgconfig lib_package
