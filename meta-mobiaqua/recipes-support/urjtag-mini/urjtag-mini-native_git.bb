DESCRIPTION = "This is minimalised and modified version of UrJTAG is a universal JTAG tool"
HOMEPAGE = "http://urjtag.org/"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
DEPENDS = "libftdi1-native libusb1-native gettext-native readline-native pkgconfig-native"

SRCREV = "ac3c214d888fe036315ddb819ade80e73fe439eb"

PV = "0.10"
PR = "r1"
PR:append = "+gitr${SRCPV}"

S = "${WORKDIR}/git/urjtag-mini"

SRC_URI = "git://github.com/mobiaqua/tools;protocol=https;branch=master \
          "

inherit autotools native

# no idea why -s would make a difference but without it configure fails.
# guess the symlink is created before the actual content is there
EXTRA_AUTORECONF = "-s"

EXTRA_OECONF = "--enable-jedec-exp"

do_install () {
        oe_runmake DESTDIR=${D} MKINSTALLDIRS="${S}/tools/mkinstalldirs" install
}

EXTRA_OECONF:append:class-native = " --enable-shared=no"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no"
