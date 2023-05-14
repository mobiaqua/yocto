LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

PR = "r0"
PV = "1"

COMPATIBLE_MACHINE = "panda|beagle|beagle64|igep0030"

SRC_URI = "\
           file://rc.pvr \
          "

inherit update-rc.d

INITSCRIPT_NAME = "pvr-init.sh"
INITSCRIPT_PARAMS = "start 30 S ."

do_install() {
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

FILES:${PN} += "${sysconfdir}"
