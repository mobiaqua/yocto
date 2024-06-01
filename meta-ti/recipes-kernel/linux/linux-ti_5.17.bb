require recipes-kernel/linux/linux-yocto.inc

INC_PR = "r0"
DEPENDS += "openssl-native ncurses-native elf-native kmod-native"
DEPENDS:remove = "util-linux-native elfutils-native"
COMPATIBLE_MACHINE = "(panda|beagle)"
KERNEL_VERSION_SANITY_SKIP = "1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "5.17.15"
PV = "${LINUX_VERSION}"
KERNEL_ARTIFACT_NAME = "${PKGE}${PKGV}${IMAGE_VERSION_SUFFIX}"
KERNEL_ARTIFACT_LINK_NAME = ""
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"
KERNEL_DEVICETREE:panda = "omap4-panda.dtb omap4-panda-es.dtb"
KERNEL_DEVICETREE:beagle = "am57xx-beagle-x15-revc.dtb am5729-beagleboneai.dtb"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti_5.17:"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v5.x/linux-${LINUX_VERSION}.tar.xz \
           file://drm/0004-drm-omap-add-crtc-background-property.patch \
           file://drm/0005-drm-omap-add-crtc-transparency-key-property.patch \
           file://drm/0006-drm-omap-add-alpha-blender-property.patch \
           file://drm/0007-drm-omap-wb.patch \
           file://dts/0001-ARM-dts-am57xx-evm-Enable-BB2D-node.patch \
           file://dts/0005-ARM-dts-dra7-Add-device-tree-entry-for-SGX.patch \
           file://eth-dra7/0001-HACK-drivers-net-cpsw-add-support-for-switch-ioctl.patch \
           file://eth-dra7/0001-HACK-net-ethernet-ti-cpsw-allow-to-configure-min-tx-.patch \
           file://eth-dra7/0001-HACK-net-ethtool-export-convert-legacy_settings_to_l.patch \
           file://eth-dra7/0001-HACK-net-ioctl-Adding-cpsw-switch-configuration-via-.patch \
           file://eth-dra7/0001-drivers-net-cpsw-ale-add-broadcast-multicast-rate-li.patch \
           file://remoteproc/0001-remoteproc-add-api-for-retrieving-a-rproc-unique-id.patch \
           file://remoteproc/0002-remoteproc-add-an-api-to-do-pa-to-da-conversion.patch \
           file://remoteproc/0004-remoteproc-move-rproc_da_to_va-declaration-to-remote.patch \
           file://remoteproc/0005-remoteproc-omap-add-a-trace-to-print-missing-alias-i.patch \
           file://remoteproc/0006-remoteproc-debugfs-Optimize-the-trace-va-lookup.patch \
           file://remoteproc/0007-remoteproc-implement-last-trace-for-remoteproc.patch \
           file://remoteproc/0008-remoteproc-Fix-multiple-back-to-back-error-recoverie.patch \
           file://remoteproc/0009-remoteproc-omap-Trigger-IOMMU-during-crash-recovery-.patch \
           file://rpmsg/0001-net-rpmsg-add-support-for-new-rpmsg-sockets.patch \
           file://rpmsg/0002-net-rpmsg-add-support-to-handle-a-remote-processor-e.patch \
           file://rpmsg/0003-net-rpmsg-return-ESHUTDOWN-upon-Tx-on-errored-socket.patch \
           file://rpmsg/0004-net-rpmsg-return-ENOLINK-upon-Rx-on-errored-sockets.patch \
           file://rpmsg/0005-net-rpmsg-unblock-reader-threads-operating-on-errore.patch \
           file://rpmsg-rpc/0050-TEMP-rpmsg-add-a-description-field.patch \
           file://rpmsg-rpc/0051-rpmsg-virtio_rpmsg_bus-move-back-rpmsg_hdr-into-a-pu.patch \
           file://rpmsg-rpc/0052-rpmsg-rpc-introduce-a-new-rpmsg_rpc-driver.patch \
           file://rpmsg-rpc/0053-rpmsg-rpc-fix-sysfs-entry-creation-failures-during-r.patch \
           file://rpmsg-rpc/0054-rpmsg-rpc-maintain-a-reference-device-pointer-per-op.patch \
           file://rpmsg-rpc/0055-rpmsg-rpc-use-the-local-device-pointer-in-all-file-o.patch \
           file://rpmsg-rpc/0056-rpmsg-rpc-fix-ept-memory-leak-during-recovery.patch \
           file://rpmsg-rpc/0057-rpmsg-rpc-fix-potential-memory-leak-of-unprocessed-s.patch \
           file://rpmsg-rpc/0058-rpmsg-rpc-fix-static-checker-errors.patch \
           file://rpmsg-rpc/0060-rpmsg-fix-lockdep-warnings-in-virtio-rpmsg-bus-drive.patch \
           file://ti/0011-clocksource-drivers-timer-ti-dm-ack-pending-interrup.patch \
           file://ti/0013-drm-atomic-integrate-private-objects-with-suspend-re.patch \
           file://ti/0014-iommu-omap-Add-transition-support-between-hwmod-and-.patch \
           file://ti/0016-HACK-regulator-tps65917-palmas-Disable-bypass-for-ld.patch \
           file://ti/0017-Revert-Revert-drm-omap-add-OMAP_BO-flags-to-affect-b.patch \
           file://wlan/0001-wlan.patch \
           file://upstream/0001-drm-omapdrm-Fix-implicit-dma_buf-fencing.patch \
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
           file://0013-fix-dra7-l3-noc.patch \
           file://0020-restore-dmabuf-map.patch \
           file://0030-HACK-drm-omap-flush-the-mapped-page-in-kmap-kunmap.patch \
           file://0040-remove-spectre-trace.patch \
           file://0200-beagle-ai.patch \
           file://defconfig \
           "

SRC_URI[sha256sum] = "4a1c922a490eeabf5b44d4fde36de9ba5b71711b7352c6258716da41160db628"

S = "${WORKDIR}/linux-${PV}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_LOADADDRESS}"
