FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-macos.patch"

PACKAGECONFIG:remove:class-native = "libsolv"
PACKAGECONFIG:remove:class-nativesdk = "libsolv"

DEPENDS:remove = "zstd"
DEPENDS:append:class-native = " zstd"
DEPENDS:append:class-nativesdk = " zstd"

EXTRA_OECONF:append:class-native = " --enable-shared=no"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no"

LDFLAGS:append:class-native = " -lz -llzma -lzstd"
LDFLAGS:append:class-nativesdk = " -lz -llzma -lzstd"

LDFLAGS:append:darwin:class-native = " -liconv"
LDFLAGS:append:darwin:class-nativesdk = " -liconv"
