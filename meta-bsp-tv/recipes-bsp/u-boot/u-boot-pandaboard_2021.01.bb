HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native kern-tools-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"
PE = "1"

inherit uboot-config uboot-extlinux-config uboot-sign deploy

FILESEXTRAPATHS_prepend := "${THISDIR}/files/pandaboard:${THISDIR}/files:"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "c4fddedc48f336eabc4ce3f74940e6aa372de18c"

SRC_URI = "git://git.denx.de/u-boot.git;branch=master \
           file://boot-panda-label.script \
           file://boot-panda-sdcard.script \
           file://boot-panda-nfs.script \
           file://boot-panda-nfs2.script \
           file://avoid-python2.patch \
          "

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" V=1'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE += 'STAGING_INCDIR=${STAGING_INCDIR_NATIVE} STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'

PACKAGE_ARCH = "${MACHINE_ARCH}"

UBOOT_MACHINE = "omap4_panda_defconfig"

COMPATIBLE_MACHINE = "board-tv"

UBOOT_SUFFIX ??= "bin"
UBOOT_IMAGE ?= "u-boot-panda-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET ?= "all"

MLO_IMAGE ?= "MLO-panda-${PV}-${PR}"

do_configure_prepend () {
    sed -i -e s,NFS_IP,${MA_NFS_IP},g ${WORKDIR}/boot-panda-nfs.script
    sed -i -e s,NFS_PATH,${MA_NFS_PATH},g ${WORKDIR}/boot-panda-nfs.script

    sed -i -e s,NFS_IP,${MA_NFS_IP},g ${WORKDIR}/boot-panda-nfs2.script
    sed -i -e s,NFS_PATH,${MA_NFS_PATH},g ${WORKDIR}/boot-panda-nfs2.script
    sed -i -e s,TARGET_IP,${MA_TARGET_IP},g ${WORKDIR}/boot-panda-nfs2.script
    sed -i -e s,GATEWAY_IP,${MA_GATEWAY_IP},g ${WORKDIR}/boot-panda-nfs2.script
    sed -i -e s,TARGET_MAC,${MA_TARGET_MAC},g ${WORKDIR}/boot-panda-nfs2.script

    sed -i -e s,TARGET_MAC,${MA_TARGET_MAC},g ${WORKDIR}/boot-panda-nfs.script
    sed -i -e s,TARGET_MAC,${MA_TARGET_MAC},g ${WORKDIR}/boot-panda-label.script
    sed -i -e s,TARGET_MAC,${MA_TARGET_MAC},g ${WORKDIR}/boot-panda-sdcard.script
}

do_configure () {
    oe_runmake -C ${S} O=${B} ARCH=arm ${UBOOT_MACHINE}
}

do_compile () {
    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    oe_runmake -C ${S} O=${B} ARCH=arm ${UBOOT_MAKE_TARGET}

    oe_runmake -C ${S} O=${B} tools env HOSTCC="gcc"
}

do_install () {
    install -d ${D}/boot
    install -m 0644 ${B}/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}

    install -m 0644 ${B}/MLO ${D}/boot/${MLO_IMAGE}

    install -m 0644 ${WORKDIR}/boot-panda-sdcard.script ${D}/boot/uEnv-panda-sdcard.txt
    install -m 0644 ${WORKDIR}/boot-panda-label.script ${D}/boot/uEnv-panda-label.txt
    install -m 0644 ${WORKDIR}/boot-panda-nfs.script ${D}/boot/uEnv-panda-nfs.txt
    install -m 0644 ${WORKDIR}/boot-panda-nfs2.script ${D}/boot/uEnv-panda-nfs2.txt
}

FILES_${PN} = "/boot"

do_deploy () {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/${UBOOT_BINARY} ${DEPLOYDIR}/${UBOOT_IMAGE}

    cd ${DEPLOYDIR}
    rm -f ${UBOOT_SYMLINK}

    install -m 0644 ${B}/MLO ${DEPLOYDIR}/${MLO_IMAGE}

    install -m 0644 ${WORKDIR}/boot-panda-sdcard.script ${DEPLOYDIR}/uEnv-panda-sdcard.txt
    install -m 0644 ${WORKDIR}/boot-panda-label.script ${DEPLOYDIR}/uEnv-panda-label.txt
    install -m 0644 ${WORKDIR}/boot-panda-nfs.script ${DEPLOYDIR}/uEnv-panda-nfs.txt
    install -m 0644 ${WORKDIR}/boot-panda-nfs2.script ${DEPLOYDIR}/uEnv-panda-nfs2.txt
}

addtask deploy before do_build after do_compile
