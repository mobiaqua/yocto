HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
PROVIDES = "virtual/bootloader"
DEPENDS += "flex-native bison-native kern-tools-native openssl-native"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
PE = "1"

inherit uboot-config uboot-extlinux-config uboot-sign deploy kernel-arch

FILESEXTRAPATHS:prepend := "${THISDIR}/files/de10nano:${THISDIR}/files:"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "866ca972d6c3cabeaf6dbac431e8e08bb30b3c8e"

SRCREV_FORMAT = "hardware"

SRCREV_hardware = "d03450606b22a5f4f0d39da79fe169745ceffbec"

SRC_URI = "git://source.denx.de/u-boot/u-boot.git;protocol=https;branch=master \
           git://github.com/01org/de10-nano-hardware.git;destsuffix=hardware;name=hardware;branch=RELEASE_BUILDS;protocol=https \
           file://avoid-python2.patch \
           file://enable-i2c.patch \
           file://HSD-1507875164-1-arm-socfpga-Add-socfpga_sdram_apply.patch \
           file://HSD-1507875164-2-fpga-socfpga-Add-call-to-socfpga_sd.patch \
           file://HSD-1509758009-2-i2c-designware_i2c-Remove-clk-disab.patch \
           file://Revert-arm-cp15-update-DACR-value-to-activate-access.patch \
           file://arm-socfpga-Add-bsp-generator-scripts-with-qts-filte.patch \
           file://Add-DE10-Nano-HDMI-configuration-and-debug-apps.patch \
           file://fpga-hdmi-enable.patch \
           file://rtc-mod.patch \
          "

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" V=1'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE += 'STAGING_INCDIR=${STAGING_INCDIR_NATIVE} STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "de10nano"

UBOOT_MACHINE = "socfpga_de10_nano_defconfig"

SYSROOT_DIRS += "/boot"

UBOOT_SUFFIX = "sfp"
UBOOT_BINARY = "u-boot-with-spl.${UBOOT_SUFFIX}"
UBOOT_IMAGE = "u-boot-with-spl.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET ?= "all"

do_configure () {
    oe_runmake -C ${S} O=${B} ARCH=arm ${UBOOT_MACHINE}

#    ${S}/arch/arm/mach-socfpga/qts-filter.sh \
#        cyclone5 \
#        ${WORKDIR}/hardware/de0-nano/ \
#        ${WORKDIR}/hardware/de0-nano/preloader/ \
#        ${S}/board/terasic/de10-nano/qts/
}

do_compile () {
    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    oe_runmake -C ${S} O=${B} ARCH=arm ${UBOOT_MAKE_TARGET}
}

do_install () {
    install -D -m 644 ${B}/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}

    if [ "${UBOOT_EXTLINUX}" = "1" ]
    then
        install -D -m 644 ${UBOOT_EXTLINUX_CONFIG} ${D}/${UBOOT_EXTLINUX_INSTALL_DIR}/${UBOOT_EXTLINUX_CONF_NAME}
    fi
}

PACKAGE_BEFORE_PN += "${PN}-extlinux"

FILES:${PN}-extlinux = "${UBOOT_EXTLINUX_INSTALL_DIR}/${UBOOT_EXTLINUX_CONF_NAME}"
RDEPENDS:${PN} += "${@bb.utils.contains('UBOOT_EXTLINUX', '1', '${PN}-extlinux', '', d)}"

FILES:${PN} = "/boot"

do_deploy () {
    install -D -m 644 ${B}/${UBOOT_BINARY} ${DEPLOYDIR}/boot/${UBOOT_IMAGE}

    if [ "${UBOOT_EXTLINUX}" = "1" ]
    then
        install -D -m 644 ${UBOOT_EXTLINUX_CONFIG} ${DEPLOYDIR}/${UBOOT_EXTLINUX_INSTALL_DIR}/${UBOOT_EXTLINUX_CONF_NAME}
    fi
}

addtask deploy before do_build after do_compile
