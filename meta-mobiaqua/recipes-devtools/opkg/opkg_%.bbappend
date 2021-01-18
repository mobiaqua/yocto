FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-macos.patch"

PACKAGECONFIG_remove = "libsolv"

EXTRA_OECONF_append_class-native = " --enable-shared=no"
EXTRA_OECONF_append_class-nativesdk = " --enable-shared=no"

LDFLAGS_append_class-native = " -lz"
LDFLAGS_append_class-nativesdk = " -lz"

LDFLAGS_append_darwin_class-native = " -liconv"
LDFLAGS_append_darwin_class-nativesdk = " -liconv"
