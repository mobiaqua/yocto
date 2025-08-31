SUMMARY =  "Kernel drivers for the PowerVR SGX chipset found in the TI SoCs"
LICENSE = "MIT | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e"

inherit module

PROVIDES = "virtual/gpudriver"

COMPATIBLE_MACHINE = "(beagle|panda|igep0030)"

PR = "r0"
PV = "1.17"
PR:append = "+gitr-${SRCREV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

INSANE_SKIP += "buildpaths"

DEPENDS = "libdrm virtual/kernel"

SRC_URI = "git://github.com/mobiaqua/sgx-pvr5-module.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

SRCREV = "7589b68513a1179f800128340ee7d5278dee1da9"

TARGET_PRODUCT:panda = "ti443x"
TARGET_PRODUCT:beagle = "ti572x"
TARGET_PRODUCT:igep0030 = "ti335x"

PVR_BUILD = "release"
PVR_WS = "lws-generic"

EXTRA_OEMAKE += 'KERNELDIR="${STAGING_KERNEL_DIR}" BUILD=${PVR_BUILD} \
WINDOW_SYSTEM=${PVR_WS} PVR_BUILD_DIR=${TARGET_PRODUCT}_linux TARGET_PRODUCT=${TARGET_PRODUCT}'

# There are useful flags here that are interpreted by the final kbuild pass
# These variables are not necessary when compiling outside of Yocto
export KERNEL_CC
export KERNEL_LD
export KERNEL_AR
export KERNEL_OBJCOPY
export KERNEL_STRIP

do_install() {
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
	make -C ${STAGING_KERNEL_DIR} M=${B}/eurasiacon/binary_${TARGET_PRODUCT}_linux_${PVR_WS}_${PVR_BUILD}/target_armhf/kbuild INSTALL_MOD_PATH=${D}${root_prefix} PREFIX=${STAGING_DIR_HOST} modules_install
	cp ${S}/eurasiacon/binary_${TARGET_PRODUCT}_linux_${PVR_WS}_${PVR_BUILD}/target_armhf/kbuild/omapdrm_pvr_${TARGET_PRODUCT}.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
}
