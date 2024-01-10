DESCRIPTION = "Kernel drivers for the PowerVR SGX 5 Series chipset found in the TI devices"
LICENSE = "MIT | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e"

inherit module

COMPATIBLE_MACHINE = "beagle"

PR = "r0"
PV = "1.17"
PR:append = "+gitr-${SRCREV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "libdrm virtual/kernel"

SRC_URI = "git://github.com/mobiaqua/sgx-pvr5-module.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

SRCREV = "0a6e21ebc6b2da9aa36be81b121848c92b03fe46"

BUILD_TYPE = "release"

MAKE_TARGETS = "-C eurasiacon/build/linux2/omap_linux BUILD=${BUILD_TYPE} TARGET_PRODUCT=ti572x KERNELDIR=${STAGING_KERNEL_DIR}"

do_install() {
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
	cp ${S}/eurasiacon/binary2_omap_linux_${BUILD_TYPE}/target_armhf/kbuild/omapdrm_pvr_ti572x.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
}
