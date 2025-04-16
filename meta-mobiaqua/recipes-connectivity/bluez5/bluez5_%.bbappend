FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:append() {
    if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -d ${D}${sysconfdir}/rcS.d
        ln -sf ../init.d/bluetooth ${D}${sysconfdir}/rcS.d/S05bluetooth
    fi

    install -d ${D}${sysconfdir}/bluetooth/
    install -m 0644 ${S}/src/main.conf ${D}/${sysconfdir}/bluetooth/

    sed -i -e 's/#AutoEnable=true/AutoEnable=true/g' ${D}/${sysconfdir}/bluetooth/main.conf
    sed -i -e 's/#IdleTimeout=30/IdleTimeout=3/g' ${D}/${sysconfdir}/bluetooth/input.conf
    sed -i -e 's/#ClassicBondedOnly=true/ClassicBondedOnly=false/g' ${D}/${sysconfdir}/bluetooth/input.conf
}
