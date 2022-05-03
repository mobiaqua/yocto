EXTRA_OECONF:append:class-native = " --enable-shared=no"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://macos-libs.patch"
