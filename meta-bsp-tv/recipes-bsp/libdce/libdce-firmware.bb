LICENSE = "Proprietary"
ERROR_QA_remove = "license-checksum"

PV = "1.00"
PR = "r0"

SRC_URI = "file://ducati-m3-ipu.xem3 \
           file://ducati-m3-ipu.xem3.license.pdf \
           file://remote_proc_dce.sh \
"

S = "${WORKDIR}"

INITSCRIPT_NAME = "remote_proc_dce.sh"

do_install() {
	install -d ${D}${base_libdir}/firmware

	install -m 0644 ${S}/ducati-m3-ipu.xem3 ${D}${base_libdir}/firmware/
	install -m 0644 ${S}/ducati-m3-ipu.xem3.license.pdf ${D}${base_libdir}/firmware/
	ln -s ducati-m3-ipu.xem3 ${D}${base_libdir}/firmware/ducati-m3-core0.xem3

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/remote_proc_dce.sh ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
	for i in 2 3 4 5; do
		install -d ${D}${sysconfdir}/rc${i}.d
		ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rc${i}.d/S40${INITSCRIPT_NAME}
	done
}

FILES_${PN} += "${sysconfdir} ${base_libdir}/firmware/"

