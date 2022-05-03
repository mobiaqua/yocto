SUMMARY = "Fluidsynth is a software synthesizer"
HOMEPAGE = "http://www.fluidsynth.org/"
SECTION = "libs/multimedia"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc178bcd425090939a8b634d1d6a9594"

SRC_URI = "git://github.com/FluidSynth/fluidsynth.git;protocol=https;branch=master"
SRCREV = "2779d5b2b80f50c247a97e5bf163419e810a5f75"
S = "${WORKDIR}/git"
PV = "2.2.7"

inherit cmake pkgconfig lib_package
