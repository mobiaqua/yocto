require common-base.inc

DESCRIPTION = "<description>"

PV = "1.0.0"
PR = "r0"

INSTALL_PKGS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-release"
IMAGE_INSTALL += "${INSTALL_PKGS} vm-exit binfmt-support"

inherit mobiaqua-clean-boot
inherit quartus-dummy-locale
inherit vm-rosetta

ROOTFS_POSTPROCESS_COMMAND += "mobiaqua_rootfs_clean_boot_dir ; mobiaqua_dummy_locale ; mobiaqua_rootfs_vm_rosetta ; "
