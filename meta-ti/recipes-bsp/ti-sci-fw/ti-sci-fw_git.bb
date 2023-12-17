SUMMARY = "TI SYSFW/TIFS Firmware"

LICENSE = "TI-TFL"
ERROR_QA:remove = "license-checksum"

PV = "1.0"
PR = "r0"

CLEANBROKEN = "1"

COMPATIBLE_MACHINE = "beagle64"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "9ee2fedb1fb4815f54310dd872d34faf9948c7c1"

SRC_URI = " \
        git://git.ti.com/git/processor-firmware/ti-linux-firmware.git;protocol=https;branch=ti-linux-firmware \
"

S = "${WORKDIR}/git"

do_install() {
}

inherit deploy

do_deploy () {
    install -d ${DEPLOYDIR}
    install -m 644 ${S}/ti-sysfw/ti-fs-firmware-j721e-gp.bin ${DEPLOYDIR}
}

addtask deploy before do_build after do_compile
