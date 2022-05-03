SUMMARY = "Touchscreen calibration data"
SECTION = "base"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://pointercal"

S = "${WORKDIR}"

do_install() {
	# Only install file if it has a contents
	if [ -s ${S}/pointercal ]; then
	        install -d ${D}${sysconfdir}/
	        install -m 0644 ${S}/pointercal ${D}${sysconfdir}/
	fi
}

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
INHIBIT_DEFAULT_DEPS = "1"
