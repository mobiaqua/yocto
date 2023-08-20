SUMMARY = "Video Decoding Firmware"

LICENSE = "TI-TFL"
ERROR_QA:remove = "license-checksum"

PV = "1.0"
PR = "r0"

CLEANBROKEN = "1"

COMPATIBLE_MACHINE = "beagle64"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "79c498ef6ab1558b699a2243a26c4a65a1c44d26"

BRANCH ?= "ti-linux-firmware"

SRC_URI = " \
	git://git.ti.com/git/processor-firmware/ti-linux-firmware.git;protocol=https;branch=${BRANCH} \
"

S = "${WORKDIR}/git"

do_install() {
        install -d ${D}${nonarch_base_libdir}/firmware
        install -m 0644 ${S}/ti-img/pvdec_full_bin.fw ${D}${nonarch_base_libdir}/firmware/pvdec_full_bin.fw
}

FILES:${PN} = "${nonarch_base_libdir}/firmware"
