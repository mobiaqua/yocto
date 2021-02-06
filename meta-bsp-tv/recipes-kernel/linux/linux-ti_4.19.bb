require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
COMPATIBLE_MACHINE = "board-tv"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LINUX_VERSION = "4.19.173"
PV = "${LINUX_VERSION}"
KERNEL_DEVICETREE_board-tv = "omap4-panda.dtb omap4-panda-es.dtb am57xx-beagle-x15-revc.dtb"
FILESEXTRAPATHS_prepend := "${THISDIR}/linux-ti_4.19:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${LINUX_VERSION}.tar.xz \
           file://fix_nonlinux_compile.patch \
           file://0001-bootup-hacks-move-mmc-early.patch \
           file://fixed_name_hdmi_audio.patch \
           file://smsc95xx-add-macaddr-module-parameter.patch \
           file://panda-bt-fixes.patch \
           file://0003-bootup-hacks-xor-select-neon-or-arm4regs.patch \
           file://reverse_zorder.patch \
           file://wait-for-rootfs.patch \
           file://remoteproc-rpmsg-omapdrm-gem-machomap2.patch \
           file://gpu-dts.patch \
           file://dra7.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "d2fdd236e6f31aa8e939f4a686c95417d8571bde3d091fc6a2535937c144195f"

S = "${WORKDIR}/linux-${PV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"

do_kernel_configme() {
    :
}
