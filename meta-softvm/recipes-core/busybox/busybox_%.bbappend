FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://override-machine.patch"

do_install:append() {
	rm -f ${D}${sysconfdir}/init.d/rcK
}

do_prepare_config:append() {
	sed -i 's|# CONFIG_FEATURE_INIT_QUIET is not set|CONFIG_FEATURE_INIT_QUIET=y|g' ${S}/.config
}
