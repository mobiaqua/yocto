DESCRIPTION = "VPDMA firmware for Video Input Port and Video Processing Engine"

LICENSE = "TI-TSPA"
ERROR_QA:remove = "license-checksum"

# MobiAqua: use TI firmware repo
require recipes-bsp/ti-linux-fw/ti-linux-fw.inc

COMPATIBLE_MACHINE = "beagle"

PACKAGE_ARCH = "${MACHINE_ARCH}"

TARGET = "vpdma-1b8.bin"

do_install() {
    mkdir -p ${D}${nonarch_base_libdir}/firmware
    cp ${S}/ti/${TARGET} ${D}${nonarch_base_libdir}/firmware/${TARGET}
}

FILES:${PN} += "${nonarch_base_libdir}/firmware/${TARGET}"

PR = "r1"
