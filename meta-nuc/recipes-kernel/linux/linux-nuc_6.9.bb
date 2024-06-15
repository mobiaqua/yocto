require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native linux-firmware wireless-regdb intel-microcode"
DEPENDS:remove = "util-linux-native elfutils-native"
COMPATIBLE_MACHINE:intel-x86-common = "${MACHINE}"
KMACHINE:corei7-64-intel-common = "intel-corei7-64"
KMACHINE:core2-32-intel-common = "intel-core2-32"
KMACHINE:x86-64-v3-intel-common = "intel-corei7-64"
KERNEL_FEATURES:remove = "cfg/efi.scc"
KERNEL_VERSION_SANITY_SKIP = "1"
INSANE_SKIP += "buildpaths"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.9.3"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-nuc_6.9:"
KBUILD_DEFCONFIG = "nuc_defconfig"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${LINUX_VERSION}.tar.xz \
           file://asm-posix_types.h \
           file://asm-types.h \
           file://byteswap.h \
           file://endian.h \
           file://0001_fix_nonlinux_compile.patch \
           file://0004_wait-for-rootfs.patch \
           file://nuc_config \
           "

SRC_URI[sha256sum] = "c321c46401368774fc236f57095b205a5da57415f9a6008018902f9fd5eddfae"

S = "${WORKDIR}/linux-${PV}"

kernel_do_configure:prepend() {
        install -m 644 ${WORKDIR}/asm-posix_types.h ${S}/tools/include/asm/posix_types.h
        install -m 644 ${WORKDIR}/asm-types.h ${S}/tools/include/asm/types.h
        install -m 644 ${WORKDIR}/byteswap.h ${S}/tools/include/byteswap.h
        install -m 644 ${WORKDIR}/byteswap.h ${S}/scripts/mod/byteswap.h
        install -m 644 ${WORKDIR}/endian.h ${S}/tools/include/endian.h
}

do_applypath() {
        sed 's|MOBIAQUA_FW_PATH|${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/|g' ${WORKDIR}/nuc_config > ${S}/arch/${ARCH}/configs/nuc_defconfig
}

addtask applypath before do_patch after do_unpack
