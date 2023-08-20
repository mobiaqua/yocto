DESCRIPTION = "x-loader for IGEP based platforms"
SECTION = "bootloader"
LICENSE = "GPL-2.0-or-later"
ERROR_QA:remove = "license-checksum"

DEPENDS = "signgp-native"
COMPATIBLE_MACHINE = "igep0030"
PARALLEL_MAKE=""
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCREV = "f91c5e54aa1b1f86ed247bcf0e887ae7d1ebb366"

SRC_URI = "git://github.com/mobiaqua/igep-x-loader.git;protocol=https;branch=master \
           file://igep.ini \
          "

S = "${WORKDIR}/git"

XLOAD_MACHINE = "igep00x0_config"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"

MLO_IMAGE ?= "MLO-${MACHINE}-${PV}-${PR}"
MLO_SYMLINK ?= "MLO"
MLO_SYMLINK_NOMACHINE ?= "MLO"

do_compile () {
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS
	oe_runmake distclean
	oe_runmake ${XLOAD_MACHINE}
	oe_runmake
}

do_install () {
	signGP ${B}/x-load.bin

	install -d ${D}/boot
	install -m 0644 ${B}/x-load.bin.ift ${D}/boot/${MLO_IMAGE}
	install -m 0644 ${WORKDIR}/igep.ini ${D}/boot/
}

FILES:${PN} = "/boot"

do_deploy () {
	signGP ${B}/x-load.bin

	install -d ${DEPLOYDIR}
	install -m 0644 ${B}/x-load.bin.ift ${DEPLOYDIR}/${MLO_IMAGE}
	install -m 0644 ${WORKDIR}/igep.ini ${DEPLOYDIR}/
}

addtask deploy before do_build after do_compile
