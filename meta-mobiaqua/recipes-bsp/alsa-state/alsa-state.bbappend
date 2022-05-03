FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:append() {
	if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
		sed -i -e "s:#STATEDIR#:${localstatedir}/lib/alsa:g" ${WORKDIR}/alsa-state-init
		install -d ${D}${sysconfdir}/rcS.d
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/alsa-state-init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
		ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S39${INITSCRIPT_NAME}
	fi
}

FILES:${PN}:append = " ${sysconfdir}/rcS.d/S39${INITSCRIPT_NAME}"
