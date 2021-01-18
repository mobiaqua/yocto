DESCRIPTION = "Kernel drivers for the PowerVR SGX 5 Series chipset found in the TI devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e"

COMPATIBLE_MACHINE = "board-tv"

PR = "r0"
PV = "1.9.6"
PR_append = "+gitr-${SRCREV}"

DEPENDS = "libdrm linux-ti sgx-pvrsrvinit"

SRCREV = "b2a9a08174bc16ce37bd330c282a34a55bd182ff"

SRC_URI = "git://github.com/mobiaqua/sgx-pvr5-module.git;protocol=git \
           file://rc.pvr \
          "

S = "${WORKDIR}/git"

inherit module update-rc.d

DEBUG = "release"

MACHINE_KERNEL_PR_append = "a"

MAKE_TARGETS = "-C eurasiacon/build/linux2/omap4430_linux BUILD=${DEBUG} W=1 V=1 SUPPORT_V4L2_GFX=0 KERNELDIR=${STAGING_KERNEL_DIR}"

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
