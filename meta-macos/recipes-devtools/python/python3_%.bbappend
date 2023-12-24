FILESEXTRAPATHS:prepend:class-native := "${THISDIR}/${PN}:"

DEPENDS:remove:class-native = "libtirpc libnsl2"

EXTRA_OECONF:append:class-native = " --enable-shared=no"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no"
