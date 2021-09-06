FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://main.conf"

GI_DATA_ENABLED = "False"

PACKAGECONFIG_remove = "obex-profiles"

RDEPENDS_${PN}-testtools_remove = "python3-dbus python3-core"
PACKAGES_remove = "${PN}-testtools"
FILES_${PN}-testtools = ""

INSANE_SKIP_${PN} += "installed-vs-shipped"

do_install_append() {
	if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d
		touch ${D}${sysconfdir}/init.d/functions
		install -d ${D}${sysconfdir}/rcS.d
		ln -sf ../init.d/bluetooth ${D}${sysconfdir}/rcS.d/S05bluetooth
	fi
	install -d ${D}${sysconfdir}/bluetooth/
	install -m 0644 ${WORKDIR}/main.conf ${D}/${sysconfdir}/bluetooth/

	if [ -f ${D}/${sysconfdir}/bluetooth/input.conf ]; then
		sed -i -e 's/#IdleTimeout=30/IdleTimeout=1/g' ${D}/${sysconfdir}/bluetooth/input.conf
	fi
}
