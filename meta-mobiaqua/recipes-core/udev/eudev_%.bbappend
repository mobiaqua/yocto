do_install:append() {
	if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
		install -d ${D}${sysconfdir}/rcS.d
		ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S04${INITSCRIPT_NAME}
	fi
}
