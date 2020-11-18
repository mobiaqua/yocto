DESCRIPTION = "libelf is an ELF object file access library. \
The elf library provides routines to access, and manipulate, Elf object files."
LICENSE = "LGPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=6e29c688d912da12b66b73e32b03d812"
SECTION = "libs"
PR = "r1"

SRC_URI = "https://fossies.org/linux/misc/old/libelf-${PV}.tar.gz;name=archive \
	  file://elf_repl.patch \
	  file://skip-po.patch \
	  "
inherit autotools

PARALLEL_MAKE = ""

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OECONF_append_virtclass-native = " --enable-static"
EXTRA_OECONF_append_virtclass-nativesdk = " --enable-static"

do_configure_prepend () {
	if test ! -e ${S}/acinclude.m4; then
		cp ${S}/aclocal.m4 ${S}/acinclude.m4
	fi
}

do_install () {
	oe_runmake 'prefix=${D}${prefix}' 'exec_prefix=${D}${exec_prefix}' \
		   'libdir=${D}${libdir}' 'includedir=${D}${includedir}' \
		   install
	install -d ${STAGING_INCDIR}/libelf
        for i in libelf.h nlist.h gelf.h; do
                install -m 0644 ${S}/lib/$i ${STAGING_INCDIR}/libelf/
        done
        install -m 0644 ${B}/lib/sys_elf.h ${STAGING_INCDIR}/libelf/
        make includedir=${STAGING_INCDIR} install-compat
}
BBCLASSEXTEND = "native nativesdk"
# both SRC_URI items are the same file
SRC_URI[archive.md5sum] = "4136d7b4c04df68b686570afa26988ac"
SRC_URI[archive.sha256sum] = "591a9b4ec81c1f2042a97aa60564e0cb79d041c52faa7416acb38bc95bd2c76d"

