require common-base.inc

DESCRIPTION = "<description>"

PV = "1.0.0"
PR = "r0"

INSTALL_PKGS += "\
libegl-mesa libgles1-mesa libgles2-mesa \
wireless-regdb-static libavcodec libavformat \
libavutil libswscale libswresample mplayer-mini kmscube mpv \
"

INSTALL_PKGS:append:panda = " \
sgx-pvr-gbm sgx-module-ti443x ti-sgx-ddk-um sgx-module-init sgx-pvrsrvinit \
libdce libdce-firmware wl127x-fw u-boot-pandaboard \
"

INSTALL_PKGS:append:beagle = " \
sgx-pvr-gbm sgx-module-ti572x ti-sgx-ddk-um sgx-module-init sgx-pvrsrvinit \
ti-gc320-driver ti-gc320-libs libdce libdce-firmware vpdma-fw brcmfmac-fw \
u-boot-beagleboard"

RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-release"
IMAGE_INSTALL += "${INSTALL_PKGS} "

inherit mobiaqua-clean-boot

ROOTFS_POSTPROCESS_COMMAND += "mobiaqua_rootfs_clean_boot_dir ; "
