
DEPENDS:remove:class-native = "util-linux attr"

EXTRA_OECONF:remove:class-native = "--enable-elf-shlibs --enable-bsd-shlibs"

do_install:prepend:class-native() {
    install -d ${D}${base_libdir}
}
