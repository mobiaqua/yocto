DESCRIPTION = "Kernel drivers for the PowerVR SGX chipset found in the omap4 SoCs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e"

#MobiAqua: custom package
COMPATIBLE_MACHINE = "board-tv"

PR = "r0"
PV = "1.9.6"
PR_append = "+gitr-${SRCREV}"

DEPENDS = "libdrm linux-omap4 omap4-sgx-pvrsrvinit"

SRCREV = "1b9a0badb65efd7f5112798b26ecbdbbc00ca5bb"

SRC_URI = "git://github.com/mobiaqua/pvr-omap4-dkms.git;protocol=git \
           file://rc.pvr \
           file://0001-core-mk-fix.patch;striplevel=2 \
           file://0001-Revert-SGX-KM-HACK-Disable-burst-combiner.patch;striplevel=2 \
           file://0002-Revert-No-more-__devinitdata-in-v3.8.patch;striplevel=2 \
           file://0003-Revert-Hack-include-paths-to-fix-compilation-with-v3.patch;striplevel=2 \
           file://0004-Revert-Fix-use-of-VM_RESERVED-for-kernel-3.7.patch;striplevel=2 \
           file://dont-use-gp-timer.patch;striplevel=2 \
          "

S = "${WORKDIR}/git/sgx"

inherit module update-rc.d

DEBUG = "release"

MACHINE_KERNEL_PR_append = "a"

MAKE_TARGETS = "-C eurasiacon/build/linux2/omap4430_linux BUILD=${DEBUG} KERNELDIR=${STAGING_KERNEL_DIR}"

INITSCRIPT_NAME = "pvr-init.sh"
INITSCRIPT_PARAMS = "start 30 S ."

do_install() {
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
	cp eurasiacon/binary2_omap4430_linux_${DEBUG}/target/kbuild/*.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr

	if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/rc.pvr ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
	fi

	if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/rc.pvr ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
		install -d ${D}${sysconfdir}/rcS.d
		ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S30${INITSCRIPT_NAME}
	fi
}

PACKAGE_STRIP = "no"

FILES_${PN} += "${sysconfdir}"
