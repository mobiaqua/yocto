do_install () {
	install -d -m 755 ${D}${sbindir}
	# MobiAqua: disabled '-o root -g root'
	install -p -m 755 ${B}/update-passwd ${D}${sbindir}/
	install -d -m 755 ${D}${mandir}/man8 ${D}${mandir}/pl/man8
	install -p -m 644 ${S}/man/update-passwd.8 ${D}${mandir}/man8/
	install -p -m 644 ${S}/man/update-passwd.pl.8 \
		${D}${mandir}/pl/man8/update-passwd.8
	gzip -9 ${D}${mandir}/man8/* ${D}${mandir}/pl/man8/*
	install -d -m 755 ${D}${datadir}/base-passwd
	# MobiAqua: removed '-o root -g root'
	install -p -m 644 ${S}/passwd.master ${D}${datadir}/base-passwd/
	sed -i 's#:/root:#:${ROOT_HOME}:#' ${D}${datadir}/base-passwd/passwd.master
	# MobiAqua: removed '-o root -g root'
	install -p -m 644 ${S}/group.master ${D}${datadir}/base-passwd/
	#MobiAqua: added defined custom root passwd
	sed -i -e s,root::0:0:root:,root:${MA_ROOT_PASSWORD}:0:0:root:, ${D}${datadir}/base-passwd/passwd.master

	install -d -m 755 ${D}${docdir}/${BPN}
	install -p -m 644 ${S}/debian/changelog ${D}${docdir}/${BPN}/
	gzip -9 ${D}${docdir}/${BPN}/*
	install -p -m 644 ${S}/README ${D}${docdir}/${BPN}/
	install -p -m 644 ${S}/debian/copyright ${D}${docdir}/${BPN}/
}
