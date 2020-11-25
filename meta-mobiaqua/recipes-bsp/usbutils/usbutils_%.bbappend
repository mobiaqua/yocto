RDEPENDS_${PN}-python_remove = "python3-core"

do_install_append () {
	rm -f ${D}/${bindir}/lsusb.py
}
