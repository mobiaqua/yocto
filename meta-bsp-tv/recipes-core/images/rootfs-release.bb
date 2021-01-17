
require recipes-core/images/common-base.inc

DESCRIPTION = "<description>"

PV = "1.0.0"
PR = "r0"

INSTALL_PKGS += "sgx-libs sgx-module sgx-pvrsrvinit \
                 libdce libdce-firmware libavcodec libavformat libavutil libavresample \
                 libswscale libswresample wl127x-fw abefw scummvm \
"

RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-release"
IMAGE_INSTALL += "${INSTALL_PKGS} "

inherit mobiaqua-clean-boot

ROOTFS_POSTPROCESS_COMMAND += "mobiaqua_rootfs_clean_boot_dir ; "
