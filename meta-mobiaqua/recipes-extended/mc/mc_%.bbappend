RDEPENDS:${BPN}-helpers-perl:remove = "perl"

EXTRA_OECONF:append = " --disable-vfs-extfs"

CFLAGS:append = " -DNCURSES_WIDECHAR=0"

do_install:prepend () {
	# w/a for missing files
	install -d ${D}${libexecdir}/mc/extfs.d/
	touch ${D}${libexecdir}/mc/extfs.d/s3+
	touch ${D}${libexecdir}/mc/extfs.d/uc1541
}
