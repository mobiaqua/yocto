require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native linux-firmware"
DEPENDS:remove = "util-linux-native elfutils-native"
COMPATIBLE_MACHINE = "beagle64"
KERNEL_VERSION_SANITY_SKIP = "1"
INSANE_SKIP += "buildpaths"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.1.46"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:beagle64 = "ti/k3-j721e-beagleboneai64.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti64-up_6.1:"
KBUILD_DEFCONFIG = "ti_defconfig"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"

BRANCH = "ti-linux-6.1.y"

SRCREV = "2804e8ee9bf513f6a7fb54427469942a0e3d5a30"
PV = "6.1+git${SRCPV}"

KERNEL_GIT_URI = "git://git.ti.com/git/ti-linux-kernel/ti-linux-kernel.git"
KERNEL_GIT_PROTOCOL = "https"
SRC_URI += "${KERNEL_GIT_URI};protocol=${KERNEL_GIT_PROTOCOL};branch=${BRANCH} \
            file://0001_fix_nonlinux_compile.patch \
            file://0002-bootup-hacks-move-mmc-early.patch \
            file://0003-Kbuild.include.patch \
            file://0004_wait-for-rootfs.patch \
            file://0011_fix-mhdp-reg.patch \
            file://0014-WIP-tusb322.patch \
            file://0015_enable-snd-soc-hdmi-codec.patch \
            file://ti_config \
"

S = "${WORKDIR}/git"

do_applypath() {
    sed 's|MOBIAQUA_FW_PATH|${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/|g' ${WORKDIR}/ti_config > ${S}/arch/${ARCH}/configs/ti_defconfig
}

addtask applypath before do_patch after do_unpack
