DESCRIPTION =  "Kernel drivers for the PowerVR Rogue GPU found in the TI SoCs"
HOMEPAGE = "http://git.ti.com/graphics/ti-img-rogue-driver"
LICENSE = "MIT | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://README;beginline=14;endline=19;md5=0403c7dea01a2b8232261e805325fac2"

inherit module

COMPATIBLE_MACHINE = "beagle64"

PR = "r0"
PV = "23.1"
PR:append = "+gitr-${SRCREV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "libdrm virtual/kernel"

BRANCH = "linuxws/kirkstone/k6.1/23.2.6460340"

SRC_URI = "git://git.ti.com/git/graphics/ti-img-rogue-driver.git;protocol=https;branch=${BRANCH} \
           file://fix_make.patch \
          "

S = "${WORKDIR}/git"

SRCREV = "2595524729c5acbabcbdee2185de64e7fcd6b36a"

EXTRA_OEMAKE += 'KERNELDIR="${STAGING_KERNEL_DIR}" BUILD=release PVR_BUILD_DIR=j721e_linux WINDOW_SYSTEM=lws-generic'

do_install() {
	make -C ${STAGING_KERNEL_DIR} M=${B}/binary_j721e_linux_lws-generic_release/target_aarch64/kbuild INSTALL_MOD_PATH=${D}${root_prefix} PREFIX=${STAGING_DIR_HOST} modules_install
}
