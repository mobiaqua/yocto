HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native kern-tools-native"
PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
PE = "1"

inherit uboot-config uboot-extlinux-config uboot-sign deploy

FILESEXTRAPATHS_prepend := "${THISDIR}/files/beagleboard:${THISDIR}/files:"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "36fec02b1f90b92cf51ec531564f9284eae27ab4"

SRC_URI = "git://git.denx.de/u-boot.git \
           file://boot-beagle-label.script \
           file://boot-beagle-sdcard.script \
           file://boot-beagle-nfs.script \
           file://boot-beagle-nfs2.script \
           file://0001-am57xx_evm-fixes.patch \
          "

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" V=1'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE += 'STAGING_INCDIR=${STAGING_INCDIR_NATIVE} STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'

PACKAGE_ARCH = "${MACHINE_ARCH}"

UBOOT_MACHINE = "am57xx_evm_defconfig"

COMPATIBLE_MACHINE = "board-tv"

UBOOT_SUFFIX ??= "bin"
UBOOT_IMAGE ?= "u-boot-${MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_SYMLINK ?= "u-boot-${MACHINE}.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET ?= "all"

MLO_IMAGE ?= "MLO-${MACHINE}-${PV}-${PR}"
MLO_SYMLINK ?= "MLO"

do_configure_prepend () {
    sed -i -e s,NFS_IP,${MA_NFS_IP},g ${WORKDIR}/boot-beagle-nfs.script
    sed -i -e s,NFS_PATH,${MA_NFS_PATH},g ${WORKDIR}/boot-beagle-nfs.script

    sed -i -e s,NFS_IP,${MA_NFS_IP},g ${WORKDIR}/boot-beagle-nfs2.script
    sed -i -e s,NFS_PATH,${MA_NFS_PATH},g ${WORKDIR}/boot-beagle-nfs2.script
    sed -i -e s,TARGET_IP,${MA_TARGET_IP},g ${WORKDIR}/boot-beagle-nfs2.script
    sed -i -e s,GATEWAY_IP,${MA_GATEWAY_IP},g ${WORKDIR}/boot-beagle-nfs2.script
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
    ln -sf ${UBOOT_IMAGE} ${D}/boot/${UBOOT_BINARY}

    install -m 0644 ${B}/MLO ${D}/boot/${MLO_IMAGE}
    ln -sf ${MLO_IMAGE} ${D}/boot/${MLO_SYMLINK}

    install -m 0644 ${WORKDIR}/boot-beagle-sdcard.script ${D}/boot/uEnv-sdcard.txt
    install -m 0644 ${WORKDIR}/boot-beagle-label.script ${D}/boot/uEnv-label.txt
    install -m 0644 ${WORKDIR}/boot-beagle-nfs.script ${D}/boot/uEnv-nfs.txt
    install -m 0644 ${WORKDIR}/boot-beagle-nfs2.script ${D}/boot/uEnv-nfs2.txt
    ln -sf uEnv-label.txt ${D}/boot/uEnv.txt
}

FILES_${PN} = "/boot"

do_deploy () {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/${UBOOT_BINARY} ${DEPLOYDIR}/${UBOOT_IMAGE}

    cd ${DEPLOYDIR}
    rm -f ${UBOOT_SYMLINK}
    ln -sf ${UBOOT_IMAGE} ${UBOOT_SYMLINK}

    install -m 0644 ${B}/MLO ${DEPLOYDIR}/${MLO_IMAGE}
    ln -sf ${MLO_IMAGE} ${DEPLOYDIR}/${MLO_SYMLINK}

    install -m 0644 ${WORKDIR}/boot-beagle-sdcard.script ${DEPLOYDIR}/uEnv-sdcard.txt
    install -m 0644 ${WORKDIR}/boot-beagle-label.script ${DEPLOYDIR}/uEnv-label.txt
    install -m 0644 ${WORKDIR}/boot-beagle-nfs.script ${DEPLOYDIR}/uEnv-nfs.txt
    install -m 0644 ${WORKDIR}/boot-beagle-nfs2.script ${DEPLOYDIR}/uEnv-nfs2.txt
    rm -f uEnv.txt
    ln -sf uEnv-label.txt uEnv.txt
}

addtask deploy before do_build after do_compile
