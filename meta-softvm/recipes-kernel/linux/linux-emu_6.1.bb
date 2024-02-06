require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
COMPATIBLE_MACHINE:intel-x86-common = "${MACHINE}"
KMACHINE:corei7-64-intel-common = "intel-corei7-64"
KMACHINE:core2-32-intel-common = "intel-core2-32"
KMACHINE:x86-64-v3-intel-common = "intel-corei7-64"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.1.77"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-emu_6.1:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${LINUX_VERSION}.tar.xz \
           file://asm-posix_types.h \
           file://asm-types.h \
           file://byteswap.h \
           file://endian.h \
           file://0001_fix_nonlinux_compile.patch \
           file://0003-Kbuild.include.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "3b54ec567716cdfb3618caf38c58a8aab1372cc41c16430633febe9ccdb3f91d"

S = "${WORKDIR}/linux-${PV}"

EXTRA_OEMAKE:append = " KCFLAGS=-Wa,-mx86-used-note=no"

kernel_do_configure:prepend() {
        install -m 644 ${WORKDIR}/asm-posix_types.h ${S}/tools/include/asm/posix_types.h
        install -m 644 ${WORKDIR}/asm-types.h ${S}/tools/include/asm/types.h
        install -m 644 ${WORKDIR}/byteswap.h ${S}/tools/include/byteswap.h
        install -m 644 ${WORKDIR}/endian.h ${S}/tools/include/endian.h
}
