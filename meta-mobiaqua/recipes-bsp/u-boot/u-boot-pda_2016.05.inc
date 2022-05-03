HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native kern-tools-native"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
PE = "1"

inherit uboot-config uboot-extlinux-config uboot-sign deploy

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRCREV = "0db612a7b713ebcdf7ee350395037570f1f4fd24"

PV = "v2016.05+git${SRCPV}"

SRC_URI = "git://github.com/mobiaqua/bootloader.git;branch=master;protocol=git"

S = "${WORKDIR}/git/u-boot-pda"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" V=1'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE += 'STAGING_INCDIR=${STAGING_INCDIR_NATIVE} STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(pda-sa1110|pda-pxa250)"

PARALLEL_MAKE = ""

UBOOT_SUFFIX ??= "bin"
UBOOT_IMAGE = "u-boot-pda-sa1110-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET ?= "all"

do_configure () {
    oe_runmake -C ${S} O=${B} ARCH=arm ${UBOOT_MACHINE}
}

do_compile () {
    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    oe_runmake -C ${S} O=${B} ARCH=arm ${UBOOT_MAKE_TARGET}
    oe_runmake -C ${S} O=${B} tools HOSTCC="gcc"
}

do_install () {
    install -d ${D}/boot
    install -m 0644 ${B}/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}
}

FILES:${PN} = "/boot"

do_deploy () {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/${UBOOT_BINARY} ${DEPLOYDIR}/${UBOOT_IMAGE}
}

addtask deploy before do_build after do_compile
