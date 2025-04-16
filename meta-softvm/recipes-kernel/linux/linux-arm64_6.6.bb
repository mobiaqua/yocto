require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
DEPENDS:remove = "util-linux-native elfutils-native"
COMPATIBLE_MACHINE = "vm-*"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.6.87"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-arm64_6.6:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${LINUX_VERSION}.tar.xz \
           file://0001_fix_nonlinux_compile.patch \
           file://arm64-Support-the-TSO-memory-model.patch \
           file://cpu-ext.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "8957e5c2dacdbc47a16dbf1f6303ca7088409be6197a3881f752313275357ac6"

S = "${WORKDIR}/linux-${PV}"
