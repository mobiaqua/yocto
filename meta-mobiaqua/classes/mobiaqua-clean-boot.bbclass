mobiaqua_rootfs_clean_boot_dir() {
	rm -f ${IMAGE_ROOTFS}/boot/*
}

mobiaqua_rootfs_clean_fw_dir() {
	rm -rf ${IMAGE_ROOTFS}/lib/firmware/*
}
