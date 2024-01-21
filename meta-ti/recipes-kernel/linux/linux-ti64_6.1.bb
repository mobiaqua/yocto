require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native linux-firmware"
COMPATIBLE_MACHINE = "beagle64"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.1.74"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:beagle64 = "ti/k3-j721e-beagleboneai64.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti64_6.1:"
KBUILD_DEFCONFIG = "ti_defconfig"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${LINUX_VERSION}.tar.xz \
            file://1/0001-kbuild-Allow-DTB-overlays-to-built-from-.dtso-named-.patch \
            file://1/0002-kbuild-Allow-DTB-overlays-to-built-into-.dtbo.S-file.patch \
            file://1/0003-of-overlay-rename-overlay-source-files-from-.dts-to-.patch \
            file://1/0005-kbuild-Cleanup-DT-Overlay-intermediate-files-as-appr.patch \
            file://1/0009-kbuild-Disallow-DTB-overlays-to-built-from-.dts-name.patch \
            file://1/0036-arm64-dts-ti-k3-j721e-Enable-UART-nodes-at-the-board.patch \
            file://1/0037-arm64-dts-ti-k3-j721e-Enable-I2C-nodes-at-the-board-.patch \
            file://1/0038-arm64-dts-ti-k3-j721e-Enable-MCASP-nodes-at-the-boar.patch \
            file://1/0039-arm64-dts-ti-k3-j721e-Enable-MCAN-nodes-at-the-board.patch \
            file://1/0043-arm64-dts-ti-k3-j721e-Enable-Mailbox-nodes-at-the-bo.patch \
            file://1/0066-dmaengine-ti-convert-k3-udma-to-module.patch \
            file://1/0067-dmaengine-ti-convert-PSIL-to-be-buildable-as-module.patch \
            file://1/0069-dmaengine-ti-k3-udma-Fix-BCDMA-for-case-w-o-BCHAN.patch \
            file://1/0072-dmaengine-ti-k3-udma-Add-support-for-BCDMA-CSI-RX.patch \
            file://1/0078-arm64-dts-Update-cache-properties-for-ti.patch \
            file://1/0090-arm64-dts-ti-k3-j721e-main-Drop-RNG-clock.patch \
            file://1/0127-arm64-dts-ti-Use-local-header-for-pinctrl-register-v.patch \
            file://1/0149-dmaengine-ti-k3-udma-glue-do-not-create-glue-dma-dev.patch \
            file://1/0162-arm64-dts-ti-k3-j721e-Add-MCSPI-nodes.patch \
            file://1/0166-phy-Remove-unused-phy_optional_get.patch \
            file://1/0168-phy-Add-devm_of_phy_optional_get-helper.patch \
            file://1/0175-phy-ti-gmii-sel-Update-methods-for-fetching-and-usin.patch \
            file://1/0176-phy-ti-gmii-sel-Add-support-for-CPSW9G-GMII-SEL-in-J.patch \
            file://1/0179-ptp-convert-remaining-drivers-to-adjfine-interface.patch \
            file://1/0201-arm64-dts-ti-k3-j721e-Add-CPSW9G-nodes.patch \
            file://1/0208-dmaengine-ti-k3-udma-Add-system-suspend-resume-suppo.patch \
            file://1/0210-net-ethernet-ti-cpsw_ale-Add-cpsw_ale_restore-helper.patch \
            file://1/0231-dmaengine-ti-k3-udma-Workaround-errata-i2234.patch \
            file://1/0232-dmaengine-ti-k3-udma-remove-non-fatal-probe-deferral.patch \
            file://1/0239-dma-buf-heaps-Initialize-during-core-instead-of-subs.patch \
            file://1/0240-dma-buf-heaps-Add-Carveout-heap-to-DMA-BUF-Heaps.patch \
            file://1/0241-misc-sram-Add-DMA-BUF-Heap-exporting-of-SRAM-areas.patch \
            file://1/0245-HACK-misc-Add-dma-buf-to-physical-address-exporter.patch \
            file://1/0256-arm64-dts-ti-k3-j721e-main-add-gpu-node.patch \
            file://1/0259-phy-dphy-refactor-get_default_config.patch \
            file://1/0260-phy-dphy-add-support-to-calculate-the-timing-based-o.patch \
            file://1/0261-phy-cadence-cdns-dphy-rx-Add-common-module-reset-sup.patch \
            file://1/0269-media-Accept-non-subdev-sinks-in-v4l2_create_fwnode_.patch \
            file://1/0273-media-cadence-csi2rx-Cleanup-media-entity-properly.patch \
            file://1/0274-media-cadence-csi2rx-Add-get_fmt-and-set_fmt-pad-ops.patch \
            file://1/0275-media-cadence-csi2rx-Add-external-DPHY-support.patch \
            file://1/0276-media-cadence-csi2rx-Soft-reset-the-streams-before-s.patch \
            file://1/0277-media-cadence-csi2rx-Set-the-STOP-bit-when-stopping-.patch \
            file://1/0278-media-cadence-csi2rx-Fix-stream-data-configuration.patch \
            file://1/0279-media-cadence-csi2rx-Populate-subdev-devnode.patch \
            file://1/0280-media-cadence-csi2rx-Add-link-validation.patch \
            file://1/0282-media-ti-Add-CSI2RX-support-for-J721E.patch \
            file://1/0284-arm64-dts-ti-k3-j721e-main-Add-nodes-to-enable-CSI2-.patch \
            file://1/0312-media-ti-j721e-csi2rx-separate-out-device-and-contex.patch \
            file://1/0313-media-ti-j721e-csi2rx-prepare-SHIM-code-for-multiple.patch \
            file://1/0314-media-ti-j721e-csi2rx-allocate-DMA-channel-based-on-.patch \
            file://1/0315-media-ti-j721e-csi2rx-rename-csi-subdev-to-csi-sourc.patch \
            file://1/0316-media-ti-j721e-csi2rx-add-a-subdev-for-the-core-devi.patch \
            file://1/0317-media-ti-j721e-csi2rx-bump-number-of-contexts-to-16.patch \
            file://1/0318-media-ti-j721e-csi2rx-get-number-of-contexts-from-de.patch \
            file://1/0319-media-ti-j721e-csi2rx-Set-the-data-size-shift-correc.patch \
            file://1/0320-media-ti-j721e-csi2rx-Enable-DMA-draining.patch \
            file://1/0321-media-cadence-ti-Support-RAW8-10-12-formats.patch \
            file://1/0322-media-cadence-csi2rx-Support-runtime-PM.patch \
            file://1/0323-media-ti-j721e-csi2rx-Add-system-suspend-resume-hook.patch \
            file://1/0331-drm-tidss-Rename-hw_videoport-to-vp_idx.patch \
            file://1/0332-drm-tidss-Remove-Video-Port-to-Output-Port-coupling.patch \
            file://1/0333-drm-tidss-Configure-different-OLDI-modes.patch \
            file://1/0334-drm-tidss-Rename-AM65x-DSS-OLDI-CTRL-MMR-registers.patch \
            file://1/0335-drm-tidss-Add-support-for-AM625-DSS.patch \
            file://1/0336-drm-tidss-Add-IO-CTRL-and-Power-support-for-OLDI-TX-.patch \
            file://1/0337-HACK-drm-tidss-Update-the-clock-request-value-for-OL.patch \
            file://1/0351-drm-tidss-Add-support-for-AM62A7-DSS.patch \
            file://1/0359-v4l-vxd-dec-Create-mmu-programming-helper-library.patch \
            file://1/0360-v4l-vxd-dec-Create-vxd_dec-Mem-Manager-helper-librar.patch \
            file://1/0361-v4l-vxd-dec-Add-vxd-helper-library.patch \
            file://1/0362-v4l-vxd-dec-Add-IMG-VXD-Video-Decoder-mem-to-mem-dri.patch \
            file://1/0363-v4l-vxd-dec-Add-hardware-control-modules.patch \
            file://1/0364-v4l-vxd-dec-Add-vxd-core-module.patch \
            file://1/0365-v4l-vxd-dec-Add-translation-control-modules.patch \
            file://1/0366-v4l-vxd-dec-Add-idgen-api-modules.patch \
            file://1/0367-v4l-vxd-dec-Add-utility-modules.patch \
            file://1/0368-v4l-vxd-dec-Add-Address-allocation-management-APIs-m.patch \
            file://1/0369-v4l-vxd-dec-Add-VDEC-MMU-wrapper.patch \
            file://1/0370-v4l-vxd-dec-Add-Bistream-Preparser-BSPP-module-for-H.patch \
            file://1/0371-v4l-vxd-dec-Add-Bistream-Preparser-BSPP-module-for-H.patch \
            file://1/0372-v4l-vxd-dec-Add-common-headers.patch \
            file://1/0373-v4l-vxd-dec-Add-firmware-interface-and-core-Register.patch \
            file://1/0374-v4l-vxd-dec-Add-firmware-interface-headers.patch \
            file://1/0375-v4l-vxd-dec-Add-pool-api-modules.patch \
            file://1/0376-v4l-vxd-dec-This-patch-implements-resource-manage-co.patch \
            file://1/0377-v4l-vxd-dec-This-patch-implements-pixel-processing-l.patch \
            file://1/0378-v4l-vxd-dec-vdecdd-utility-library.patch \
            file://1/0379-v4l-vxd-dec-Decoder-resource-component.patch \
            file://1/0380-v4l-vxd-dec-Decoder-Core-Component.patch \
            file://1/0381-v4l-vxd-dec-vdecdd-headers-added.patch \
            file://1/0382-v4l-vxd-dec-Add-Decoder-Component.patch \
            file://1/0383-v4l-vxd-dec-Add-resource-manager.patch \
            file://1/0384-v4l-vxd-dec-Add-common-error-defines-and-memory-pool.patch \
            file://1/0385-media-platform-vxe-vxd-Makefile-Add-Video-decoder-Ma.patch \
            file://1/0386-v4l-Add-V4L2-Interface-function-implementations.patch \
            file://1/0387-v4l-vxe-enc-Add-Encoder-coded-header-generation-func.patch \
            file://1/0388-v4l-vxe-enc-Add-MTX-Firmware-Interface.patch \
            file://1/0389-v4l-vxe-enc-Add-Device-specific-memory-configuration.patch \
            file://1/0390-v4l-vxe-enc-Add-Encoder-device-function-implementati.patch \
            file://1/0391-v4l-vxe-enc-Add-Encoder-Interface-API-function-imple.patch \
            file://1/0392-v4l-vxe-enc-Add-IMG-Encoder-v4l2-Driver-Interface-fu.patch \
            file://1/0393-v4l-vxe-enc-Add-Encoder-FW-binary-file.patch \
            file://1/0394-v4l-vxe-enc-Add-Firmware-headers.patch \
            file://1/0395-v4l-vxe-enc-Add-Device-register-headers.patch \
            file://1/0396-v4l-vxe-enc-Add-encoder-utility-function-implementat.patch \
            file://1/0397-v4l-vxe-enc-Add-topaz-mmu-function-implementations.patch \
            file://1/0399-media-platform-Makefile-Fix-build-failure-in-paralle.patch \
            file://1/0400-vxe-vxd-common-Fix-structure-member-initialization-s.patch \
            file://1/0401-vxe-vxd-common-Fix-pointer-size-while-typecasting-to.patch \
            file://1/0402-vxe-vxd-common-Fix-format-specifiers-in-dev_dbg-prin.patch \
            file://1/0403-vxe-vxd-decoder-Fix-for-Cntrl-C-issue.patch \
            file://1/0404-vxe-vxd-decoder-Add-seek-functionality.patch \
            file://1/0405-vxe-vxd-encoder-Fix-memory-leak-in-vxe-encoder-drive.patch \
            file://1/0406-vxe-vxd-decoder-allowing-vb-mapping-to-change-for-bu.patch \
            file://1/0407-vxe-vxd-decoder-Fix-input-buffer-size.patch \
            file://1/0408-vxe-vxd-encoder-Fix-Buffer-Alignment-of-Encoder-buff.patch \
            file://1/0409-vxe-vxd-decoder-Error-handling-of-fatal-condition.patch \
            file://1/0410-vxe-vxd-encoder-Framerate-fix-with-Non-blocking-QBUF.patch \
            file://1/0411-vxe-vxd-encoder-Two-pipe-implementation-for-consecut.patch \
            file://1/0412-vxe-vxd-encoder-Buffer-Alignment-fix-with-4k-page-si.patch \
            file://1/0413-vxe-vxd-encoder-Enable-Continuous-framerate-support.patch \
            file://1/0414-vxe-vxd-decoder-Improve-performance-of-h265-decoder.patch \
            file://1/0415-vxe-vxd-decoder-Capture-buffer-cleanup.patch \
            file://1/0416-v4l-videodev2-Add-10bit-definitions-for-NV12-and-NV1.patch \
            file://1/0418-media-img-vxe-vxd-update-macro-change-from-kernel-5..patch \
            file://1/0419-media-img-vxe-vxd-add-vxe-vxd-driver-to-MAINTAINERS-.patch \
            file://1/0420-media-platform-img-add-the-vxe-vxd-driver-into-the-b.patch \
            file://1/0424-arm64-dts-ti-k3-j721e-main-Add-v4l2-vxe_enc-device-n.patch \
            file://1/0425-arm64-dts-ti-k3-j721e-main-Add-v4l2-vxd_dec-device-n.patch \
            file://1/0426-media-platform-img-vxe-vxd-fix-up-a-variety-of-compi.patch \
            file://1/0427-media-platform-img-vxe-vxd-fix-a-array-out-of-bounds.patch \
            file://1/0435-phy-cadence-torrent-Add-function-to-get-PLL-to-be-co.patch \
            file://1/0436-phy-cadence-torrent-Prepare-driver-for-multilink-DP-.patch \
            file://1/0437-phy-cadence-torrent-Add-PCIe-DP-multilink-configurat.patch \
            file://1/0438-phy-cadence-torrent-Add-USB-DP-multilink-configurati.patch \
            file://1/0443-arm64-dts-ti-k3-j721e-main-Update-delay-select-value.patch \
            file://1/0446-phy-ti-gmii-sel-Add-support-for-SGMII-mode.patch \
            file://1/0448-phy-ti-gmii-sel-Enable-SGMII-mode-for-J721E.patch \
            file://1/0460-rpmsg-char-Update-local-endpt-address-for-virtio-rpm.patch \
            file://1/0461-rpmsg-char-Add-rpmsg_chrdev-rpmsg-device-entry-in-de.patch \
            file://1/0462-samples-rpmsg-Add-compatible-to-support-TI-IPC-firmw.patch \
            file://1/0464-remoteproc-k3-c7x-Add-support-for-C7xv-DSP-on-AM62A-.patch \
            file://1/0469-remoteproc-k4-Split-out-functions-common-with-M4-dri.patch \
            file://1/0470-remoteproc-k4-m4-Add-a-remoteproc-driver-for-M4F-sub.patch \
            file://1/0506-arm64-dts-ti-k3-j721e-main-cutdown-gpu-node.patch \
            file://2/0007-media-v4l2-subdev-Sort-includes.patch \
            file://2/0008-media-add-V4L2_SUBDEV_FL_STREAMS.patch \
            file://2/0009-media-add-V4L2_SUBDEV_CAP_STREAMS.patch \
            file://2/0011-media-subdev-Add-GS-_ROUTING-subdev-ioctls-and-opera.patch \
            file://2/0012-media-subdev-Require-code-change-to-enable-GS-_ROUTI.patch \
            file://2/0013-media-subdev-add-v4l2_subdev_has_pad_interdep.patch \
            file://2/0014-media-subdev-add-v4l2_subdev_set_routing-helper.patch \
            file://2/0015-media-subdev-Add-for_each_active_route-macro.patch \
            file://2/0017-media-subdev-add-stream-based-configuration.patch \
            file://2/0018-media-subdev-use-streams-in-v4l2_subdev_link_validat.patch \
            file://2/0019-media-subdev-add-opposite-stream-helper-funcs.patch \
            file://2/0020-media-subdev-add-streams-to-v4l2_subdev_get_fmt-help.patch \
            file://2/0021-media-subdev-add-v4l2_subdev_set_routing_with_fmt-he.patch \
            file://2/0022-media-subdev-add-v4l2_subdev_routing_validate-helper.patch \
            file://2/0023-media-v4l2-subdev-Add-v4l2_subdev_state_xlate_stream.patch \
            file://2/0024-media-v4l2-subdev-Add-subdev-.-enable-disable-_strea.patch \
            file://2/0025-media-v4l2-subdev-Add-v4l2_subdev_s_stream_helper-fu.patch \
            file://2/0026-media-Add-stream-to-frame-descriptor.patch \
            file://2/0028-media-subdev-Use-shall-instead-of-may-in-route-valid.patch \
            file://2/0029-media-subdev-Split-V4L2_SUBDEV_ROUTING_NO_STREAM_MIX.patch \
            file://2/0030-media-subdev-Add-V4L2_SUBDEV_ROUTING_NO_MULTIPLEXING.patch \
            file://2/0031-media-subdev-Fix-validation-state-lockdep-issue.patch \
            file://2/0032-media-v4l-subdev-Make-link-validation-safer.patch \
            file://2/0041-media-v4l-Add-10-bit-RGBIr-formats.patch \
            file://2/0042-media-v4l2-core-Enable-streams-api.patch \
            file://2/0046-media-cadence-csi2rx-Use-new-enable-stream-APIs.patch \
            file://2/0047-media-cadence-csi2rx-configure-DPHY-before-starting-.patch \
            file://2/0048-media-cadence-csi2rx-Propagate-set_fmt-from-sink-to-.patch \
            file://2/0049-media-cadence-csi2rx-Let-all-virtual-channels-throug.patch \
            file://2/0050-media-cadence-csi2rx-add-get_frame_desc-wrapper.patch \
            file://2/0051-media-cadence-csi2rx-Add-RAW10-RGBIr-formats.patch \
            file://2/0052-media-cadence-csi2rx-Enable-stream-wise-routing.patch \
            file://2/0053-media-cadence-csi2rx-Enable-per-stream-controls.patch \
            file://2/0054-media-ti-j721e-csi2rx-add-support-for-processing-vir.patch \
            file://2/0055-media-ti-j721e-csi2rx-add-multistream-support.patch \
            file://2/0056-media-ti-j721e-csi2rx-Reject-non-zero-index-for-enum.patch \
            file://2/0057-media-ti-j721e-csi2rx-Add-get_fmt-and-set_fmt-pad-op.patch \
            file://2/0058-media-ti-j721e-csi2rx-Add-RAW10-RGBIr-formats.patch \
            file://2/0059-media-ti-j721e-csi2rx-Enable-per-stream-controls.patch \
            file://2/0060-dmaengine-ti-k3-udma-Prioritize-CSI-RX-traffic-as-RT.patch \
            file://2/0062-media-i2c-add-Sony-IMX390-driver.patch \
            file://2/0063-media-i2c-imx390-Add-100Khz-input-clock-margin.patch \
            file://2/0082-crypto-sa2ul-change-unsafe-data-size-limit-to-255-by.patch \
            file://2/0091-drm-bridge-cdns-mhdp8546-Add-support-for-no-hpd.patch \
            file://2/0171-arm64-dts-ti-j721e-Add-VTM-node.patch \
            file://2/0194-phy-cadence-Sierra-Add-PCIe-SGMII-PHY-multilink-conf.patch \
            file://2/0202-drm-bridge-cdns-dsi-Move-to-drm-bridge-cadence.patch \
            file://2/0203-drm-bridge-cdns-dsi-Create-a-header-file.patch \
            file://2/0204-drm-bridge-cdns-dsi-Add-support-for-J721E-wrapper.patch \
            file://2/0205-drm-bridge-cdns-dsi-Fix-issue-with-phy-init.patch \
            file://2/0206-drm-bridge-cdns-dsi-Fix-cdns_dsi_attach.patch \
            file://2/0210-drm-bridge-cdns-mhdp8546-Fix-bridge-attach-for-no-hp.patch \
            file://2/0223-cpufreq-ti-Enable-ti-cpufreq-for-ARCH_K3.patch \
            file://2/0235-arm64-dts-ti-k3-j721e-main-add-timesync_router-node.patch \
            file://2/0255-thermal-k3_j72xx_bandgap-Add-cooling-device-support.patch \
            file://2/0271-media-ti-j721e-csi2rx-Restore-streams-on-system-susp.patch \
            file://2/0279-arm64-dts-ti-k3-j721e-main-Switch-MAIN-R5F-clusters-.patch \
            file://2/0335-media-img-vxe-vxd-enable-GStreamer-1.20.5.patch \
            file://2/0336-media-platform-img-add-missing-mutex-around-function.patch \
            file://2/0348-phy-cadence-torrent-Add-single-link-USXGMII-configur.patch \
            file://2/0349-phy-cadence-torrent-Use-key-value-pair-table-for-all.patch \
            file://2/0350-phy-cadence-torrent-Add-PCIe-100MHz-USXGMII-156.25MH.patch \
            file://2/0351-phy-cadence-torrent-Add-USXGMII-156.25MHz-SGMII-QSGM.patch \
            file://2/0357-remoteproc-ti-k3-Add-support-for-graceful-shutdown.patch \
            file://2/0358-remoteproc-k3-m4-Introduce-PM-suspend-resume-handler.patch \
            file://2/0366-iopoll-Do-not-use-timekeeping-in-read_poll_timeout_a.patch \
            file://2/0397-clk-keystone-syscon-clk-Allow-the-clock-node-to-not-.patch \
            file://2/0404-dma-buf-heaps-carveout-heap-initialize-ret-variable.patch \
            file://2/0405-drm-tidss-add-missing-break.patch \
            file://3/0002-kernel-reboot-add-device-to-sys_off_handler.patch \
            file://3/0025-media-ti-j721e-csi2rx-Fix-stream-stop-sequence.patch \
            file://3/0026-remoteproc-k3-m4-set-as-wakeup-capable-but-keep-disa.patch \
            file://3/0036-remoteproc-ti_k3_m4_remoteproc-Fix-compiler-warning.patch \
            file://3/0040-irqchip-irq-ti-sci-inta-Don-t-aggregate-event-until-.patch \
            file://3/0041-irqchip-irq-ti-sci-inta-Introduce-IRQ-affinity-suppo.patch \
            file://3/0050-media-platform-img-vxd-fix-the-error-handling.patch \
            file://3/0051-media-platform-img-vxd-add-a-sequencing-mutex.patch \
            file://3/0061-media-platform-img-vxe-finish-adding-profile-level-a.patch \
            file://3/0078-irqchip-irq-ti-sci-inta-Add-null-check-for-parent_ir.patch \
            file://3/0081-media-ti-j721e-csi2rx-Fix-buffer-cleanup-sequence.patch \
            file://3/0121-arm64-dts-ti-k3-j721e-main-Add-properties-to-support.patch \
            file://3/0127-drm-tidss-Set-OLDI-clock-to-bypass-25MHz-during-prob.patch \
            file://3/0128-arm64-dts-ti-Add-k3-j721e-beagleboneai64.patch \
            file://3/0129-arm64-dts-ti-k3-j721e-beagleboneai64-Fix-mailbox-nod.patch \
            file://3/0130-arm64-dts-ti-k3-j721e-Remove-PCIe-endpoint-nodes.patch \
            file://3/0131-arm64-dts-ti-k3-j721e-Enable-PCIe-nodes-at-the-board.patch \
            file://3/0132-arm64-dts-ti-k3-j721e-Enable-MDIO-nodes-at-the-board.patch \
            file://3/0133-arm64-dts-ti-k3-j721e-beagleboneai64-Move-camera-gpi.patch \
            file://3/0134-arm64-dts-ti-k3-j721e-beagleboneai64-Move-eeprom-WP-.patch \
            file://3/0135-arm64-dts-ti-k3-j721e-beagleboneai64-Fixup-reference.patch \
            file://3/0136-TEMP-media-img-vxe-vxd-decoder-Disable-CMA-for-captu.patch \
            file://3/0139-arm64-dts-ti-k3-j721e-main-Add-dts-nodes-for-EHRPWMs.patch \
            file://4/0007-media-ti-j721e-csi2rx-Make-use-of-V4L2_CAP_IO_MC.patch \
            file://4/0008-drm-tidss-Make-zpos-immutable-for-primary-plane.patch \
            file://4/0019-thermal-drivers-k3_j72xx_bandgap-Simplify-k3_thermal.patch \
            file://4/0020-thermal-drivers-k3_j72xx_bandgap-Use-bool-for-i2128-.patch \
            file://4/0021-thermal-drivers-k3_j72xx_bandgap-Remove-fuse_base-fr.patch \
            file://4/0022-thermal-drivers-k3_j72xx_bandgap-Map-fuse_base-only-.patch \
            file://4/0030-drivers-mmc-host-sdhci_am654-update-OTAP-and-ITAP-de.patch \
            file://5/0002-media-ti-j721e-csi2rx-Assert-pixel-reset-before-stop.patch \
            file://5/0003-media-cadence-csi2rx-Fix-check-for-running-bit.patch \
            file://5/0008-misc-dma-buf-phys-Add-dependency-on-DMA-BUF.patch \
            file://5/0016-HACK-media-ti-j721e-csi2rx-Enable-all-streams-togeth.patch \
            file://5/0017-media-ti-j721e-csi2rx-Assert-pixel-reset-before-stop.patch \
            file://5/0031-media-ti-j721e-csi2rx-Fix-broken-UYVY-format-orderin.patch \
            file://5/0037-media-platform-img-vxd-force-device-node-name.patch \
            file://5/0038-media-platform-img-vxe-vxd-fix-encoder-buffer-size.patch \
            file://5/0039-media-platform-img-vxd-Address-stream-compliance-and.patch \
            file://5/0040-media-platform-img-vxe-vxd-fix-size-of-firmware-comm.patch \
            file://5/0041-media-platform-img-vxd-fix-the-mapping-problem-with-.patch \
            file://5/0053-Revert-drm-tidss-Make-zpos-immutable-for-primary-pla.patch \
            file://5/0054-media-ti-j721e-csi2rx-Support-runtime-suspend.patch \
            file://5/0055-phy-cadence-cdns-dphy-rx-Add-runtime-PM-support.patch \
            file://5/0057-HACK-drm-tidss-Power-up-attached-PM-domains-on-probe.patch \
            file://5/0094-drm-tidss-Add-support-for-AM62P-DSS0.patch \
            file://5/0129-phy-ti-gmii-sel-Allow-parent-to-not-be-syscon-node.patch \
            file://5/0130-phy-ti-gmii-sel-Fix-register-offset-when-parent-is-n.patch \
            file://5/0153-Revert-drm-tidss-Annotate-dma-fence-critical-section.patch \
            file://5/0154-drm-tidss-Use-pm_runtime_resume_and_get.patch \
            file://5/0155-drm-tidss-Use-PM-autosuspend.patch \
            file://5/0156-drm-tidss-Remove-early-fb.patch \
            file://5/0157-drm-tidss-Drop-useless-variable-init.patch \
            file://5/0158-drm-tidss-Fix-OLDI-default-rate-setup.patch \
            file://5/0159-drm-tidss-Return-error-value-from-from-softreset.patch \
            file://5/0160-drm-tidss-Check-for-K2G-in-in-dispc_softreset.patch \
            file://5/0161-drm-tidss-Add-simple-K2G-manual-reset.patch \
            file://5/0162-drm-tidss-Fix-dss-reset.patch \
            file://5/0163-drm-tidss-Add-dispc_is_idle.patch \
            file://5/0164-drm-tidss-IRQ-code-cleanup.patch \
            file://5/0165-drm-tidss-Add-some-support-for-splash-screen.patch \
            file://5/0166-drm-tidss-Fix-atomic_flush-check.patch \
            file://5/0167-drm-tidss-Use-DRM_PLANE_COMMIT_ACTIVE_ONLY.patch \
            file://5/0205-bus-ti-sysc-Build-driver-for-TI-K3-SoCs.patch \
            file://5/0207-watchdog-rti_wdt-Use-managed-APIs-to-handle-runtime-.patch \
            file://5/0208-watchdog-rti_wdt-Drop-RPM-watchdog-when-unused.patch \
            file://5/0212-media-img-vxe-vxd-decoder-Remove-Padding-in-Output.patch \
            file://5/0213-dmaengine-ti-k3-udma-glue-Add-function-to-parse-chan.patch \
            file://5/0214-dmaengine-ti-k3-udma-glue-Update-name-for-remote-RX-.patch \
            file://5/0215-dmaengine-ti-k3-udma-glue-Add-function-to-request-TX.patch \
            file://5/0216-dmaengine-ti-k3-udma-glue-Add-function-to-request-RX.patch \
            file://5/0236-serial-8250-omap-Remove-unused-wakeups_enabled.patch \
            file://5/0237-serial-8250-omap-Set-wakeup-capable-do-not-enable.patch \
            file://5/0238-serial-8250-omap-Support-wakeup-pinctrl-state.patch \
            file://5/0242-media-ti-j721e-csi2rx-Re-use-a-32KiB-drain-buffer.patch \
            file://5/0259-drm-tidss-Add-support-for-display-sharing.patch \
            file://5/0276-drm-tidss-Add-support-for-AM62P-DSS1.patch \
            file://5/0278-arm64-dts-ti-k3-j721e-main-Add-DSI-and-DPHY-TX.patch \
            file://5/0284-Input-gpio-keys-Add-system-suspend-support-for-dedic.patch \
            file://6/0002-serial-8250_omap-Set-the-console-genpd-always-on-if-.patch \
            file://6/0004-media-ti-j721e-csi2rx-Submit-all-available-buffers.patch \
            file://6/0005-dmaengine-ti-k3-udma-Report-short-packet-errors.patch \
            file://6/0006-media-img-vxe-vxd-decoder-Suppress-Decoder-Compiler-.patch \
            file://0001_fix_nonlinux_compile.patch \
            file://0002-bootup-hacks-move-mmc-early.patch \
            file://0003-Kbuild.include.patch \
            file://0004_wait-for-rootfs.patch \
            file://0011_fix-mhdp-reg.patch \
            file://0014-WIP-tusb322.patch \
            file://0015_enable-snd-soc-hdmi-codec.patch \
            file://ti_config \
"

SRC_URI[sha256sum] = "b7fbd1d79faed2ce3570ef79dc1223e4e19c868b86326b14a435db56ebbb2022"

S = "${WORKDIR}/linux-${PV}"

do_applypath() {
    sed 's|MOBIAQUA_FW_PATH|${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/|g' ${WORKDIR}/ti_config > ${S}/arch/${ARCH}/configs/ti_defconfig
}

addtask applypath before do_patch after do_unpack
