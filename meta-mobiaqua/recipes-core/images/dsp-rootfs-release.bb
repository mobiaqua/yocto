require common-base.inc
require common-net.inc

DESCRIPTION = "<description>"

PV = "1.0.0"
PR = "r0"

INSTALL_PKGS += "x-loader-igep-mlo writeloader linux-firmware-sd8686"

RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-release"
IMAGE_INSTALL += "${INSTALL_PKGS} "

inherit mobiaqua-clean-boot

ROOTFS_POSTPROCESS_COMMAND += "mobiaqua_rootfs_clean_boot_dir ; "
