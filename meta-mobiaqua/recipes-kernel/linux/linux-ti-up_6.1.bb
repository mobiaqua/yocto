require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
COMPATIBLE_MACHINE = "beagle64"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.1.26"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:panda = "omap4-panda.dtb omap4-panda-es.dtb"
KERNEL_DEVICETREE:beagle = "am57xx-beagle-x15-revc.dtb am5729-beagleboneai.dtb"
KERNEL_DEVICETREE:beagle64 = "ti/k3-j721e-beagleboneai64.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti-up_6.1:"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"

BRANCH = "ti-linux-6.1.y-wip"

SRCREV = "634c15ad5831e9dd5a5ac1294bae49e3020e2b15"
PV = "6.1+git${SRCPV}"

KERNEL_GIT_URI = "git://git.ti.com/git/ti-linux-kernel/ti-linux-kernel.git"
KERNEL_GIT_PROTOCOL = "https"
SRC_URI += "${KERNEL_GIT_URI};protocol=${KERNEL_GIT_PROTOCOL};branch=${BRANCH} \
            file://0001_fix_nonlinux_compile.patch \
            file://0002-bootup-hacks-move-mmc-early.patch \
            file://0003-Kbuild.include.patch \
            file://0004_wait-for-rootfs.patch \
            file://0010_j721e-beagleboneai64.patch \
            file://0011_fix-mhdp-reg.patch \
            file://0012-Enable-eQEP-for-TDA4VM-J721E.patch \
            file://0013-v4l-vxd-dec-Flushing-all-the-IO-buffers-and-releasin.patch \
            file://0014-WIP-tusb322.patch \
            file://0015_enable-snd-soc-hdmi-codec.patch \
            file://defconfig \
"

S = "${WORKDIR}/git"
