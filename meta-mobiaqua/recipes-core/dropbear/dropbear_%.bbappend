PACKAGECONFIG_remove = "disable-weak-ciphers"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://dropbear.default"

do_install_append() {
	if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
		install -d ${D}${sysconfdir}/rcS.d
		ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S10${INITSCRIPT_NAME}
	fi

	#MobiAqua: added handle custom defined host key
	if [ -f ${MA_DROPBEAR_KEY_FILE} ]; then
		install -m 0600 ${MA_DROPBEAR_KEY_FILE} ${D}${sysconfdir}/dropbear/dropbear_rsa_host_key
	fi
}
