SUMMARY = "Firmware for OMAP4 and OMAP5 ABE"
HOMEPAGE = "http://git.ti.com"
LICENSE = "BSD | GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=923db086ed9463ab3215b24d87e05ec5"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "panda"

SRC_URI = "git://git.ti.com/glsdk/abefw-omap4plus.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

SRCREV = "ceccc0332264e39bdc51e54f80ea7256a3886c58"

PR = "r0"

do_install() {
        mkdir -p ${D}/lib/firmware
        install -m 644 ${S}/firmware/omap4_abe_new ${D}/lib/firmware/
}

FILES:${PN} += "/lib/firmware/omap4_abe_new"
