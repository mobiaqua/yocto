DESCRIPTION = "VPDMA firmware for Video Input Port and Video Processing Engine"

LICENSE = "TI-TSPA"
ERROR_QA:remove = "license-checksum"

COMPATIBLE_MACHINE = "beagle"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS += "virtual/kernel"

SRC_URI = "file://vpdma-1b8.bin \
           file://LICENCE.ti-vpdma \
          "

S = "${WORKDIR}"

do_compile() {
    :
}

TARGET = "vpdma-1b8.bin"

do_install() {
    mkdir -p ${D}${base_libdir}/firmware
    install -m 0644 ${S}/${TARGET} ${D}${base_libdir}/firmware/${TARGET}
    install -m 0644 ${S}/LICENCE.ti-vpdma ${D}${base_libdir}/firmware/LICENCE.ti-vpdma
}

FILES:${PN} += "${base_libdir}/firmware/${TARGET} ${base_libdir}/firmware/LICENCE.ti-vpdma"

PR = "r1"
