RDEPENDS:${PN}-cachegrind:remove = "python3-core"
RDEPENDS:${PN}-massif:remove = "perl"
RDEPENDS:${PN}-callgrind:remove = "perl"

do_install:append () {
	rm -f ${D}/${bindir}/ms_print
	rm -f ${D}/${bindir}/cg_diff
	rm -f ${D}/${bindir}/cg_annotate
	rm -f ${D}/${bindir}/callgrind_control
	rm -f ${D}/${bindir}/callgrind_annotate
}
