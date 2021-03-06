
require recipes-core/images/common-base.inc

DESCRIPTION = "<description>"

PV = "1.0.0"
PR = "r0"

INSTALL_PKGS += "u-boot-pandaboard u-boot-beagleboard sgx-module-init sgx-module-panda sgx-module-beagle \
                 gles-dummy libdce libdce-firmware wl127x-fw abefw \
                 libavcodec libavformat libavutil libavresample libswscale libswresample \
"

RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-release"
IMAGE_INSTALL += "${INSTALL_PKGS} "

inherit mobiaqua-clean-boot

ROOTFS_POSTPROCESS_COMMAND += "mobiaqua_rootfs_clean_boot_dir ; "
