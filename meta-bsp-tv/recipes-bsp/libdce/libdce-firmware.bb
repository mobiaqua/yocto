LICENSE = "Proprietary"
ERROR_QA_remove = "license-checksum"

PV = "1.00"
PR = "r0"

SRC_URI = "file://ducati-m3-ipu.xem3 \
           file://ducati-m3-ipu.xem3.license.pdf \
           file://remote_proc_dce.sh \
"

S = "${WORKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "remote_proc_dce.sh"
INITSCRIPT_PARAMS = "start 90 S ."

do_install() {
	install -d ${D}${base_libdir}/firmware

	install -m 0644 ${S}/ducati-m3-ipu.xem3 ${D}${base_libdir}/firmware/
	install -m 0644 ${S}/ducati-m3-ipu.xem3.license.pdf ${D}${base_libdir}/firmware/
	ln -s ducati-m3-ipu.xem3 ${D}${base_libdir}/firmware/ducati-m3-core0.xem3

	if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/remote_proc_dce.sh ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
	fi
}

FILES_${PN} += "${sysconfdir} ${base_libdir}/firmware/"

