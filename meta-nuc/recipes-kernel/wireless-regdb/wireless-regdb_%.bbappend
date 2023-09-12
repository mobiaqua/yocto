do_deploy:prepend:nuc () {
    install -d ${DEPLOYDIR}/firmwares
    install -m 0644 ${WORKDIR}/wireless-regdb-${PV}/regulatory.db ${DEPLOYDIR}/firmwares/
    install -m 0644 ${WORKDIR}/wireless-regdb-${PV}/regulatory.db.p7s ${DEPLOYDIR}/firmwares/
}
