EXTRA_OECONF_append_class-native = " --enable-shared=no"
EXTRA_OECONF_append_class-nativesdk = " --enable-shared=no"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://macos-libs.patch"
