DESCRIPTION = "Shared Transport Line Discipline User Mode initialisation Manager Daemon"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://uim.c;beginline=1;endline=18;md5=9f0bbfbc10c67689e81a523e2976c31e"

INITSCRIPT_NAME = "uim-sysfs"
INITSCRIPT_PARAMS = "start 80 S ."

inherit update-rc.d

SRCREV = "a75f45be2d5c74fc1dd913d08afc30f09a230aa9"
SRC_URI = "git://git.ti.com/ti-bt/uim.git"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 uim ${D}${bindir}

	if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 scripts/uim-sysfs ${D}${sysconfdir}/init.d
	fi
}
