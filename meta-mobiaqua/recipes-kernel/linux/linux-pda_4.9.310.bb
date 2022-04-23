require recipes-kernel/linux/linux-yocto.inc

#DEFAULT_PREFERENCE = "-1"
INC_PR = "r0"
DEPENDS += "ncurses-native elf-native kmod-native"
COMPATIBLE_MACHINE = "(pda-sa1110|pda-pxa250)"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
FILESEXTRAPATHS_prepend := "${THISDIR}/linux-pda-4.9:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v4.x/linux-${PV}.tar.xz \
           file://fix_nonlinux_compile.patch \
           file://h3600-fix-micro-port-mod.patch \
           file://h3600-fix-mtd-part.patch \
           file://h3600-fix-micro-keys.patch \
           file://fix-blocking-irq0.patch \
           file://defconfig \
          "

SRC_URI[sha256sum] = "85d3d93757f402d95ae3b34c52d8dc3b797d25f7f6ea0dcc405b9418968ea39d"

S = "${WORKDIR}/linux-${PV}"

do_kernel_configme() {
    :
}
