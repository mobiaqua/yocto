LICENSE = "Proprietary"
ERROR_QA_remove = "license-checksum"

PV = "1.00"
PR = "r0"

SRC_URI = "file://omap4-ipu-fw.xem3 \
           file://omap5-ipu-fw.xem4 \
           file://dra7-ipu2-fw.xem4 \
           file://ipu-fw.license.pdf \
"

S = "${WORKDIR}"

do_install() {
	install -d ${D}${base_libdir}/firmware

	install -m 0644 ${S}/omap4-ipu-fw.xem3 ${D}${base_libdir}/firmware/
#	install -m 0644 ${S}/omap5-ipu-fw.xem4 ${D}${base_libdir}/firmware/
	install -m 0644 ${S}/dra7-ipu2-fw.xem4 ${D}${base_libdir}/firmware/
	install -m 0644 ${S}/ipu-fw.license.pdf ${D}${base_libdir}/firmware/
}

FILES_${PN} += "${base_libdir}/firmware/"

