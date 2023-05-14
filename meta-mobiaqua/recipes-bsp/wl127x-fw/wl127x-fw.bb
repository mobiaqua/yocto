DESCRIPTION = "Firmware files for Bluetooth and Wifi"
LICENSE = "TI-TSPA"
ERROR_QA:remove = "license-checksum"

PV = "1.0"
PR = "r0"

COMPATIBLE_MACHINE = "panda"

SRC_URI = "file://TIInit_7.2.31.bts \
           file://TIInit_7.6.15.bts \
           file://wl127x-fw-3.bin \
           file://wl127x-fw-4-mr.bin \
           file://wl127x-fw-4-plt.bin \
           file://wl127x-fw-4-sr.bin \
           file://wl127x-fw-5-mr.bin \
           file://wl127x-fw-5-plt.bin \
           file://wl127x-fw-5-sr.bin \
           file://wl127x-fw-plt-3.bin \
           file://wl127x-nvs.bin \
           file://LICENCE.ti-connectivity \
"

S = "${WORKDIR}"

do_compile() {
    :
}

do_install() {
    install -d ${D}${base_libdir}/firmware/ti-connectivity
    install -m 0644 ${S}/*.bts ${D}${base_libdir}/firmware/ti-connectivity/
    install -m 0644 ${S}/*.bin ${D}${base_libdir}/firmware/ti-connectivity/
    ln -s wl127x-nvs.bin ${D}${base_libdir}/firmware/ti-connectivity/wl1271-nvs.bin
    install -m 0644 ${S}/LICENCE.ti-connectivity ${D}${base_libdir}/firmware/ti-connectivity/
}

FILES:${PN} += "${base_libdir}/firmware/ti-connectivity/*"
