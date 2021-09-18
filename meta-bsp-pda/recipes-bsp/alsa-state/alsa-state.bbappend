FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
	if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
		install -d ${D}${sysconfdir}/rcS.d
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/alsa-state-init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
		ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S39${INITSCRIPT_NAME}
	fi
}

FILES_${PN}_append = " ${sysconfdir}/rcS.d/S39${INITSCRIPT_NAME}"
