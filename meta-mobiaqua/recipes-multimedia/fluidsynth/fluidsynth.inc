SUMMARY = "Fluidsynth is a software synthesizer"
HOMEPAGE = "http://www.fluidsynth.org/"
SECTION = "libs/multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc178bcd425090939a8b634d1d6a9594"

SRC_URI = "git://github.com/FluidSynth/fluidsynth.git;protocol=https;branch=master"
SRCREV = "7e3fdc9056f1d94bcff686d5178af6f3cf720613"
S = "${WORKDIR}/git"
PV = "2.2.8"

inherit cmake pkgconfig lib_package
