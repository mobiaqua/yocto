require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
COMPATIBLE_MACHINE = "board-tv"
KERNEL_VERSION_SANITY_SKIP = "1"
LINUX_VERSION = "4.9.249"
PV = "${LINUX_VERSION}"
KERNEL_DEVICETREE_board-tv = "omap3-igep0030.dtb omap4-panda.dtb omap4-panda-es.dtb omap5-igep0050.dtb"
FILESEXTRAPATHS_prepend := "${THISDIR}/linux-omap:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${LINUX_VERSION}.tar.xz \
           file://fix_nonlinux_compile.patch \
           file://0001-bootup-hacks-move-mmc-early.patch \
           file://fixed_name_hdmi_audio.patch \
           file://smsc95xx-add-macaddr-module-parameter.patch \
           file://smsc75xx-add-macaddr-module-parameter.patch \
           file://panda-bt-fixes.patch \
           file://omap5-igep0050-dts-fixes.patch \
           file://0003-bootup-hacks-xor-select-neon-or-arm4regs.patch \
           file://0002-HACK-PandaBoard-Bring-back-twl6030-clk32kg-regulator.patch \
           file://reverse_zorder.patch \
           file://wait-for-rootfs.patch \
           file://hdmi-fixed-err-handle.patch \
           file://remoteproc-and-rpmsg.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "b1f38696ff5bce89e1d8248126aa2a8ce5b19100efdd68f1d335a9f736e04c4e"

S = "${WORKDIR}/linux-${PV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"

do_kernel_configme() {
    :
}
