DESCRIPTION = "Userspace libraries for GC320 chipset on TI SoCs"
HOMEPAGE = "https://git.ti.com/graphics/ti-gc320-libs"
LICENSE = "TI-TSPA"
LIC_FILES_CHKSUM = "file://Manifest.html;md5=a9121e8936ace09820d23f7626daaca5"

COMPATIBLE_MACHINE = "beagle"

CLEANBROKEN = "1"

BRANCH = "ti-${PV}"

SRC_URI = "git://git.ti.com/graphics/ti-gc320-libs.git;protocol=git;branch=${BRANCH}"
SRCREV = "c0afab259de59909cfe74c01f3f7fbaa147f94b5"

TARGET_PRODUCT = "jacinto6evm"

PR = "r1"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "DESTDIR=${D} TARGET_PRODUCT=${TARGET_PRODUCT} LIBDIR=${libdir}"

do_install() {
    oe_runmake install
    ln -s libGAL.so.5.0.11.p7 ${D}${libdir}/libGAL.so.5
}

INSANE_SKIP:${PN} += "ldflags"
