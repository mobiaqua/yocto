require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "ncurses-native elf-native kmod-native"
COMPATIBLE_MACHINE = "(pda-sa1110|pda-pxa250)"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
LINUX_VERSION = "4.9.315"
PV = "${LINUX_VERSION}"
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-pda-4.9:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz \
           file://fix_nonlinux_compile.patch \
           file://h3600-fix-micro-port-mod.patch \
           file://h3600-fix-mtd-part.patch \
           file://h3600-fix-micro-keys.patch \
           file://fix-blocking-irq0.patch \
           file://defconfig \
          "

SRC_URI[sha256sum] = "e6c909958e6a5e6ab2f6bd8611def3bf551a03ba137d4f59785d02a041bae184"

S = "${WORKDIR}/linux-${PV}"
