# Copyright Matthias Hentges <devel@hentges.net> (c) 2006
# License: MIT (see COPYING.MIT)

LICENSE = "PD"
ERROR_QA:remove = "license-checksum"

DESCRIPTION = "Preconfigured mplayer preferences"

PV = "0.0.1"
PR = "r1"

SRC_URI = "file://mplayer.conf file://codecs.conf file://input.conf"

do_install() {
	install -d "${D}/usr${sysconfdir}/mplayer"
	install -m 0644 ${WORKDIR}/mplayer.conf "${D}/usr${sysconfdir}/mplayer"
	install -m 0644 ${WORKDIR}/codecs.conf "${D}/usr${sysconfdir}/mplayer"
	install -m 0644 ${WORKDIR}/input.conf "${D}/usr${sysconfdir}/mplayer"
}

FILES:${PN} = "/usr${sysconfdir}/mplayer"
PACKAGE_ARCH = "all"
