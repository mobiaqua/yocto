SUMMARY = "binfmt-support, tools for managing executable binary formats"
DESCRIPTION = "binfmt-support, tools for managing executable binary formats"
HOMEPAGE = "https://nongnu.org/binfmt-support/"
SECTION = "base"
LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

DEPENDS = "libpipeline"

SRCREV = "bf39b502dc0cca26713180dbe8db0e228b10fae8"
SRC_URI = "git://gitlab.com/cjwatson/binfmt-support.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

PV = "2.2.3+git"

inherit autotools pkgconfig

do_configure:prepend () {
    ( cd ${S} && ./bootstrap )
}

do_install:append() {
    rm -rf ${D}${datadir}
}
