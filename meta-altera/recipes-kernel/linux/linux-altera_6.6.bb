require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
DEPENDS:remove = "util-linux-native elfutils-native"
COMPATIBLE_MACHINE = "de10nano"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.6.63"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:de10nano = "intel/socfpga/socfpga_cyclone5_de10_nano.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-altera_6.6:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${LINUX_VERSION}.tar.xz \
           file://0001_fix_nonlinux_compile.patch \
           file://0002-bootup-hacks-move-mmc-early.patch \
           file://0004_wait-for-rootfs.patch \
           file://0010-Add-de10-nano-DT.patch \
           file://0011-FogBugz-184646-Turn-on-all-peripheral-clocks-for-a-s.patch \
           file://0012-FogBugz-365525-3-socfpga-dts-remove-fpga-image.patch \
           file://0013-i2c-designware-introduce-a-custom-scl-recovery-for-S.patch \
           file://0014-arm-dts-socfpga-use-the-intel-socfpga-i2c-binding.patch \
           file://0015-Add-cpufreq-overclock-driver-34.patch \
           file://0020-FogBugz-398879-2-fpga-mgr-debugfs.patch \
           file://0030-OF-DT-Overlay-configfs-interface-v7.patch \
           file://0031-kbuild-Enable-DT-symbols-when-CONFIG_OF_OVERLAY-is-u.patch \
           file://0200-Fix-for-unaligned-IN-data.-57.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "d1054ab4803413efe2850f50f1a84349c091631ec50a1cf9e891d1b1f9061835"

S = "${WORKDIR}/linux-${PV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"
