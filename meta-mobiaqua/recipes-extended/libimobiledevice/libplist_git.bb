SUMMARY = "A library to handle Apple Property List format whereas it's binary or XML"
HOMEPAGE = "https://github.com/libimobiledevice/libplist"
LICENSE = "GPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=ebb5c50ab7cab4baeffba14977030c07 \
                    file://COPYING.LESSER;md5=6ab17b41640564434dda85c06b7124f7"

# MobiAqua: removed "swig python3"
DEPENDS = "libxml2 glib-2.0"

# MobiAqua: removed "python3native python3targetconfig"
inherit autotools pkgconfig

PV = "2.6.0+git"

SRCREV = "e8791e2d8b1d1672439b78d31271a8cf74d6a16d"
SRC_URI = "git://github.com/libimobiledevice/libplist;protocol=https;branch=master"

S = "${WORKDIR}/git"

CVE_STATUS_GROUPS += "CVE_STATUS_LIBLIST"
CVE_STATUS_LIBLIST[status] = "fixed-version: The CPE in the NVD database doesn't reflect correctly the vulnerable versions."
CVE_STATUS_LIBLIST = " \
    CVE-2017-5834 \
    CVE-2017-5835 \
    CVE-2017-5836 \
"

do_configure:prepend() {
    rm -f ${S}/m4/ax_python_devel.m4
}

do_install:append () {
    if [ -e ${D}${PYTHON_SITEPACKAGES_DIR}/plist/_plist.so ]; then
        chrpath -d ${D}${PYTHON_SITEPACKAGES_DIR}/plist/_plist.so
    fi
}

PACKAGES =+ "${PN}-utils \
             ${PN}++ \
             ${PN}-python"

FILES:${PN} = "${libdir}/libplist-2.0${SOLIBS}"
FILES:${PN}++ = "${libdir}/libplist++-2.0${SOLIBS}"
FILES:${PN}-utils = "${bindir}/*"
FILES:${PN}-python = "${PYTHON_SITEPACKAGES_DIR}/*"
