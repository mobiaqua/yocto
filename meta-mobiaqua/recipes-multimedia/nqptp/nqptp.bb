SUMMARY = "Not Quite PTP"
DESCRIPTION = "nqptp is a daemon that monitors timing data from any PTP clocks it sees on ports 319 and 320. It maintains records for each clock, identified by Clock ID and IP."
HOMEPAGE = "https://github.com/mikebrady/nqptp"

LICENSE  = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS += "initscripts"

SRCREV = "fc8d83fdf5fa16b1312536eaddc3e18854a50e6d"
PV = "+git${SRCPV}"
SRC_URI = "git://github.com/mikebrady/nqptp.git;branch=development;protocol=https \
           file://nqptp.d"

S = "${WORKDIR}/git"

inherit autotools update-rc.d

INITSCRIPT_NAME = "nqptp"

do_install:append() {
    if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -d ${D}${sysconfdir}/rcS.d
        install -m 0755 ${WORKDIR}/nqptp.d ${D}${sysconfdir}/init.d/nqptp
        ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S50${INITSCRIPT_NAME}
    fi
}