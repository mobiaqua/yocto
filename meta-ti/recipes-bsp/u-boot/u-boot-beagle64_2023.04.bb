HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
PROVIDES = "virtual/bootloader"
DEPENDS += "flex-native bison-native kern-tools-native openssl-native dtc-native"

# SYSFW/TIFS Firmware
DEPENDS:append:k3r5 = " ti-sci-fw-imggen"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
PE = "1"

inherit uboot-config uboot-extlinux-config uboot-sign deploy kernel-arch ti-secdev

DEFAULT_PREFERENCE = "-10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files/beagle64:${THISDIR}/files:"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "b0d717b732ee28e446baf94522b3491e590f7fbb"

SRC_URI = "git://git.ti.com/git/ti-u-boot/ti-u-boot.git;branch=ti-u-boot-2023.04;protocol=https \
           file://avoid-python2.patch \
           file://Revert-k3-tools-config.mk-Update-makefile-and-remove.patch \
           file://k3-force-local-bash.patch \
          "

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" V=1'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE += 'STAGING_INCDIR=${STAGING_INCDIR_NATIVE} STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "beagle64|beagle64r5"

DM_FIRMWARE = "ipc_echo_testb_mcu1_0_release_strip.xer5f"

PLAT_SFX = ""
PLAT_SFX:beagle64 = "j721e"

PACKAGECONFIG[atf] = "BL31=${STAGING_DIR_HOST}/firmware/bl31.bin,,trusted-firmware-a"
PACKAGECONFIG[optee] = "TEE=${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/bl32.bin,,optee-os"
PACKAGECONFIG[dm] = "DM=${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/ti-dm/${PLAT_SFX}/${DM_FIRMWARE},,ti-dm-fw"

PACKAGECONFIG:append:aarch64 = " atf optee"
PACKAGECONFIG:append:beagle64 = " dm"

EXTRA_OEMAKE += "${PACKAGECONFIG_CONFARGS}"

SYSROOT_DIRS += "/boot"

UBOOT_SUFFIX ??= "img"
UBOOT_IMAGE ??= "u-boot.${UBOOT_SUFFIX}"
UBOOT_BINARY ??= "u-boot.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET ?= "all"

do_configure () {
    oe_runmake -C ${S} O=${B} ARCH=arm ${UBOOT_MACHINE}
}

do_compile () {
    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    oe_runmake -C ${S} O=${B} ARCH=arm ${UBOOT_MAKE_TARGET}
}

do_install () {
    install -D -m 644 ${B}/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}
    if [ -n "${SPL_BINARY}" ]
    then
        install -m 644 ${B}/${SPL_BINARY} ${D}/boot/${SPL_IMAGE}
    fi

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
    if [ -n "${SPL_BINARY}" ]
    then
        install -m 644 ${B}/${SPL_BINARY} ${DEPLOYDIR}/boot/${SPL_IMAGE}
    fi

    if [ "${UBOOT_EXTLINUX}" = "1" ]
    then
        install -D -m 644 ${UBOOT_EXTLINUX_CONFIG} ${DEPLOYDIR}/${UBOOT_EXTLINUX_INSTALL_DIR}/${UBOOT_EXTLINUX_CONF_NAME}
    fi
}

addtask deploy before do_build after do_compile
