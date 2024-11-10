EXTRA_OEMESON:append:class-native:darwin = " -Ddefault_library=static"
EXTRA_OEMESON:append:class-nativesdk:darwin = " -Ddefault_library=static"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " file://pixman-arm.patch;;striplevel=0"
