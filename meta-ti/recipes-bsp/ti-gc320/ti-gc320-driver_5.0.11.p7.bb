DESCRIPTION = "Kernel drivers for the Vivante GC320 chipset found in TI SoCs"
HOMEPAGE = "https://git.ti.com/graphics/ti-gc320-driver"
LICENSE = "MIT | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=78d9818a51b9a8e9bb89dea418bac297"

COMPATIBLE_MACHINE = "beagle"

inherit module

PR:append = "+gitr-${SRCREV}"
MACHINE_KERNEL_PR:append = "i"

DEPENDS = "linux-ti"

# Need to branch out with ${PV} var
BRANCH = "ti-${PV}-k5.10"

SRCREV = "849cfc1280821045d1ea6fc7339da914de01a0d1"

SRC_URI = "git://github.com/mobiaqua/ti-gc320-driver.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git/src"

EXTRA_OEMAKE += "-f Kbuild AQROOT=${S} KERNEL_DIR=${STAGING_KERNEL_DIR} TOOLCHAIN_PATH=${TOOLCHAIN_PATH} CROSS_COMPILE=${TARGET_PREFIX} ARCH_TYPE=${TARGET_ARCH}"

do_install() {
    install -d ${D}/${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 644 ${S}/galcore.ko ${D}/${base_libdir}/modules/${KERNEL_VERSION}/extra
}

INSANE_SKIP += "buildpaths"
