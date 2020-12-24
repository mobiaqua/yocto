require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"

#LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEPENDS += "openssl-native ncurses-native elf-native kmod-native"

COMPATIBLE_MACHINE = "board-tv"

KERNEL_VERSION_SANITY_SKIP = "1"

LINUX_VERSION = "4.4.230"

KERNEL_DEVICETREE_board-tv = "omap3-igep0030.dtb omap4-panda.dtb omap4-panda-es.dtb omap5-igep0050.dtb"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-omap:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz \
           file://0001-bootup-hacks-move-mmc-early.patch \
           file://fixed_name_hdmi_audio.patch \
           file://smsc95xx-add-macaddr-module-parameter.patch \
           file://smsc75xx-add-macaddr-module-parameter.patch \
           file://panda-bt-fixes.patch \
           file://omap5-igep0050-dts-fixes.patch \
           file://0003-bootup-hacks-xor-select-neon-or-arm4regs.patch \
           file://0002-HACK-PandaBoard-Bring-back-twl6030-clk32kg-regulator.patch \
           file://0004-drm-omap-Add-omapdrm-plugin-API.patch \
           file://fixed-drm-flags.patch \
           file://reverse_zorder.patch \
           file://wait-for-rootfs.patch \
           file://omapdce.patch \
           file://Kconfig \
           file://Makefile \
           file://dce.c \
           file://dce_rpc.h \
           file://omap_dce.h \
           file://defconfig \
           "

SRC_URI[sha256sum] = "ca3161a6278dd86633883cc48f1476f76c1f583a2fceb366164c7a142926a7e3"

S = "${WORKDIR}/linux-${PV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"

do_configure_prepend () {
	mkdir -p ${S}/drivers/staging/omapdce
	cp ${WORKDIR}/Kconfig ${S}/drivers/staging/omapdce
	cp ${WORKDIR}/Makefile ${S}/drivers/staging/omapdce
	cp ${WORKDIR}/dce.c ${S}/drivers/staging/omapdce
	cp ${WORKDIR}/dce_rpc.h ${S}/drivers/staging/omapdce
	cp ${WORKDIR}/omap_dce.h ${S}/drivers/staging/omapdce
}

do_kernel_configme() {
    :
}
