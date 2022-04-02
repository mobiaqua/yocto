DESCRIPTION = "Kernel drivers for the PowerVR SGX 5 Series chipset found in the TI devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e"

COMPATIBLE_MACHINE = "board-tv"

PR = "r0"
PV = "1.17"
PR_append = "+gitr-${SRCREV}"

DEPENDS = "libdrm linux-ti"

SRCREV = "5f27e30d2011e7b9031bf83ba1701cc3d7ef4a4a"

SRC_URI = "git://github.com/mobiaqua/sgx-pvr5-module.git;protocol=git"

S = "${WORKDIR}/git"

inherit module

MACHINE_KERNEL_PR_append = "a"

MAKE_TARGETS = "-C eurasiacon/build/linux2/omap_linux BUILD=release TARGET_PRODUCT=beaglex15 KERNELDIR=${STAGING_KERNEL_DIR}"

do_install() {
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
	cp ${S}/eurasiacon/binary2_omap_linux_release/target_armhf/kbuild/omapdrm_pvr_beaglex15.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
}
