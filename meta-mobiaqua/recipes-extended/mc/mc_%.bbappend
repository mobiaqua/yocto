RDEPENDS_${BPN}-helpers-perl_remove = "perl"

EXTRA_OECONF_append = " --disable-vfs-extfs"

do_install_prepend () {
	# w/a for missing files
	install -d ${D}${libexecdir}/mc/extfs.d/
	touch ${D}${libexecdir}/mc/extfs.d/s3+
	touch ${D}${libexecdir}/mc/extfs.d/uc1541
}
