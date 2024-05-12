DESCRIPTION = "Firmware files for Bluetooth and Wifi"
LICENSE = "Proprietary"
ERROR_QA:remove = "license-checksum"

PV = "1.0"
PR = "r0"

COMPATIBLE_MACHINE = "beagle"

inherit update-rc.d

INITSCRIPT_NAME = "beagle-wireless"
INITSCRIPT_PARAMS = "start 03 S ."

SRC_URI = "\
           file://cyfmac43455-sdio.beagle,am5729-beagleboneai.bin \
           file://cyfmac43455-sdio.beagle,am5729-beagleboneai.txt \
           file://cyfmac43455-sdio.clm_blob \
           file://BCM4345C0.hcd \
           file://LICENCE.txt \
           file://beagle-wireless-init \
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

do_install:append:beagle() {
    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/beagle-wireless-init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    fi

    if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/beagle-wireless-init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
        install -d ${D}${sysconfdir}/rcS.d
        ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S03${INITSCRIPT_NAME}
    fi
}

FILES:${PN} += "${base_libdir}/firmware/cypress/* ${sysconfdir}/firmware/* ${sysconfdir}"
