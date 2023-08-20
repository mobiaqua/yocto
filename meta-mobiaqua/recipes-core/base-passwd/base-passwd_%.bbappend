do_install:append () {
	#MobiAqua: added defined custom root passwd
	sed -i -e s,root::0:0:root:,root:${MA_ROOT_PASSWORD}:0:0:root:, ${D}${datadir}/base-passwd/passwd.master
}
