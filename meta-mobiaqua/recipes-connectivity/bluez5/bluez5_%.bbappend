FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://main.conf"

do_install:append() {
    if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -d ${D}${sysconfdir}/rcS.d
        ln -sf ../init.d/bluetooth ${D}${sysconfdir}/rcS.d/S05bluetooth
    fi
    install -d ${D}${sysconfdir}/bluetooth/
    install -m 0644 ${WORKDIR}/main.conf ${D}/${sysconfdir}/bluetooth/

    if [ -f ${D}/${sysconfdir}/bluetooth/input.conf ]; then
        sed -i -e 's/#IdleTimeout=30/IdleTimeout=3/g' ${D}/${sysconfdir}/bluetooth/input.conf
        sed -i -e 's/#ClassicBondedOnly=true/ClassicBondedOnly=false/g' ${D}/${sysconfdir}/bluetooth/input.conf
    fi
}
