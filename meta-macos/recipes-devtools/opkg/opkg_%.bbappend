FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-macos.patch"

PACKAGECONFIG:remove = "libsolv"

EXTRA_OECONF:append:class-native = " --enable-shared=no"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no"

LDFLAGS:append:class-native = " -lz -llzma"
LDFLAGS:append:class-nativesdk = " -lz -llzma"

LDFLAGS:append:darwin:class-native = " -liconv"
LDFLAGS:append:darwin:class-nativesdk = " -liconv"
