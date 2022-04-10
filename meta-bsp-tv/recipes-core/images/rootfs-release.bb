
require recipes-core/images/common-base.inc

DESCRIPTION = "<description>"

PV = "1.0.0"
PR = "r0"

INSTALL_PKGS += "u-boot-pandaboard u-boot-beagleboard sgx-module-init sgx-pvrsrvinit \
                 sgx-pvr-gbm sgx-module-panda sgx-module-beagle gles-dummy ti-gc320-driver \
                 ti-gc320-libs libdce libdce-firmware wl127x-fw vpdma-fw brcmfmac-fw \
                 wireless-regdb-static libavcodec libavformat libavutil libswscale \
                 libswresample \
"

RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-release"
IMAGE_INSTALL += "${INSTALL_PKGS} "

inherit mobiaqua-clean-boot

ROOTFS_POSTPROCESS_COMMAND += "mobiaqua_rootfs_clean_boot_dir ; "
