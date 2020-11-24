do_install_append() {
	#MobiAqua: added handle custom defined host key
	if [ -f ${MA_DROPBEAR_KEY_FILE} ]; then
		install -m 0600 ${MA_DROPBEAR_KEY_FILE} ${D}${sysconfdir}/dropbear/dropbear_rsa_host_key
	fi
}
