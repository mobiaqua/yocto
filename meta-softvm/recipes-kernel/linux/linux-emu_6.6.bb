require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
DEPENDS:remove = "util-linux-native elfutils-native"
COMPATIBLE_MACHINE:intel-x86-common = "${MACHINE}"
KMACHINE:corei7-64-intel-common = "intel-corei7-64"
KMACHINE:core2-32-intel-common = "intel-core2-32"
KMACHINE:x86-64-v3-intel-common = "intel-corei7-64"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.6.32"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-emu_6.6:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${LINUX_VERSION}.tar.xz \
           file://asm-posix_types.h \
           file://asm-types.h \
           file://byteswap.h \
           file://endian.h \
           file://0001_fix_nonlinux_compile.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "aaa824eaf07f61911d22b75ff090a403c3dd0bd73e23933e0bba8b5971436ce1"

S = "${WORKDIR}/linux-${PV}"

EXTRA_OEMAKE:append = " KCFLAGS=-Wa,-mx86-used-note=no"

kernel_do_configure:prepend() {
        install -m 644 ${WORKDIR}/asm-posix_types.h ${S}/tools/include/asm/posix_types.h
        install -m 644 ${WORKDIR}/asm-types.h ${S}/tools/include/asm/types.h
        install -m 644 ${WORKDIR}/byteswap.h ${S}/tools/include/byteswap.h
        install -m 644 ${WORKDIR}/endian.h ${S}/tools/include/endian.h
}
