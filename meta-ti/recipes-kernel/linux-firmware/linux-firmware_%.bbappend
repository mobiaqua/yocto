do_deploy:prepend:beagle64 () {
    install -d ${DEPLOYDIR}/firmwares/cadence
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/cadence/mhdp8546.bin ${DEPLOYDIR}/firmwares/cadence/
}
