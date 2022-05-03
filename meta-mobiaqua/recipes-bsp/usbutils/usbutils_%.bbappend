RDEPENDS:${PN}-python:remove = "python3-core"

do_install:append () {
	rm -f ${D}/${bindir}/lsusb.py
}
