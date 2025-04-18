require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native linux-firmware"
DEPENDS:remove = "util-linux-native elfutils-native"
COMPATIBLE_MACHINE = "beagle64"
KERNEL_VERSION_SANITY_SKIP = "1"
INSANE_SKIP += "buildpaths"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.6.87"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:beagle64 = "ti/k3-j721e-beagleboneai64.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti64_6.6:"
KBUILD_DEFCONFIG = "ti_defconfig"
DEFAULT_PREFERENCE = "-1"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${LINUX_VERSION}.tar.xz \
            file://1/0008-net-ethernet-ti-am65-cpsw-Convert-to-platform-remove.patch \
            file://1/0009-net-ethernet-ti-cpsw-Convert-to-platform-remove-call.patch \
            file://1/0010-net-ethernet-ti-cpsw-new-Convert-to-platform-remove-.patch \
            file://1/0011-firmware-ti_sci-Use-list_for_each_entry-helper.patch \
            file://1/0012-firmware-ti_sci-refactor-deprecated-strncpy.patch \
            file://1/0027-arm64-dts-ti-k3-j721e-mcu-wakeup-Add-MCU-domain-ESM-.patch \
            file://1/0061-media-cadence-csi2rx-Cleanup-media-entity-properly.patch \
            file://1/0062-media-cadence-csi2rx-Add-get_fmt-and-set_fmt-pad-ops.patch \
            file://1/0063-media-cadence-csi2rx-Configure-DPHY-using-link-freq.patch \
            file://1/0064-media-cadence-csi2rx-Soft-reset-the-streams-before-s.patch \
            file://1/0065-media-cadence-csi2rx-Set-the-STOP-bit-when-stopping-.patch \
            file://1/0066-media-cadence-csi2rx-Fix-stream-data-configuration.patch \
            file://1/0067-media-cadence-csi2rx-Populate-subdev-devnode.patch \
            file://1/0068-media-cadence-csi2rx-Add-link-validation.patch \
            file://1/0070-media-ti-Add-CSI2RX-support-for-J721E.patch \
            file://1/0072-arm64-dts-ti-k3-Convert-NAVSS-to-simple-bus.patch \
            file://1/0083-firmware-ti_sci-Use-device_get_match_data.patch \
            file://1/0084-media-platform-cadence-select-MIPI_DPHY-dependency.patch \
            file://1/0101-net-ethernet-ti-am65-cpsw-rx_pause-tx_pause-controls.patch \
            file://1/0102-media-v4l2-Add-ignore_streaming-flag.patch \
            file://1/0103-media-v4l2-Allow-M2M-job-queuing-w-o-streaming-CAP-q.patch \
            file://1/0109-net-ethernet-am65-cpsw-Add-standard-Ethernet-MAC-sta.patch \
            file://1/0110-net-ethernet-ti-am65-cpsw-Re-arrange-functions-to-av.patch \
            file://1/0111-net-ethernet-am65-cpsw-Set-default-TX-channels-to-ma.patch \
            file://1/0112-net-ethernet-ti-am65-cpsw-Fix-error-handling-in-am65.patch \
            file://1/0118-remoteproc-k3-dsp-Suppress-duplicate-error-message-i.patch \
            file://1/0119-remoteproc-k3-dsp-Use-symbolic-error-codes-in-error-.patch \
            file://1/0120-remoteproc-k3-dsp-Convert-to-platform-remove-callbac.patch \
            file://1/0130-drivers-tidss-Add-support-for-AM62A7-DSS.patch \
            file://1/0138-arm64-dts-ti-k3-j721e-Add-chipid-node-to-wkup_conf-b.patch \
            file://1/0146-net-ethernet-ti-davinci_mdio-Update-K3-SoCs-list-for.patch \
            file://1/0157-soc-ti-k3-socinfo-Fix-typo.patch \
            file://1/0158-soc-ti-k3-socinfo-Avoid-overriding-ret.patch \
            file://1/0159-soc-ti-k3-socinfo-Revamp-driver-to-accommodate-diffe.patch \
            file://1/0180-arm64-dts-ti-k3-j7-Add-additional-regs-for-DMA-compo.patch \
            file://1/0190-net-ethernet-am65-cpsw-Build-am65-cpsw-qos-only-if-r.patch \
            file://1/0191-net-ethernet-am65-cpsw-Rename-TI_AM65_CPSW_TAS-to-TI.patch \
            file://1/0192-net-ethernet-am65-cpsw-cleanup-TAPRIO-handling.patch \
            file://1/0193-net-ethernet-ti-am65-cpsw-Move-code-to-avoid-forward.patch \
            file://1/0194-net-ethernet-am65-cpsw-Move-register-definitions-to-.patch \
            file://1/0195-net-ethernet-ti-am65-cpsw-add-mqprio-qdisc-offload-i.patch \
            file://1/0196-net-ethernet-ti-am65-cpsw-qos-Add-Frame-Preemption-M.patch \
            file://1/0197-net-ethernet-ti-am65-cpsw-add-sw-tx-rx-irq-coalescin.patch \
            file://1/0203-mux-mmio-use-reg-property-when-parent-device-is-not-.patch \
            file://1/0214-media-cadence-csi2rx-add-Y8_1X8-format.patch \
            file://1/0215-media-ti-j721e-csi2rx-add-GREY-format.patch \
            file://1/0216-media-cadence-csi2rx-add-support-for-RGB-formats.patch \
            file://1/0217-media-ti-j721e-csi2rx-add-support-for-RGB-formats.patch \
            file://1/0226-dmaengine-ti-k3-udma-glue-Add-function-to-parse-chan.patch \
            file://1/0227-dmaengine-ti-k3-udma-glue-Update-name-for-remote-RX-.patch \
            file://1/0228-dmaengine-ti-k3-udma-glue-Add-function-to-request-TX.patch \
            file://1/0229-dmaengine-ti-k3-udma-glue-Add-function-to-request-RX.patch \
            file://1/0231-remoteproc-k3-dsp-Use-devm_rproc_alloc-helper.patch \
            file://1/0232-remoteproc-k3-dsp-Add-devm-action-to-release-reserve.patch \
            file://1/0233-remoteproc-k3-dsp-Use-devm_kcalloc-helper.patch \
            file://1/0234-remoteproc-k3-dsp-Use-devm_ti_sci_get_by_phandle-hel.patch \
            file://1/0235-remoteproc-k3-dsp-Use-devm_kzalloc-helper.patch \
            file://1/0236-remoteproc-k3-dsp-Add-devm-action-to-release-tsp.patch \
            file://1/0237-remoteproc-k3-dsp-Use-devm_ioremap_wc-helper.patch \
            file://1/0238-remoteproc-k3-dsp-Use-devm_rproc_add-helper.patch \
            file://1/0252-arm64-dts-ti-k3-j721e-beagleboneai64-Do-not-split-si.patch \
            file://1/0271-phy-ti-gmii-sel-add-resume-support.patch \
            file://1/0279-arm64-dts-ti-k3-j721e-Convert-serdes_ln_ctrl-node-in.patch \
            file://1/0280-arm64-dts-ti-k3-j721e-Convert-usb_serdes_mux-node-in.patch \
            file://1/0303-media-ti-Use-devm_platform_ioremap_resource-in-ti_cs.patch \
            file://1/0310-arm64-dts-ti-k3-j721e-main-Add-CSI2RX-capture-nodes.patch \
            file://3/0001-dma-buf-heaps-Initialize-during-core-instead-of-subs.patch \
            file://3/0002-dma-buf-heaps-Add-Carveout-heap-to-DMA-BUF-Heaps.patch \
            file://3/0003-misc-sram-Add-DMA-BUF-Heap-exporting-of-SRAM-areas.patch \
            file://3/0006-remoteproc-core-Make-Remoteproc-devices-DMA-capable.patch \
            file://3/0007-remoteproc-core-Make-shutdown-on-release-per-file-ha.patch \
            file://3/0008-remoteproc-core-Add-DMA-BUF-attachment-interface-to-.patch \
            file://3/0014-clk-keystone-sci-clk-Adding-support-for-non-contiguo.patch \
            file://4/0002-pinctrl-pinctrl-single-move-suspend-resume-callbacks.patch \
            file://4/0003-serial-8250_omap-Set-the-console-genpd-always-on-if-.patch \
            file://5/0015-arm64-dts-ti-k3-j721e-main-add-gpu-node.patch \
            file://5/0028-firmware-ti_sci-Introduce-Power-Management-Ops.patch \
            file://5/0029-firmware-ti_sci-Add-support-for-querying-the-firmwar.patch \
            file://5/0030-firmware-ti_sci-Allocate-memory-for-Low-Power-Modes.patch \
            file://5/0031-firmware-ti_sci-Add-system-suspend-call.patch \
            file://5/0032-firmware-ti_sci-Change-the-lpm-context-address-to-be.patch \
            file://5/0033-net-ethernet-ti-Add-accessors-for-struct-k3_cppi_des.patch \
            file://5/0034-net-ethernet-ti-Add-desc_infos-member-to-struct-k3_c.patch \
            file://5/0035-net-ethernet-ti-am65-cpsw-Add-minimal-XDP-support.patch \
            file://5/0036-net-ethernet-ti-am65-cpsw-Fix-xdp_rxq-error-for-disa.patch \
            file://5/0039-arm64-dts-ti-k3-j721e-main-add-clock-and-pd-to-gpu.patch \
            file://5/0066-mmc-sdhci_am654-Fix-itapdly-otapdly-array-type.patch \
            file://5/0067-mmc-sdhci_am654-Update-comments-in-sdhci_am654_set_c.patch \
            file://5/0074-remoteproc-core-Select-DMA-BUF-from-REMOTEPROC.patch \
            file://5/0075-remoteproc-core-Free-CDEV-resources-only-after-core-.patch \
            file://5/0076-kernel-reboot-add-device-to-sys_off_handler.patch \
            file://5/0100-remoteproc-k3-m4-Add-a-remoteproc-driver-for-M4F-sub.patch \
            file://5/0116-drm-tidss-Use-pm_runtime_resume_and_get.patch \
            file://5/0117-drm-tidss-Use-PM-autosuspend.patch \
            file://5/0118-drm-tidss-Drop-useless-variable-init.patch \
            file://5/0119-drm-tidss-IRQ-code-cleanup.patch \
            file://5/0120-drm-tidss-Use-DRM_PLANE_COMMIT_ACTIVE_ONLY.patch \
            file://5/0133-drm-tidss-Populate-crtc_-timing-params.patch \
            file://5/0134-drm-bridge-cdns-dsi-Fix-OF-node-pointer.patch \
            file://5/0135-drm-bridge-cdns-dsi-Fix-the-phy_initialized-variable.patch \
            file://5/0136-drm-bridge-cdns-dsi-Fix-the-link-and-phy-init-order.patch \
            file://5/0137-drm-bridge-cdns-dsi-Fix-the-clock-variable-for-mode_.patch \
            file://5/0138-drm-bridge-cdns-dsi-Wait-for-Clk-and-Data-Lanes-to-b.patch \
            file://5/0139-drm-bridge-cdns-dsi-Reset-the-DCS-write-FIFO.patch \
            file://5/0140-drm-bridge-cdns-dsi-Support-atomic-bridge-APIs.patch \
            file://5/0141-drm-bridge-Introduce-early_enable-and-late-disable.patch \
            file://5/0142-drm-bridge-cdns-dsi-Implement-early_enable-and-late_.patch \
            file://5/0146-drm-tidss-Add-OLDI-bridge-support.patch \
            file://5/0162-arm64-dts-ti-k3-j721e-main-Add-DSI-and-DPHY-TX.patch \
            file://5/0176-phy-cadence-torrent-Add-PCIe-100MHz-USXGMII-156.25MH.patch \
            file://5/0177-phy-cadence-torrent-Add-USXGMII-156.25MHz-SGMII-QSGM.patch \
            file://5/0178-phy-cadence-torrent-Add-USXGMII-156.25MHz-SGMII-QSGM.patch \
            file://5/0188-net-ethernet-ti-am65-cpsw-nuss-Enable-SGMII-mode-for.patch \
            file://5/0211-drm-tidss-Add-dispc_is_idle.patch \
            file://5/0212-drm-tidss-Add-some-support-for-splash-screen.patch \
            file://5/0215-drm-bridge-cdns-mhdp8546-core-Move-mode_valid-hook-t.patch \
            file://5/0216-drm-bridge-cdns-mhdp8546-Add-support-for-no-hpd.patch \
            file://5/0235-HACK-arm64-dts-ti-k3-j721e-beagleboneai64-Drop-dp_li.patch \
            file://5/0265-dmaengine-ti-k3-udma-Fix-teardown-for-cyclic-PDMA-tr.patch \
            file://5/0274-media-ti-j721e-csi2rx-Fix-races-while-restarting-DMA.patch \
            file://5/0279-media-v4l2-core-Enable-multi-stream-support.patch \
            file://5/0280-media-cadence-csi2rx-Support-runtime-PM.patch \
            file://5/0284-media-ti-j721e-csi2rx-separate-out-device-and-contex.patch \
            file://5/0285-media-ti-j721e-csi2rx-prepare-SHIM-code-for-multiple.patch \
            file://5/0286-media-ti-j721e-csi2rx-allocate-DMA-channel-based-on-.patch \
            file://5/0287-media-ti-j721e-csi2rx-add-a-subdev-for-the-core-devi.patch \
            file://5/0288-media-ti-j721e-csi2rx-get-number-of-contexts-from-de.patch \
            file://5/0289-media-cadence-csi2rx-add-get_frame_desc-wrapper.patch \
            file://5/0290-media-ti-j721e-csi2rx-add-support-for-processing-vir.patch \
            file://5/0291-media-cadence-csi2rx-Use-new-enable-stream-APIs.patch \
            file://5/0292-media-cadence-csi2rx-Enable-multi-stream-support.patch \
            file://5/0293-media-ti-j721e-csi2rx-add-multistream-support.patch \
            file://5/0294-media-ti-j721e-csi2rx-Submit-all-available-buffers.patch \
            file://5/0295-media-cadence-csi2rx-Support-RAW12-bayer-formats.patch \
            file://5/0296-media-ti-csi2rx-Support-RAW12-bayer-formats.patch \
            file://5/0299-media-i2c-add-Sony-IMX390-driver.patch \
            file://5/0300-media-i2c-imx390-Add-100Khz-input-clock-margin.patch \
            file://5/0301-media-i2c-imx219-Add-mbus_frame_desc-for-pad.patch \
            file://5/0307-media-v4l-Add-10-bit-RGBIr-formats.patch \
            file://5/0309-media-cadence-csi2rx-Add-RAW10-RGBIr-formats.patch \
            file://5/0310-media-ti-j721e-csi2rx-Add-RAW10-RGBIr-formats.patch \
            file://5/0318-soc-ti-k3-socinfo-Add-J721E-SR2.0.patch \
            file://5/0325-media-cadence-csi2rx-Fix-uninitialized-used_vc-varia.patch \
            file://5/0327-Revert-drm-tidss-Add-some-support-for-splash-screen.patch \
            file://5/0330-drm-tidss-Power-up-attached-PM-domains-on-probe.patch \
            file://5/0331-drm-tidss-Add-support-for-AM62P-DSS0.patch \
            file://5/0332-drm-tidss-Add-support-for-AM62P-DSS1.patch \
            file://5/0349-phy-cadence-torrent-Add-SGMII-QSGMII-multilink-confi.patch \
            file://5/0350-rpmsg-char-Update-local-endpt-address-for-virtio-rpm.patch \
            file://5/0351-rpmsg-char-Add-rpmsg_chrdev-rpmsg-device-entry-in-de.patch \
            file://5/0360-firmware-ti_sci-Use-devm_register_restart_handler.patch \
            file://5/0361-firmware-ti_sci-Unconditionally-register-reset-handl.patch \
            file://5/0365-firmware-ti_sci-Partial-IO-support.patch \
            file://5/0366-arm64-dts-ti-k3-pinctrl-Add-WKUP_EN-flag.patch \
            file://5/0378-serial-8250-omap-Remove-unused-wakeups_enabled.patch \
            file://5/0379-serial-8250-omap-Set-wakeup-capable-do-not-enable.patch \
            file://5/0380-serial-8250-omap-Support-wakeup-pinctrl-state.patch \
            file://5/0381-serial-8250-omap-Set-wakeup-pinctrl-on-suspend.patch \
            file://5/0385-phy-cadence-torrent-extract-calls-to-clk_get-from-cd.patch \
            file://5/0386-phy-cadence-torrent-register-resets-even-if-the-phy-.patch \
            file://5/0387-phy-cadence-torrent-add-already_configured-to-struct.patch \
            file://5/0388-phy-cadence-torrent-remove-noop_ops-phy-operations.patch \
            file://5/0389-phy-cadence-torrent-add-suspend-and-resume-support.patch \
            file://5/0400-net-ethernet-ti-am65-cpsw-add-cut-thru-support-for-a.patch \
            file://5/0401-HACK-net-ethernet-ti-am65-cpsw-nuss-add-debugfs-to-c.patch \
            file://5/0403-arm64-dts-ti-k3-j721e-main-Switch-MAIN-R5F-clusters-.patch \
            file://5/0406-arm64-dts-ti-k3-j7xx-Change-timer-nodes-status-to-re.patch \
            file://5/0423-firmware-ti_sci-Invoke-IO-Isolation-in-LPM.patch \
            file://5/0424-firmware-ti_sci-re-assign-suspend-resume-functions-a.patch \
            file://5/0463-clocksource-drivers-timer-ti-dm-Don-t-fail-probe-if-.patch \
            file://6/0026-arm64-dts-ti-k3-j721e-main-add-timesync_router-node.patch \
            file://6/0040-i2c-omap-switch-to-NOIRQ_SYSTEM_SLEEP_PM_OPS-and-RUN.patch \
            file://6/0041-i2c-omap-wakeup-the-controller-during-suspend-callba.patch \
            file://6/0042-mux-add-mux_chip_resume-function.patch \
            file://6/0043-mux-mmio-add-resume-support.patch \
            file://6/0046-PCI-j721e-Use-dev_err_probe-in-the-probe-function.patch \
            file://6/0061-media-ti-j721e-csi2rx-Fix-missing-v4l2-cleanup.patch \
            file://6/0062-HACK-media-ti-j721e-csi2rx-Enable-all-streams-togeth.patch \
            file://6/0063-media-ti-j721e-csi2rx-Add-system-suspend-resume-hook.patch \
            file://6/0064-media-ti-j721e-csi2rx-Restore-streams-on-system-susp.patch \
            file://6/0065-phy-cadence-cdns-dphy-rx-Add-runtime-PM-support.patch \
            file://6/0066-media-ti-j721e-csi2rx-Support-runtime-suspend.patch \
            file://6/0120-media-vxe-vxd-common-Create-mmu-programming-helper-l.patch \
            file://6/0121-media-vxe-vxd-common-Create-vxd_dec-Mem-Manager-help.patch \
            file://6/0122-media-vxe-vxd-decoder-Add-vxd-helper-library.patch \
            file://6/0123-media-vxe-vxd-decoder-Add-IMG-VXD-Video-Decoder-mem-.patch \
            file://6/0124-media-vxe-vxd-decoder-Add-hardware-control-modules.patch \
            file://6/0125-media-vxe-vxd-decoder-Add-vxd-core-module.patch \
            file://6/0126-media-vxe-vxd-decoder-Add-translation-control-module.patch \
            file://6/0127-media-vxe-vxd-common-Add-idgen-api-modules.patch \
            file://6/0128-media-vxe-vxd-common-Add-utility-modules.patch \
            file://6/0129-media-vxe-vxd-common-Add-Address-allocation-manageme.patch \
            file://6/0130-media-vxe-vxd-decoder-Add-VDEC-MMU-wrapper.patch \
            file://6/0131-media-vxe-vxd-decoder-Add-Bistream-Preparser-BSPP-mo.patch \
            file://6/0132-media-vxe-vxd-decoder-Add-Bistream-Preparser-BSPP-mo.patch \
            file://6/0133-media-vxe-vxd-decoder-Add-common-headers.patch \
            file://6/0134-media-vxe-vxd-decoder-Add-firmware-interface-and-cor.patch \
            file://6/0135-media-vxe-vxd-decoder-Add-firmware-interface-headers.patch \
            file://6/0136-media-vxe-vxd-common-Add-pool-api-modules.patch \
            file://6/0137-media-vxe-vxd-common-This-patch-implements-resource-.patch \
            file://6/0138-media-vxe-vxd-decoder-This-patch-implements-pixel-pr.patch \
            file://6/0139-media-vxe-vxd-decoder-vdecdd-utility-library.patch \
            file://6/0140-media-vxe-vxd-decoder-Decoder-resource-component.patch \
            file://6/0141-media-vxe-vxd-decoder-Decoder-Core-Component.patch \
            file://6/0142-media-vxe-vxd-decoder-vdecdd-headers-added.patch \
            file://6/0143-media-vxe-vxd-decoder-Add-Decoder-Component.patch \
            file://6/0144-media-vxe-vxd-common-Add-resource-manager.patch \
            file://6/0145-media-vxe-vxd-common-Add-common-error-defines-and-me.patch \
            file://6/0146-media-vxe-vxd-Makefile-Add-Video-decoder-Makefile.patch \
            file://6/0147-media-vxe-vxd-decoder-Add-V4L2-Interface-function-im.patch \
            file://6/0148-media-vxe-vxd-Add-Encoder-coded-header-generation-fu.patch \
            file://6/0149-media-vxe-vxd-encoder-Add-MTX-Firmware-Interface.patch \
            file://6/0150-media-vxe-vxd-encoder-Add-Device-specific-memory-con.patch \
            file://6/0151-media-vxe-vxd-encoder-Add-Encoder-device-function-im.patch \
            file://6/0152-media-vxe-vxd-encoder-Add-Encoder-Interface-API-func.patch \
            file://6/0153-media-vxe-vxd-encoder-Add-IMG-Encoder-v4l2-Driver-In.patch \
            file://6/0154-media-vxe-vxd-encoder-Add-Encoder-FW-binary-file.patch \
            file://6/0155-media-vxe-vxd-encoder-Add-Firmware-headers.patch \
            file://6/0156-media-vxe-vxd-encoder-Add-Device-register-headers.patch \
            file://6/0157-media-vxe-vxd-encoder-Add-encoder-utility-function-i.patch \
            file://6/0158-media-vxe-vxd-encoder-Add-topaz-mmu-function-impleme.patch \
            file://6/0161-media-vxe-vxd-decoder-Fix-structure-member-initializ.patch \
            file://6/0162-media-vxe-vxd-encoder-Fix-pointer-size-while-typecas.patch \
            file://6/0163-media-vxe-vxd-encoder-Fix-format-specifiers-in-dev_d.patch \
            file://6/0164-media-vxe-vxd-decoder-Fix-for-Cntrl-C-issue.patch \
            file://6/0165-media-vxe-vxd-decoder-Add-seek-functionality.patch \
            file://6/0166-media-vxe-vxd-encoder-Fix-memory-leak-in-vxe-encoder.patch \
            file://6/0167-media-vxe-vxd-decoder-allowing-vb-mapping-to-change-.patch \
            file://6/0168-media-vxe-vxd-decoder-Fix-input-buffer-size.patch \
            file://6/0169-media-vxe-vxd-encoder-Fix-Buffer-Alignment-of-Encode.patch \
            file://6/0170-media-vxe-vxd-decoder-Error-handling-of-fatal-condit.patch \
            file://6/0171-media-vxe-vxd-encoder-Framerate-fix-with-Non-blockin.patch \
            file://6/0172-media-vxe-vxd-encoder-Two-pipe-implementation-for-co.patch \
            file://6/0173-media-vxe-vxd-encoder-Buffer-Alignment-fix-with-4k-p.patch \
            file://6/0174-media-vxe-vxd-encoder-Enable-Continuous-framerate-su.patch \
            file://6/0175-media-vxe-vxd-decoder-Improve-performance-of-h265-de.patch \
            file://6/0176-media-vxe-vxd-decoder-Capture-buffer-cleanup.patch \
            file://6/0178-media-platform-img-Add-the-vxe-vxd-driver-into-the-b.patch \
            file://6/0180-media-platform-img-vxe-vxd-fix-up-a-variety-of-compi.patch \
            file://6/0181-media-v4l2-core-Add-10bit-Custom-definitions-for-NV1.patch \
            file://6/0182-media-platform-img-vxe-vxd-fix-a-array-out-of-bounds.patch \
            file://6/0183-media-img-vxe-vxd-enable-GStreamer-1.20.5.patch \
            file://6/0184-media-platform-img-add-missing-mutex-around-function.patch \
            file://6/0185-media-platform-img-vxd-fix-the-error-handling.patch \
            file://6/0186-media-platform-img-vxd-add-a-sequencing-mutex.patch \
            file://6/0187-media-platform-img-vxe-finish-adding-profile-level-a.patch \
            file://6/0188-media-platform-img-vxd-force-device-node-name.patch \
            file://6/0189-media-platform-img-vxe-vxd-fix-encoder-buffer-size.patch \
            file://6/0190-media-platform-img-vxd-Address-stream-compliance-and.patch \
            file://6/0191-media-platform-img-vxe-vxd-fix-size-of-firmware-comm.patch \
            file://6/0192-media-platform-img-vxd-fix-the-mapping-problem-with-.patch \
            file://6/0193-media-img-vxe-vxd-decoder-Remove-Padding-in-Output.patch \
            file://6/0194-media-img-vxe-vxd-decoder-Suppress-Decoder-Compiler-.patch \
            file://6/0195-media-img-vxe-vxd-decoder-Add-DMA-Coherent-Mask.patch \
            file://6/0196-media-img-vxe-vxd-encoder-Expose-I-Frame-Period-Cont.patch \
            file://6/0197-media-img-vxe-vxd-encoder-Update-RGB-Format.patch \
            file://6/0198-arm64-dts-ti-Video-Encoder-Decoder.patch \
            file://6/0199-media-img-vxe-vxd-encoder-Fix-Compile-Warning.patch \
            file://6/0235-phy-cadence-torrent-Add-PCIe-multilink-configuration.patch \
            file://6/0236-phy-cadence-torrent-Add-PCIe-multilink-USB-with-same.patch \
            file://6/0242-irqchip-irq-ti-sci-inta-Don-t-aggregate-event-until-.patch \
            file://6/0243-irqchip-irq-ti-sci-inta-Introduce-IRQ-affinity-suppo.patch \
            file://6/0244-net-ethernet-ti-am65-cpsw-nuss-Setup-IRQ-affinity-hi.patch \
            file://6/0252-arm64-dts-ti-k3-j721e-main-Add-CSI2RX-multistream-DM.patch \
            file://6/0295-PCI-j721e-Add-support-for-enabling-ACSPCIE-PAD-IO-Bu.patch \
            file://6/0310-dmaengine-ti-k3-udma-Prioritize-CSI-RX-traffic-as-RT.patch \
            file://6/0330-drm-tidss-Add-some-support-for-splash-screen.patch \
            file://6/0331-HACK-drm-tidss-Soft-reset-dispc-if-simple-framebuffe.patch \
            file://6/0337-drm-tidss-Initialize-OLDI-after-dispc_init.patch \
            file://6/0338-remoteproc-pru-Add-support-for-virtio-rpmsg-stack.patch \
            file://6/0339-rpmsg-pru-add-a-PRU-RPMsg-driver.patch \
            file://6/0363-remoteproc-k3-dsp-Set-dma-mask-to-48-bit.patch \
            file://6/0364-remoteproc-k3-r5-Set-dma-mask-to-48-bit.patch \
            file://6/0365-remoteproc-Introduce-mailbox-messages-for-graceful-s.patch \
            file://6/0366-remoteproc-k3-r5-support-for-graceful-shutdown-of-re.patch \
            file://6/0367-remoteproc-k3-dsp-support-for-graceful-shutdown-of-r.patch \
            file://6/0368-remoteproc-k3-m4-support-for-graceful-shutdown-of-re.patch \
            file://6/0369-firmware-ti_sci-Introduce-LPM-Constraint-Ops.patch \
            file://6/0370-firmware-ti_sci-suspend-prepare_sleep-support-DM_MAN.patch \
            file://6/0371-firmware-ti_sci-add-CPU-latency-constraint-managemen.patch \
            file://6/0372-pmdomain-ti_sci-add-per-device-latency-constraint-ma.patch \
            file://6/0373-pmdomain-ti_sci-add-wakeup-constraint-management.patch \
            file://6/0374-remoteproc-k3_r5-add-expose-resume-latency-constrain.patch \
            file://6/0376-drm-tidss-Deinitialize-OLDI-before-module-remove.patch \
            file://6/0379-drm-tidss-Add-support-for-display-sharing.patch \
            file://6/0380-drm-tidss-Fix-error-paths-after-display-initializati.patch \
            file://6/0381-fbdev-simplefb-Add-support-to-power-up-multiple-PM-d.patch \
            file://6/0384-drm-tidss-fix-uninitialized-variable.patch \
            file://6/0385-pmdomain-ti-ti_sci_pm_domains-Fix-build-error-with-C.patch \
            file://6/0386-drm-tidss-Fix-warnings-on-error-prints.patch \
            file://6/0399-net-ethernet-ti-fix-duplicate-entry-for-k3-cppi-desc.patch \
            file://6/0401-firmware-ti_sci-recover-properly-when-firmware-NAKs.patch \
            file://6/0402-pmdomain-ti_sci-handle-wake-IRQs-for-IO-daisy-chain-.patch \
            file://6/0403-media-img-vxe-vxd-fix-incorrect-allocation-sizes.patch \
            file://6/0405-media-img-vxe-vxd-fix-incomplete-decoder-initializat.patch \
            file://6/0410-remoteproc-k3-r5-Introduce-PM-suspend-resume-handler.patch \
            file://6/0411-remoteproc-k3-m4-Introduce-PM-suspend-resume-handler.patch \
            file://6/0412-mailbox-omap-Move-suspend-to-suspend_late.patch \
            file://6/0413-remoteproc-k3-r5-Move-suspend-to-suspend_late.patch \
            file://6/0414-remoteproc-k3-m4-Move-suspend-to-suspend_late.patch \
            file://6/0415-firmware-ti_sci-Call-prepare_sleep-in-suspend.patch \
            file://6/0422-media-imagination-vxe-vxd-encoder-Fix-kmalloc-Alignm.patch \
            file://6/0436-remoteproc-core-Make-DMA-BUF-attachment-interface-th.patch \
            file://6/0440-mailbox-omap-Convert-to-platform-remove-callback-ret.patch \
            file://6/0441-mailbox-omap-Remove-unused-omap_mbox_-enable-disable.patch \
            file://6/0442-mailbox-omap-Remove-unused-omap_mbox_request_channel.patch \
            file://6/0443-mailbox-omap-Move-omap_mbox_irq_t-into-driver.patch \
            file://6/0444-mailbox-omap-Move-fifo-size-check-to-point-of-use.patch \
            file://6/0445-mailbox-omap-Remove-unneeded-header-omap-mailbox.h.patch \
            file://6/0446-mailbox-omap-Remove-device-class.patch \
            file://6/0447-mailbox-omap-Use-devm_pm_runtime_enable-helper.patch \
            file://6/0448-mailbox-omap-Merge-mailbox-child-node-setup-loops.patch \
            file://6/0449-mailbox-omap-Use-function-local-struct-mbox_controll.patch \
            file://6/0450-mailbox-omap-Use-mbox_controller-channel-list-direct.patch \
            file://6/0451-mailbox-omap-Remove-mbox_chan_to_omap_mbox.patch \
            file://6/0452-mailbox-omap-Reverse-FIFO-busy-check-logic.patch \
            file://6/0453-mailbox-omap-Remove-kernel-FIFO-message-queuing.patch \
            file://6/0454-mailbox-omap-Fix-mailbox-interrupt-sharing.patch \
            file://6/0473-remoteproc-k3-r5-Use-devm_rproc_alloc-helper.patch \
            file://6/0475-remoteproc-k3-dsp-Acquire-mailbox-handle-during-prob.patch \
            file://7/0001-firmware-ti_sci-Fix-CPU-latency-constraint-device-de.patch \
            file://7/0002-media-cadence-csi2rx-fix-uninitialized-frame-descrip.patch \
            file://7/0024-mmc-sdhci_am654-Add-sdhci_am654_start_signal_voltage.patch \
            file://7/0025-mmc-sdhci_am654-Add-retry-tuning.patch \
            file://7/0026-mmc-sdhci_am654-Add-prints-to-tuning-algorithm.patch \
            file://7/0028-remoteproc-k3-r5-keep-the-device-on-during-stop.patch \
            file://7/0050-arm64-dts-ti-k3-j721e-Make-the-scm_conf-node-a-simpl.patch \
            file://7/0056-media-cadence-csi2rx-Fix-unnecessary-s_stream-call.patch \
            file://7/0060-v4l2-controls-Add-Control-for-Background-Detection.patch \
            file://7/0062-drm-bridge-cdns-mhdp8546-Fix-possible-null-pointer-d.patch \
            file://7/0071-remoteproc-k3-r5-Drop-check-performed-in-k3_r5_rproc.patch \
            file://7/0072-remoteproc-k3-dsp-Drop-check-performed-in-k3_dsp_rpr.patch \
            file://7/0088-remoteproc-k3-r5-fix-unused-variable.patch \
            file://7/0092-arm64-dts-ti-k3-j7-Add-phase-detect-selector-value-i.patch \
            file://7/0125-drm-tidss-Remove-unused-OCP-error-flag.patch \
            file://7/0126-drm-tidss-Remove-extra-K2G-check.patch \
            file://7/0127-drm-tidss-Add-printing-of-underflows.patch \
            file://7/0130-drm-tidss-Rename-wait_lock-to-irq_lock.patch \
            file://7/0150-arm64-dts-ti-k3-j721e-Add-MIT-license-along-with-GPL.patch \
            file://7/0153-arm64-dts-ti-k3-pinctrl-Add-MIT-license-along-with-G.patch \
            file://7/0154-arm64-dts-ti-k3-serdes-Add-MIT-license-along-with-GP.patch \
            file://7/0155-arm64-dts-ti-beagle-Add-MIT-license-along-with-GPL-2.patch \
            file://7/0164-TEMP-media-imagination-vxe-vxd-encoder-Fix-RGB-Crash.patch \
            file://7/0170-remoteproc-k3-dsp-Introduce-PM-suspend-resume-handle.patch \
            file://7/0172-HACK-gpio-davinci-Restore-GPIO-context-early-in-ti_s.patch \
            file://0001_fix_nonlinux_compile.patch \
            file://0002-bootup-hacks-move-mmc-early.patch \
            file://0004_wait-for-rootfs.patch \
            file://0011_fix-mhdp-reg.patch \
            file://0014-WIP-tusb322.patch \
            file://0015_enable-snd-soc-hdmi-codec.patch \
            file://ti_config \
"

SRC_URI[sha256sum] = "8957e5c2dacdbc47a16dbf1f6303ca7088409be6197a3881f752313275357ac6"

S = "${WORKDIR}/linux-${PV}"

do_applypath() {
    sed 's|MOBIAQUA_FW_PATH|${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/|g' ${WORKDIR}/ti_config > ${S}/arch/${ARCH}/configs/ti_defconfig
}

addtask applypath before do_patch after do_unpack
