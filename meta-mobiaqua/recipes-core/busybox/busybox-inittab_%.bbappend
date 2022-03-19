do_install() {
	install -d ${D}${sysconfdir}
	install -D -m 0644 ${WORKDIR}/inittab ${D}${sysconfdir}/inittab
	sed -i 's/^::sysinit/null::sysinit/' ${D}${sysconfdir}/inittab
	sed -i 's/^::shutdown/null::shutdown/' ${D}${sysconfdir}/inittab
	sed -i 's/^::restart/null::restart/' ${D}${sysconfdir}/inittab
	tmp="${SERIAL_CONSOLES}"
	[ -n "$tmp" ] && echo >> ${D}${sysconfdir}/inittab
	for i in $tmp
	do
		j=`echo ${i} | sed s/\;/\ /g`
		id=`echo ${i} | sed -e 's/^.*;//' -e 's/;.*//'`
		echo "null::respawn:${base_sbindir}/getty ${j}" >> ${D}${sysconfdir}/inittab
	done
}
