SUMMARY = "Video Decoding Firmware"

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

TARGET = "pvdec_full_bin.fw"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/ti-img/${TARGET} ${D}${nonarch_base_libdir}/firmware/${TARGET}
}

FILES:${PN} = "${nonarch_base_libdir}/firmware"

# MobiAqua: added deploy step
inherit deploy

do_deploy () {
}

addtask deploy before do_build after do_compile
