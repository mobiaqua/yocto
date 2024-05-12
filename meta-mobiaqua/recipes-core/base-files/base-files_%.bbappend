FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://input-dev-dir"

do_install:append () {
	if [ -f ${MA_FSTAB_FILE} ]; then
		install -m 0644 ${MA_FSTAB_FILE} ${D}${sysconfdir}/fstab
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/input-dev-dir ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
	fi

	if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/input-dev-dir ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
		install -d ${D}${sysconfdir}/rcS.d
		ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S90${INITSCRIPT_NAME}
	fi
}

inherit update-rc.d

INITSCRIPT_NAME = "input-dev-dir.sh"
INITSCRIPT_PARAMS = "start 90 S ."

FILES:${PN} += "${sysconfdir}"
