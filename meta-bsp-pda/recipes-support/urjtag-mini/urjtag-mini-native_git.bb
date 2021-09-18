DESCRIPTION = "This is minimalised and modified version of UrJTAG is a universal JTAG tool"
HOMEPAGE = "http://urjtag.org/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
DEPENDS = "libftdi1-native libusb1-native gettext-native readline-native"

SRCREV = "67bc2eca0d7c7df81dea03282ef6e31c5215a339"

PV = "0.10"
PR = "r1"
PR_append = "+gitr${SRCPV}"

S = "${WORKDIR}/git/urjtag-mini"

SRC_URI = "git://github.com/mobiaqua/tools;protocol=git;branch=master \
          "

inherit autotools native

# no idea why -s would make a difference but without it configure fails.
# guess the symlink is created before the actual content is there
EXTRA_AUTORECONF = "-s"

EXTRA_OECONF = "--enable-jedec-exp"

do_install () {
        oe_runmake DESTDIR=${D} MKINSTALLDIRS="${S}/tools/mkinstalldirs" install
}

EXTRA_OECONF_append_class-native = " --enable-shared=no"
EXTRA_OECONF_append_class-nativesdk = " --enable-shared=no"
