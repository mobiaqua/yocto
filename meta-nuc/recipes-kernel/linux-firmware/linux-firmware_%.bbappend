do_deploy:prepend:nuc () {
    install -d ${DEPLOYDIR}/firmwares/i915
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/i915/adlp_dmc.bin ${DEPLOYDIR}/firmwares/i915/
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/i915/adlp_guc_70.bin ${DEPLOYDIR}/firmwares/i915/
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/i915/tgl_huc.bin ${DEPLOYDIR}/firmwares/i915/
}
