do_install_append() {
	install -d ${D}${sysconfdir}/rcS.d
	ln -sf ../init.d/run-postinsts ${D}${sysconfdir}/rcS.d/S00run-postinsts
}
