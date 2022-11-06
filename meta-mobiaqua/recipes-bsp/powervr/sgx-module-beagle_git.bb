DESCRIPTION = "Kernel drivers for the PowerVR SGX 5 Series chipset found in the TI devices"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e"

COMPATIBLE_MACHINE = "board-tv"

PR = "r0"
PV = "1.17"
PR:append = "+gitr-${SRCREV}"

DEPENDS = "libdrm linux-ti"

SRCREV = "561d9a51ab71202df99b9c31bae0edffa7cedca5"

SRC_URI = "git://github.com/mobiaqua/sgx-pvr5-module.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit module

MACHINE_KERNEL_PR:append = "a"

MAKE_TARGETS = "-C eurasiacon/build/linux2/omap_linux BUILD=release TARGET_PRODUCT=beaglex15 KERNELDIR=${STAGING_KERNEL_DIR}"

do_install() {
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
	cp ${S}/eurasiacon/binary2_omap_linux_release/target_armhf/kbuild/omapdrm_pvr_beaglex15.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
}
