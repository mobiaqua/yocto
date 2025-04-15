DESCRIPTION =  "Kernel drivers for the PowerVR Rogue GPU found in the TI SoCs"
HOMEPAGE = "http://git.ti.com/graphics/ti-img-rogue-driver"
LICENSE = "MIT | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README;beginline=14;endline=19;md5=0403c7dea01a2b8232261e805325fac2"

inherit module

PROVIDES = "virtual/gpudriver"

PR = "r0"
PV = "24.2"
PR:append = "+gitr-${SRCREV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

INSANE_SKIP += "buildpaths"

COMPATIBLE_MACHINE = "beagle64"

DEPENDS = "virtual/kernel"

BRANCH = "linuxws/scarthgap/k6.12/24.2.6643903"

SRC_URI = "git://git.ti.com/git/graphics/ti-img-rogue-driver.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"

SRCREV = "8eaff654a8871118c08cfafe53795f57e3b6b396"

TARGET_PRODUCT:beagle64 = "j721e_linux"
PVR_BUILD = "release"
PVR_WS = "lws-generic"

EXTRA_OEMAKE += 'KERNELDIR="${STAGING_KERNEL_DIR}" BUILD=${PVR_BUILD} PVR_BUILD_DIR=${TARGET_PRODUCT} WINDOW_SYSTEM=${PVR_WS}'

do_install() {
    make -C ${STAGING_KERNEL_DIR} M=${B}/binary_${TARGET_PRODUCT}_${PVR_WS}_${PVR_BUILD}/target_aarch64/kbuild INSTALL_MOD_PATH=${D}${root_prefix} PREFIX=${STAGING_DIR_HOST} modules_install
}
