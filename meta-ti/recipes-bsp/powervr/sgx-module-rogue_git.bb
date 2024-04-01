DESCRIPTION =  "Kernel drivers for the PowerVR Rogue GPU found in the TI SoCs"
HOMEPAGE = "http://git.ti.com/graphics/ti-img-rogue-driver"
LICENSE = "MIT | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README;beginline=14;endline=19;md5=0403c7dea01a2b8232261e805325fac2"

inherit module

PROVIDES = "virtual/gpudriver"

COMPATIBLE_MACHINE = "beagle64"

PR = "r0"
PV = "23.2"
PR:append = "+gitr-${SRCREV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "libdrm virtual/kernel"

BRANCH = "linuxws/kirkstone/k6.1/23.3.6512818"

SRC_URI = "git://git.ti.com/git/graphics/ti-img-rogue-driver.git;protocol=https;branch=${BRANCH} \
          "

S = "${WORKDIR}/git"

SRCREV = "c89c1efa4a1ee5da64fd525f45e9e33728cf6181"

TARGET_PRODUCT:beagle64 = "j721e_linux"
PVR_BUILD = "release"
PVR_WS = "lws-generic"

EXTRA_OEMAKE += 'KERNELDIR="${STAGING_KERNEL_DIR}" BUILD=${PVR_BUILD} PVR_BUILD_DIR=${TARGET_PRODUCT} WINDOW_SYSTEM=${PVR_WS}'

do_install() {
    make -C ${STAGING_KERNEL_DIR} M=${B}/binary_${TARGET_PRODUCT}_${PVR_WS}_${PVR_BUILD}/target_aarch64/kbuild INSTALL_MOD_PATH=${D}${root_prefix} PREFIX=${STAGING_DIR_HOST} modules_install
}
