do_deploy:prepend:nuc () {
    install -d ${DEPLOYDIR}/firmwares/intel
    install -d ${DEPLOYDIR}/firmwares/i915
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/i915/adlp_dmc_ver2_16.bin ${DEPLOYDIR}/firmwares/i915/
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/i915/adlp_guc_70.bin ${DEPLOYDIR}/firmwares/i915/
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/i915/tgl_huc.bin ${DEPLOYDIR}/firmwares/i915/
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/intel/ibt-0040-0041.sfi ${DEPLOYDIR}/firmwares/intel/
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/intel/ibt-0040-0041.ddc ${DEPLOYDIR}/firmwares/intel/
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/iwlwifi-so-a0-gf-a0-72.ucode ${DEPLOYDIR}/firmwares/
    install -m 0644 ${WORKDIR}/linux-firmware-${PV}/iwlwifi-so-a0-gf-a0.pnvm ${DEPLOYDIR}/firmwares/
}
