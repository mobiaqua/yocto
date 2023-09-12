do_install() {
	install -d ${D}${sysconfdir}
	install -D -m 0644 ${WORKDIR}/inittab ${D}${sysconfdir}/inittab
	sed -i 's/^::sysinit/null::sysinit/' ${D}${sysconfdir}/inittab
	sed -i 's/^::shutdown/null::shutdown/' ${D}${sysconfdir}/inittab
	sed -i 's/^::restart/null::restart/' ${D}${sysconfdir}/inittab
	sed -i 's/^null::shutdown/#null::shutdown/' ${D}${sysconfdir}/inittab
	sed -i 's/^null::restart/#null::restart/' ${D}${sysconfdir}/inittab
	sed -i 's/^null::respawn/#null::respawn/' ${D}${sysconfdir}/inittab
	sed -i 's/^null::sysinit:\/sbin\/swapon/#null::sysinit:\/sbin\/swapon/' ${D}${sysconfdir}/inittab
	sed -i 's/^null::sysinit:\/etc\/init.d\/rcS/null::sysinit:\/etc\/init.d\/rcS > \/dev\/ttyS1 2>\&1/' ${D}${sysconfdir}/inittab
}
