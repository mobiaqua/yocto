require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
COMPATIBLE_MACHINE = "board-tv"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LINUX_VERSION = "4.19.234"
PV = "${LINUX_VERSION}"
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:board-tv = "omap4-panda.dtb omap4-panda-es.dtb am57xx-beagle-x15-revc.dtb am5729-beagleboneai.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti_4.19:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${LINUX_VERSION}.tar.xz \
           file://ti-upstream.patch \
           file://fix_nonlinux_compile.patch \
           file://0003-Kbuild.include.patch \
           file://0001-bootup-hacks-move-mmc-early.patch \
           file://0003-bootup-hacks-xor-select-neon-or-arm4regs.patch \
           file://wait-for-rootfs.patch \
           file://smsc95xx-add-macaddr-module-parameter.patch \
           file://fixed_name_hdmi_audio.patch \
           file://gpu-dts.patch \
           file://panda-bt-fixes.patch \
           file://reverse_zorder.patch \
           file://gpu2d.patch \
           file://dra7-revert-timer.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "0082966be4a476c42ed59996e072e7e9fa5282b742efe4ffabadd6cd91212d8b"

S = "${WORKDIR}/linux-${PV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"
