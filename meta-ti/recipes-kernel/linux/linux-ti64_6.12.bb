require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native linux-firmware"
DEPENDS:remove = "util-linux-native elfutils-native"
COMPATIBLE_MACHINE = "beagle64"
KERNEL_VERSION_SANITY_SKIP = "1"
INSANE_SKIP += "buildpaths"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.12.49"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:beagle64 = "ti/k3-j721e-beagleboneai64.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti64_6.12:"
KBUILD_DEFCONFIG = "ti_defconfig"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${LINUX_VERSION}.tar.xz \
            file://byteswap.h \
            file://01/0002-PENDING-remoteproc-core-Make-Remoteproc-devices-DMA-.patch \
            file://01/0003-PENDING-remoteproc-core-Make-shutdown-on-release-per.patch \
            file://01/0004-PENDING-remoteproc-core-Add-DMA-BUF-attachment-inter.patch \
            file://01/0007-PENDING-remoteproc-k3-dsp-Set-DMA-mask-to-48bits.patch \
            file://01/0008-PENDING-remoteproc-k3-r5-Set-DMA-mask-to-48bits.patch \
            file://02/0002-PENDING-media-cadence-csi2rx-Support-RAW12-bayer-for.patch \
            file://02/0003-PENDING-media-ti-csi2rx-Support-RAW12-bayer-formats.patch \
            file://02/0004-FROMLIST-media-ti-j721e-csi2rx-separate-out-device-a.patch \
            file://02/0005-FROMLIST-media-ti-j721e-csi2rx-prepare-SHIM-code-for.patch \
            file://02/0006-FROMLIST-media-ti-j721e-csi2rx-allocate-DMA-channel-.patch \
            file://02/0007-FROMLIST-media-ti-j721e-csi2rx-add-a-subdev-for-the-.patch \
            file://02/0008-FROMLIST-media-ti-j721e-csi2rx-get-number-of-context.patch \
            file://02/0009-FROMLIST-media-cadence-csi2rx-add-get_frame_desc-wra.patch \
            file://02/0010-FROMLIST-media-ti-j721e-csi2rx-add-support-for-proce.patch \
            file://02/0011-FROMLIST-media-cadence-csi2rx-Use-new-enable-stream-.patch \
            file://02/0012-FROMLIST-media-cadence-csi2rx-Enable-multi-stream-su.patch \
            file://02/0013-FROMLIST-media-ti-j721e-csi2rx-add-multistream-suppo.patch \
            file://02/0014-FROMLIST-media-ti-j721e-csi2rx-Submit-all-available-.patch \
            file://02/0015-PENDING-media-v4l2-core-Enable-streams-api.patch \
            file://02/0016-PENDING-media-i2c-imx219-Add-mbus_frame_desc-for-pad.patch \
            file://02/0018-PENDING-media-i2c-add-Sony-IMX390-driver.patch \
            file://02/0019-TI-HACK-media-i2c-imx390-Remove-V4L2_SUBDEV_FL_STREA.patch \
            file://02/0022-PENDING-arm64-dts-ti-k3-j721e-main-Add-CSI2RX-multis.patch \
            file://02/0041-UPSTREAM-Merge-tag-ti-k3-dt-for-v6.13-of-https-git.k.patch \
            file://02/0048-UPSTREAM-mmc-sdhci_am654-Add-sdhci_am654_start_signa.patch \
            file://02/0054-FROMLIST-phy-cadence-torrent-Add-PCIe-multilink-conf.patch \
            file://02/0055-FROMLIST-phy-cadence-torrent-Add-PCIe-multilink-USB-.patch \
            file://02/0059-UPSTREAM-iommu-omap-Add-minimal-fwnode-support.patch \
            file://02/0060-UPSTREAM-iommu-Make-bus_iommu_probe-static.patch \
            file://02/0088-UPSTREAM-firmware-ti_sci-Add-support-for-querying-th.patch \
            file://02/0089-UPSTREAM-firmware-ti_sci-Add-system-suspend-and-resu.patch \
            file://02/0090-UPSTREAM-firmware-ti_sci-Introduce-Power-Management-.patch \
            file://02/0091-UPSTREAM-firmware-ti_sci-add-CPU-latency-constraint-.patch \
            file://02/0136-PENDING-dma-buf-heaps-Initialize-during-core-instead.patch \
            file://02/0137-PENDING-dma-buf-heaps-Add-Carveout-heap-to-DMA-BUF-H.patch \
            file://02/0138-PENDING-misc-sram-Add-DMA-BUF-Heap-exporting-of-SRAM.patch \
            file://02/0152-FROMLIST-drm-bridge-cdns-mhdp8546-Fix-possible-null-.patch \
            file://02/0172-UPSTREAM-pmdomain-ti_sci-add-per-device-latency-cons.patch \
            file://02/0173-UPSTREAM-pmdomain-ti_sci-add-wakeup-constraint-manag.patch \
            file://02/0174-UPSTREAM-pmdomain-ti_sci-handle-wake-IRQs-for-IO-dai.patch \
            file://02/0198-PENDING-v4l2-controls-Add-Control-for-Background-Det.patch \
            file://02/0202-FROMLIST-rpmsg-char-Export-alias-for-RPMSG-ID-rpmsg-.patch \
            file://02/0203-PENDING-rpmsg-char-Update-local-endpt-address-for-vi.patch \
            file://02/0208-UPSTREAM-remoteproc-k3-r5-Add-devm-action-to-release.patch \
            file://02/0212-FROMLIST-remoteproc-k3-Refactor-shared-data-structur.patch \
            file://02/0213-FROMLIST-remoteproc-k3-Refactor-mailbox-rx_callback-.patch \
            file://02/0214-FROMLIST-remoteproc-k3-Refactor-.kick-rproc-ops-into.patch \
            file://02/0215-FROMLIST-remoteproc-k3-m4-Use-k3_rproc_mem_data-stru.patch \
            file://02/0216-FROMLIST-remoteproc-k3-Refactor-rproc_reset-implemen.patch \
            file://02/0217-FROMLIST-remoteproc-k3-Refactor-rproc_release-implem.patch \
            file://02/0218-FROMLIST-remoteproc-k3-Refactor-rproc_request_mbox-i.patch \
            file://02/0219-FROMLIST-remoteproc-k3-Refactor-.prepare-rproc-ops-i.patch \
            file://02/0220-FROMLIST-remoteproc-k3-Refactor-.unprepare-rproc-ops.patch \
            file://02/0221-FROMLIST-remoteproc-k3-Refactor-.start-rproc-ops-int.patch \
            file://02/0222-FROMLIST-remoteproc-k3-Refactor-.stop-rproc-ops-into.patch \
            file://02/0223-FROMLIST-remoteproc-k3-Refactor-.attach-rproc-ops-in.patch \
            file://02/0224-FROMLIST-remoteproc-k3-Refactor-.detach-rproc-ops-in.patch \
            file://02/0225-FROMLIST-remoteproc-k3-Refactor-.get_loaded_rsc_tabl.patch \
            file://02/0226-FROMLIST-remoteproc-k3-Refactor-.da_to_va-rproc-ops-.patch \
            file://02/0227-FROMLIST-remoteproc-k3-Refactor-of_get_memories-func.patch \
            file://02/0228-FROMLIST-remoteproc-k3-Refactor-mem_release-function.patch \
            file://02/0229-FROMLIST-remoteproc-k3-Refactor-reserved_mem_init-fu.patch \
            file://02/0230-FROMLIST-remoteproc-k3-Refactor-release_tsp-function.patch \
            file://02/0258-FROMLIST-drm-bridge-cdns-dsi-Move-to-devm_drm_of_get.patch \
            file://02/0259-FROMLIST-drm-mipi-dsi-Add-helper-to-find-input-forma.patch \
            file://02/0260-FROMLIST-drm-bridge-cdns-dsi-Support-atomic-bridge-A.patch \
            file://02/0261-FROMLIST-drm-bridge-cdns-dsi-Move-DSI-mode-check-to-.patch \
            file://02/0262-FROMLIST-drm-atomic-helper-Refactor-crtc-encoder-bri.patch \
            file://02/0263-FROMLIST-drm-atomic-helper-Separate-out-bridge-pre_e.patch \
            file://02/0264-FROMLIST-drm-atomic-helper-Re-order-bridge-chain-pre.patch \
            file://02/0265-FROMLIST-drm-bridge-cdns-dsi-Use-pre_enable-post_dis.patch \
            file://02/0266-FROMLIST-drm-bridge-cdns-mhdp8546-Add-support-for-no.patch \
            file://02/0267-FROMLIST-drm-bridge-cdns-mhdp8546-Move-mode_valid-ho.patch \
            file://02/0271-PENDING-drm-tidss-Populate-crtc_-timing-params.patch \
            file://02/0272-FROMLIST-drm-tidss-Add-OLDI-bridge-support.patch \
            file://02/0273-FROMLIST-drm-tidss-Add-dispc_is_idle.patch \
            file://02/0274-PENDING-drm-tidss-Power-up-attached-PM-domains-on-pr.patch \
            file://02/0275-PENDING-drm-tidss-Add-support-for-AM62P-DSS0.patch \
            file://02/0276-PENDING-drm-tidss-Add-support-for-AM62P-DSS1.patch \
            file://02/0278-FROMLIST-drm-tidss-Remove-unused-OCP-error-flag.patch \
            file://02/0279-FROMLIST-drm-tidss-Remove-extra-K2G-check.patch \
            file://02/0280-FROMLIST-drm-tidss-Add-printing-of-underflows.patch \
            file://02/0283-FROMLIST-drm-tidss-Rename-wait_lock-to-irq_lock.patch \
            file://02/0284-PENDING-drm-tidss-Add-some-support-for-splash-screen.patch \
            file://02/0285-TI-HACK-drm-tidss-Soft-reset-dispc-if-simple-framebu.patch \
            file://02/0292-FROMLIST-phy-cadence-cdns-dphy-Fix-PLL-lock-and-comm.patch \
            file://02/0311-PENDING-arm64-dts-ti-k3-j721e-main-Add-DSI-and-DPHY-.patch \
            file://02/0321-TI-HACK-arm64-dts-ti-k3-j721e-beagleboneai64-Drop-dp.patch \
            file://02/0335-FROMLIST-dmaengine-ti-k3-udma-Enable-second-resource.patch \
            file://02/0343-PENIDNG-arm64-dts-ti-k3-j721e-main-Make-the-scm_conf.patch \
            file://02/0352-FROMLIST-soc-ti-k3-ringacc-include-platform_device-h.patch \
            file://02/0353-UPSTREAM-cpufreq-ti-cpufreq-Remove-revision-offsets-.patch \
            file://02/0355-UPSTREAM-mfd-syscon-Use-regmap-max_register_is_0-as-.patch \
            file://02/0359-FROMLIST-remoteproc-Introduce-mailbox-messages-for-g.patch \
            file://02/0360-FROMLIST-remoteproc-k3-r5-support-for-graceful-shutd.patch \
            file://02/0361-PENDING-remoteproc-k3-Add-support-for-graceful-shutd.patch \
            file://02/0364-FROMLIST-firmware-ti_sci-Support-transfers-without-r.patch \
            file://02/0365-FROMLIST-firmware-ti_sci-Partial-IO-support.patch \
            file://02/0388-FROMLIST-remoteproc-k3-m4-Update-rproc-pointer-withi.patch \
            file://03/0061-FROMLIST-drm-tidss-Add-support-for-display-sharing.patch \
            file://03/0063-PENDING-arm64-dts-ti-k3-j7-Add-phase-detect-selector.patch \
            file://03/0067-PENDING-drivers-dma-ti-Refactor-TI-K3-UDMA-driver.patch \
            file://03/0086-PENDING-arm64-dts-ti-k3-j721e-main-add-gpu-node.patch \
            file://03/0088-PENDING-dmaengine-ti-k3-udma-Fix-sporadic-crash-on-A.patch \
            file://03/0089-TI-HACK-drm-bridge-cdns-mhdp8546-core-Add-polling-su.patch \
            file://03/0090-TI-HACK-gpio-davinci-Restore-GPIO-context-early-in-t.patch \
            file://03/0120-PENDING-dmaengine-ti-k3-udma-Fix-warnings-with-W-1.patch \
            file://03/0122-FROMLIST-media-cadence-csi2rx-Enable-csi2rx_err_irq-.patch \
            file://03/0125-PENDING-arm64-dts-ti-k3-j721e-main-Add-interrupts-pr.patch \
            file://03/0151-PENDING-media-ti-j721e-csi2rx-Change-the-drain-archi.patch \
            file://03/0152-PENDING-media-ti-j721e-csi2rx-Make-streams-mutually-.patch \
            file://04/0017-PENDING-remoteproc-core-Fix-double-unlock-for-DMA-BU.patch \
            file://04/0024-PENDING-arm64-dts-ti-k3-j721e-main-Add-timesync-rout.patch \
            file://04/0043-PENDING-remoteproc-k3-Add-support-for-suspend-resume.patch \
            file://04/0044-PENDING-remoteproc-k3-dsp-Add-support-for-suspend-re.patch \
            file://04/0045-PENDING-remoteproc-k3-m4-Add-support-for-suspend-res.patch \
            file://04/0046-PENDING-remoteproc-k3-r5-add-support-for-PM-suspend-.patch \
            file://04/0047-TI-net-ethernet-ti-am65-cpsw-add-cut-thru-support-fo.patch \
            file://04/0048-TI-HACK-net-ethernet-ti-am65-cpsw-nuss-add-debugfs-t.patch \
            file://04/0050-TI-arm64-dts-ti-k3-j721e-main-Add-IMG-Encoder-and-De.patch \
            file://04/0065-PENDING-arm64-dts-ti-k3-j7xx-mcu-wakeup-Enable-split.patch \
            file://04/0071-PENDING-media-ti-j721e-csi2rx-Add-system-suspend-res.patch \
            file://04/0072-PENDING-media-ti-j721e-csi2rx-Restore-streams-on-sys.patch \
            file://04/0073-PENDING-media-ti-j721e-csi2rx-Support-runtime-suspen.patch \
            file://04/0074-PENDING-media-ti-j721e-csi2rx-Use-pm_notifier-instea.patch \
            file://04/0088-PENDING-media-v4l-Add-10-bit-RGBIr-formats.patch \
            file://04/0090-PENDING-media-cadence-csi2rx-Add-RAW10-RGBIr-formats.patch \
            file://04/0091-PENDING-media-ti-j721e-csi2rx-Add-RAW10-RGBIr-format.patch \
            file://04/0094-PENDING-media-ti-j721e-csi2rx-Wait-for-the-last-drai.patch \
            file://04/0095-PENDING-media-cadence-csi2rx-fix-uninitialized-frame.patch \
            file://04/0098-TI-HACK-net-ethernet-ti-Makefile-Remove-redundant-en.patch \
            file://04/0100-TI-HACK-remoteproc-k3-r5-set-r5-core-config-on-resum.patch \
            file://04/0102-PENDING-media-ti-j721e-csi2rx-Add-10-bit-GREY-format.patch \
            file://04/0103-PENDING-media-cadence-csi2rx-Add-10-bit-GREY-formats.patch \
            file://04/0104-PENDING-dmaengine-ti-k3-udma-common-Fix-errors-with-.patch \
            file://04/0106-FROMLIST-media-ti-j721e-csi2rx-Allow-passing-cache-h.patch \
            file://04/0108-PENDING-irqchip-irq-ti-sci-inta-Don-t-aggregate-even.patch \
            file://04/0109-PENDING-irqchip-irq-ti-sci-inta-Introduce-IRQ-affini.patch \
            file://04/0110-PENDING-net-ethernet-ti-am65-cpsw-nuss-Setup-IRQ-aff.patch \
            file://04/0151-PENDING-pmdomain-ti_sci-Handle-wakeup-constraint-if-.patch \
            file://04/0166-PENDING-media-cadence-cdns-csi2rx-Support-multiple-p.patch \
            file://04/0167-PENDING-media-ti-j721e-csi2rx-Support-multiple-pixel.patch \
            file://04/0179-PENDING-firmware-ti_sci-Convert-CPU-latency-constrai.patch \
            file://06/0003-UPSTREAM-arm64-dts-ti-k3-j721e-Add-ranges-for-PCIe0-.patch \
            file://06/0004-UPSTREAM-arm64-dts-ti-k3-j721e-main-Switch-to-64-bit.patch \
            file://06/0012-UPSTREAM-hrtimer-Use-__raise_softirq_irqoff-to-raise.patch \
            file://06/0013-UPSTREAM-timers-Use-__raise_softirq_irqoff-to-raise-.patch \
            file://06/0014-UPSTREAM-softirq-Use-a-dedicated-thread-for-timer-wa.patch \
            file://07/0007-PENDING-media-ti-j721e-csi2rx-Serialize-stream-stops.patch \
            file://07/0010-PENDING-firmware-ti_sci-Enable-abort-handling-of-ent.patch \
            file://07/0011-PENDING-pmdomain-ti_sci-Add-LPM-abort-sequence-to-su.patch \
            file://07/0012-PENDING-remoteproc-k3-r5-Return-if-core-failed-to-su.patch \
            file://08/0008-TI-mmc-sdhci_am654-Sync-v1p8-implementation-with-ups.patch \
            file://08/0014-FROMLIST-drm-bridge-cdns-dsi-Fix-the-_atomic_check.patch \
            file://08/0015-FROMLIST-drm-tidss-Use-the-crtc_-timings-when-progra.patch \
            file://08/0016-FROMLIST-phy-cdns-dphy-Store-hs_clk_rate-and-return-.patch \
            file://08/0017-FROMLIST-phy-cdns-dphy-Remove-leftover-code.patch \
            file://08/0018-FROMLIST-drm-bridge-cdns-dsi-Remove-extra-line-at-th.patch \
            file://08/0019-FROMLIST-drm-bridge-cdns-dsi-Drop-crtc_-code.patch \
            file://08/0020-FROMLIST-drm-bridge-cdns-dsi-Remove-broken-fifo-empt.patch \
            file://08/0021-FROMLIST-drm-bridge-cdns-dsi-Drop-checks-that-should.patch \
            file://08/0022-FROMLIST-drm-bridge-cdns-dsi-Update-htotal-in-cdns_d.patch \
            file://08/0023-FROMLIST-drm-bridge-cdns-dsi-Drop-cdns_dsi_adjust_ph.patch \
            file://08/0024-FROMLIST-drm-bridge-cdns-dsi-Adjust-mode-to-negative.patch \
            file://08/0025-FROMLIST-drm-bridge-cdns-dsi-Fix-REG_WAKEUP_TIME-val.patch \
            file://08/0026-FROMLIST-drm-bridge-cdns-dsi-Use-video-mode-and-clea.patch \
            file://08/0027-FROMLIST-drm-bridge-cdns-dsi-Fix-event-mode.patch \
            file://08/0028-FROMLIST-drm-bridge-cdns-dsi-Tune-adjusted_mode-cloc.patch \
            file://08/0029-FROMLIST-drm-bridge-cdns-dsi-Don-t-fail-on-MIPI_DSI_.patch \
            file://08/0047-PENDING-net-ethernet-ti-am65-cpsw-nuss-Update-port_m.patch \
            file://08/0048-PENDING-net-ethernet-ti-cpsw_ale-Update-multicast-en.patch \
            file://09/0003-PENDING-media-ti-j721e-csi2rx-Remove-word-size-align.patch \
            file://09/0029-FROMLIST-PCI-j721e-Fix-programming-sequence-of-strap.patch \
            file://09/0034-PENDING-drivers-phy-cadence-cdns-dphy-Enabling-lower.patch \
            file://09/0039-FROMLIST-soc-ti-k3-socinfo-Add-support-for-AM62P-var.patch \
            file://11/0047-FROMLIST-serial-8250-omap-Support-wakeup-pinctrl-sta.patch \
            file://11/0071-TI-pmdomain-core-Check-for-NULL-subsystem-data.patch \
            file://11/0091-FROMLIST-drm-tidss-Set-vblank-event-time-at-crtc_ato.patch \
            file://11/0093-Revert-FROMLIST-drm-bridge-cdns-mhdp8546-Move-mode_v.patch \
            file://11/0094-FROMLIST-drm-bridge-cadence-cdns-mhdp8546-core-Remov.patch \
            file://11/0095-FROMLIST-drm-bridge-cadence-cdns-mhdp8546-Change-drm.patch \
            file://11/0096-FROMLIST-drm-bridge-cadence-cdns-mhdp8546-core-Set-t.patch \
            file://11/0097-FROMLIST-drm-bridge-cadence-cdns-mhdp8546-core-Add-m.patch \
            file://11/0098-FROMLIST-drm-bridge-cadence-cdns-mhdp8546-core-Reduc.patch \
            file://11/0099-FROMLIST-drm-bridge-cadence-cdns-mhdp8546-core-Handl.patch \
            file://11/0110-FROMLIST-drm-tidss-Fix-sampling-edge-configuration.patch \
            file://12/0002-UPSTREAM-drm-tidss-Mark-AM65x-OLDI-code-separately.patch \
            file://12/0003-UPSTREAM-drm-tidss-Add-OLDI-bridge-support.patch \
            file://12/0010-TI-HACK-drm-bridge-cadence-cdns-mhdp8546-core-Add-ch.patch \
            file://12/0011-TI-HACK-drm-bridge-cdns-mhdp8546-core-Remove-no_hpd.patch \
            file://12/0041-UPSTREAM-cpuidle-psci-Add-trace-for-PSCI-domain-idle.patch \
            file://12/0046-FROMLIST-clocksource-drivers-timer-ti-dm-Add-clockso.patch \
            file://12/0047-FROMLIST-clocksource-drivers-timer-ti-dm-Add-clockev.patch \
            file://12/0067-FROMLIST-dt-bindings-clock-Add-spread-spectrum-defin.patch \
            file://12/0068-FROMLIST-clk-Introduce-clk_hw_set_spread_spectrum.patch \
            file://12/0069-FROMLIST-clk-conf-Support-assigned-clock-sscs.patch \
            file://12/0070-PENDING-clk-keystone-sci-clk-Add-support-for-clock-s.patch \
            file://12/0071-PENDING-clocksource-drivers-timer-ti-dm-Fix-section-.patch \
            file://12/0076-FROMLIST-clocksource-drivers-arm_global_timer-Add-au.patch \
            file://12/0080-UPSTREAM-remoteproc-k3-Remove-remote-processor-mailb.patch \
            file://12/0081-TI-remoteproc-k3-r5-remove-remote-processor-mailbox-.patch \
            file://12/0083-TI-HACK-EXPERIMENTAL-drm-bridge-cdns-mhdp8546-core-i.patch \
            file://0001_fix_nonlinux_compile.patch \
            file://0002-bootup-hacks-move-mmc-early.patch \
            file://0004_wait-for-rootfs.patch \
            file://0010-cdns-dsi-core.kconfig.patch \
            file://0011_fix-mhdp-reg.patch \
            file://0012-enable-video-dma-sg.patch \
            file://0013-enable-v4l-m2m.patch \
            file://0014-WIP-tusb322.patch \
            file://0015_enable-snd-soc-hdmi-codec.patch \
            file://ti_config \
"

SRC_URI[sha256sum] = "234621e146dacce2241049555d550e4f7a6bde67ccd7ef232d47ac8145425526"

S = "${WORKDIR}/linux-${PV}"

kernel_do_configure:prepend() {
        install -m 644 ${WORKDIR}/byteswap.h ${S}/tools/include/byteswap.h
        install -m 644 ${WORKDIR}/byteswap.h ${S}/scripts/mod/byteswap.h
}

do_applypath() {
    sed 's|MOBIAQUA_FW_PATH|${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/|g' ${WORKDIR}/ti_config > ${S}/arch/${ARCH}/configs/ti_defconfig
}

addtask applypath before do_patch after do_unpack
