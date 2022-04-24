DESCRIPTION = "Firmware files for Bluetooth and Wifi"
LICENSE = "Proprietary"
ERROR_QA_remove = "license-checksum"

PV = "1.0"
PR = "r0"

COMPATIBLE_MACHINE = "board-tv"

SRC_URI = "\
           file://cyfmac43455-sdio.beagle,am5729-beagleboneai.bin \
           file://cyfmac43455-sdio.beagle,am5729-beagleboneai.txt \
           file://cyfmac43455-sdio.clm_blob \
           file://BCM4345C0.hcd \
           file://LICENCE.txt \
"

S = "${WORKDIR}"

do_compile() {
    :
}

do_install() {
    install -d ${D}${base_libdir}/firmware/cypress
    install -m 0644 ${S}/*.txt ${D}${base_libdir}/firmware/cypress/
    install -m 0644 ${S}/*.bin ${D}${base_libdir}/firmware/cypress/
    install -m 0644 ${S}/*.clm_blob ${D}${base_libdir}/firmware/cypress/
    install -m 0644 ${S}/LICENCE.txt ${D}${base_libdir}/firmware/cypress/
    install -d ${D}${sysconfdir}/firmware
    install -m 0644 ${S}/BCM4345C0.hcd ${D}${sysconfdir}/firmware/
}

FILES_${PN} += "${base_libdir}/firmware/cypress/* ${sysconfdir}/firmware/*"
