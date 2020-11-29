do_install_append () {
	if [ -f ${MA_FSTAB_FILE} ]; then
		install -m 0644 ${MA_FSTAB_FILE} ${D}${sysconfdir}/fstab
	fi
}

