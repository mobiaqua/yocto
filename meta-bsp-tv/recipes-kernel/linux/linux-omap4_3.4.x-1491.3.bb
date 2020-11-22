KBRANCH ?= "ti-ubuntu-3.4-stable"

require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEPENDS += "openssl-native ncurses-native elf-native kmod-native"

COMPATIBLE_MACHINE = "board-tv"

KERNEL_VERSION_SANITY_SKIP = "1"

LINUX_VERSION = "3.4.113"

PV = "${LINUX_VERSION}+git${SRCPV}"

SRCREV = "ti-ubuntu-3.4.0-1491.3"

SRC_URI = "git://dev.omapzoom.org/pub/scm/integration/kernel-ubuntu.git;branch=${KBRANCH}; \
           file://fix_nonlinux_compile.patch \
           file://patch-3.4.103.patch \
           file://patch-3.4.103-104.patch \
           file://patch-3.4.104-105.patch \
           file://patch-3.4.105-106.patch \
           file://patch-3.4.106-107.patch \
           file://patch-3.4.107-108.patch \
           file://patch-3.4.108-109.patch \
           file://patch-3.4.109-110.patch \
           file://patch-3.4.110-111.patch \
           file://patch-3.4.111-112.patch \
           file://patch-3.4.112-113.patch \
           file://not_use_IEC958_AES1_PRO_MODE_NOTID.patch \
           file://DM_INH_and_CA.patch \
           file://set_buffer_bytes.patch \
           file://zorder.patch \
           file://reverse_zorder.patch \
           file://fixed-drm-flags.patch \
           file://fixed-bt-mux.patch \
           file://rpmsg-new-ns.patch \
           file://fix-log2.patch \
           file://fix-uaccess.patch \
           file://avoid-warning-as-error.patch \
           file://defconfig \
           "

do_kernel_configme() {
    :
}

do_configure_prepend() {
    cp ${S}/include/linux/compiler-gcc5.h ${S}/include/linux/compiler-gcc9.h
}
