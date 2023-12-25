FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-compilation.patch \
            file://fixed-attr.patch \
            file://lua-fix.patch \
            "

DEPENDS:remove = "dbus elfutils xz bzip2"
DEPENDS:append:class-native = "file-native openssl-native"

EXTRA_OECONF:remove = "--enable-python"
EXTRA_OECONF:append:class-native = " --enable-shared=no --disable-zstd --with-crypto=openssl"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no --disable-zstd --with-crypto=openssl"

LDFLAGS:append:class-native = " -lz"
LDFLAGS:append:class-nativesdk = " -lz"

LDFLAGS:append:darwin:class-native = " -liconv"
LDFLAGS:append:darwin:class-nativesdk = " -liconv"
