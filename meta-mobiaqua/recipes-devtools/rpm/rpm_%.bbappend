FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-compilation.patch"

DEPENDS_remove = "python3 python3-native dbus elfutils bzip2 xz"

EXTRA_OECONF_remove = "--enable-python"

EXTRA_OECONF_append_class-native = " --enable-shared=no"
EXTRA_OECONF_append_class-nativesdk = " --enable-shared=no"

LDFLAGS_append_class-native = " -lz -liconv"
LDFLAGS_append_class-nativesdk = " -lz -liconv"

RDEPENDS_${PN}_remove = "python3-core"
