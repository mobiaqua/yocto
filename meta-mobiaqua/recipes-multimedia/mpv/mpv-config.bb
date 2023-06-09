LICENSE = "PD"
ERROR_QA:remove = "license-checksum"

DESCRIPTION = "Preconfigured mpv preferences"

PV = "0.0.1"
PR = "r1"

SRC_URI = "file://mpv.conf file://input.conf"

do_install() {
	install -d "${D}/${sysconfdir}/mpv"
	install -m 0644 ${WORKDIR}/mpv.conf "${D}/${sysconfdir}/mpv"
	install -m 0644 ${WORKDIR}/input.conf "${D}/${sysconfdir}/mpv"
}

CONFFILES:${PN} = "{sysconfdir}/mpv/mpv.conf {sysconfdir}/mpv/input.conf"
