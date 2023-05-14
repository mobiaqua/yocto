require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
COMPATIBLE_MACHINE = "(panda|beagle)"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "5.10.108"
PV = "${LINUX_VERSION}"
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:panda = "omap4-panda.dtb omap4-panda-es.dtb"
KERNEL_DEVICETREE:beagle = "am57xx-beagle-x15-revc.dtb am5729-beagleboneai.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti_5.10:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v5.x/linux-${LINUX_VERSION}.tar.xz \
           file://drm/0001-HACK-drm-omap-increase-DSS5-max-tv-pclk-to-192MHz.patch \
           file://drm/0002-drm-omap-Enable-COLOR_ENCODING-and-COLOR_RANGE-prope.patch \
           file://drm/0003-drm-omap-Implement-CTM-property-for-CRTC-using-OVL-m.patch \
           file://drm/0004-drm-omap-add-crtc-background-property.patch \
           file://drm/0005-drm-omap-add-crtc-transparency-key-property.patch \
           file://drm/0006-drm-omap-add-alpha-blender-property.patch \
           file://drm/0007-drm-omap-Add-ability-to-check-if-requested-plane-mod.patch \
           file://drm/0008-drm-omap-Add-ovl-checking-funcs-to-dispc_ops.patch \
           file://drm/0009-drm-omap-introduce-omap_hw_overlay.patch \
           file://drm/0010-drm-omap-omap_plane-subclass-drm_plane_state.patch \
           file://drm/0011-drm-omap-Add-global-state-as-a-private-atomic-object.patch \
           file://drm/0012-drm-omap-dynamically-assign-hw-overlays-to-planes.patch \
           file://drm/0013-drm-omap-add-plane_atomic_print_state-support.patch \
           file://drm/0014-drm-omap-Add-a-right-overlay-to-plane-state.patch \
           file://drm/0015-drm-omap-add-omap_plane_reserve-release_wb.patch \
           file://drm/0016-drm-omap-add-WB-support.patch \
           file://dts/0001-ARM-dts-am57xx-evm-Enable-BB2D-node.patch \
           file://dts/0002-ARM-dts-dra7-Add-ti-sysc-node-for-VIP1.patch \
           file://dts/0003-ARM-dts-ti-Fix-node-name-for-all-ecap-dt-nodes.patch \
           file://dts/0004-ARM-dts-dra7-add-second-SHA-instance.patch \
           file://dts/0005-ARM-dts-dra7-Add-device-tree-entry-for-SGX.patch \
           file://dts/0006-ARM-dts-dra7-add-entry-for-bb2d-module.patch \
           file://dts/0007-ARM-dts-DRA74x-Add-VIP2-and-VIP3-dtsi-entries.patch \
           file://dts/0008-ARM-dts-pandaboard-es-add-bluetooth-uart-for-HCI.patch \
           file://net-rpmsg/0001-net-rpmsg-add-support-for-new-rpmsg-sockets.patch \
           file://net-rpmsg/0002-net-rpmsg-add-support-to-handle-a-remote-processor-e.patch \
           file://net-rpmsg/0003-net-rpmsg-return-ESHUTDOWN-upon-Tx-on-errored-socket.patch \
           file://net-rpmsg/0004-net-rpmsg-return-ENOLINK-upon-Rx-on-errored-sockets.patch \
           file://net-rpmsg/0005-net-rpmsg-unblock-reader-threads-operating-on-errore.patch \
           file://remoteproc/0001-remoteproc-add-api-for-retrieving-a-rproc-unique-id.patch \
           file://remoteproc/0002-remoteproc-add-an-api-to-do-pa-to-da-conversion.patch \
           file://remoteproc/0003-remoteproc-core-Remove-casting-to-rproc_handle_resou.patch \
           file://remoteproc/0004-remoteproc-move-rproc_da_to_va-declaration-to-remote.patch \
           file://remoteproc/0005-remoteproc-omap-add-a-trace-to-print-missing-alias-i.patch \
           file://remoteproc/0006-remoteproc-debugfs-Optimize-the-trace-va-lookup.patch \
           file://remoteproc/0007-remoteproc-implement-last-trace-for-remoteproc.patch \
           file://remoteproc/0008-remoteproc-Fix-multiple-back-to-back-error-recoverie.patch \
           file://remoteproc/0009-remoteproc-omap-Trigger-IOMMU-during-crash-recovery-.patch \
           file://rpmsg-rpc/0050-TEMP-rpmsg-add-a-description-field.patch \
           file://rpmsg-rpc/0051-rpmsg-virtio_rpmsg_bus-move-back-rpmsg_hdr-into-a-pu.patch \
           file://rpmsg-rpc/0052-rpmsg-rpc-introduce-a-new-rpmsg_rpc-driver.patch \
           file://rpmsg-rpc/0053-rpmsg-rpc-fix-sysfs-entry-creation-failures-during-r.patch \
           file://rpmsg-rpc/0054-rpmsg-rpc-maintain-a-reference-device-pointer-per-op.patch \
           file://rpmsg-rpc/0055-rpmsg-rpc-use-the-local-device-pointer-in-all-file-o.patch \
           file://rpmsg-rpc/0056-rpmsg-rpc-fix-ept-memory-leak-during-recovery.patch \
           file://rpmsg-rpc/0057-rpmsg-rpc-fix-potential-memory-leak-of-unprocessed-s.patch \
           file://rpmsg-rpc/0058-rpmsg-rpc-fix-static-checker-errors.patch \
           file://rpmsg-rpc/0059-rpmsg-core-add-API-to-get-MTU.patch \
           file://rpmsg-rpc/0060-rpmsg-fix-lockdep-warnings-in-virtio-rpmsg-bus-drive.patch \
           file://ti/0010-clocksource-drivers-timer-ti-dm-fix-regression-from-.patch \
           file://ti/0011-clocksource-drivers-timer-ti-dm-ack-pending-interrup.patch \
           file://ti/0012-crypto-omap-increase-priority-of-DES-3DES.patch \
           file://ti/0013-drm-atomic-integrate-private-objects-with-suspend-re.patch \
           file://ti/0014-iommu-omap-Add-transition-support-between-hwmod-and-.patch \
           file://ti/0015-iommu-omap-convert-spinlocks-to-mutexes.patch \
           file://ti/0016-HACK-regulator-tps65917-palmas-Disable-bypass-for-ld.patch \
           file://ti/0017-Revert-Revert-drm-omap-add-OMAP_BO-flags-to-affect-b.patch \
           file://eth-dra7/0001-HACK-drivers-net-cpsw-add-support-for-switch-ioctl.patch \
           file://eth-dra7/0001-HACK-net-ethernet-ti-cpsw-allow-to-configure-min-tx-.patch \
           file://eth-dra7/0001-HACK-net-ethtool-export-convert-legacy_settings_to_l.patch \
           file://eth-dra7/0001-HACK-net-ioctl-Adding-cpsw-switch-configuration-via-.patch \
           file://eth-dra7/0001-drivers-net-cpsw-ale-add-broadcast-multicast-rate-li.patch \
           file://eth-dra7/0001-drivers-net-davinci_mdio-Use-of_device_get_match_dat.patch \
           file://eth-dra7/0001-net-ethernet-ti-convert-comma-to-semicolon.patch \
           file://eth-dra7/0001-net-ethernet-ti-cpsw_ale-add-cpsw_ale_vlan_del_modif.patch \
           file://eth-dra7/0001-net-ethernet-ti-fix-netdevice-stats-for-XDP.patch \
           file://wlan/0001-wlan1.patch \
           file://wlan/0002-wlan2.patch \
           file://0001_fix_nonlinux_compile.patch \
           file://0002-bootup-hacks-move-mmc-early.patch \
           file://0003-Kbuild.include.patch \
           file://0004_wait-for-rootfs.patch \
           file://0005_smsc95xx-add-macaddr-module-parameter.patch \
           file://0007_omap4-sgx.patch \
           file://0008_fixed_name_hdmi_audio.patch \
           file://0009_panda-bt-fixes.patch \
           file://0010-omap4-clk32.patch \
           file://0011-iva-timer3-always-on.patch \
           file://0012-omap4-smartreflex.patch \
           file://0020-restore-dmabuf-map.patch \
           file://0030-HACK-drm-omap-flush-the-mapped-page-in-kmap-kunmap.patch \
           file://0040-remove-spectre-trace.patch \
           file://0050-add-export-vmalloc_node_range-for-sgx.patch \
           file://0200-beagle-ai.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "bf6cc2d6e0918b8f34d1cde2fa39a6ad69c45025425048be1a1dac4a5b3641d8"

S = "${WORKDIR}/linux-${PV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"
