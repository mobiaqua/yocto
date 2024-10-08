require common-base.inc
require common-net.inc

DESCRIPTION = "<description>"

PV = "1.0.0"
PR = "r0"

INSTALL_PKGS += "\
libegl-mesa libgles1-mesa libgles2-mesa \
wireless-regdb-static libavcodec libavformat \
libavutil libswscale libswresample kmscube \
mpv-mini mpv-config mpv-gui \
"

INSTALL_PKGS:append = " ${INSTALL_PKGS_RELEASE}"

RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-release"
IMAGE_INSTALL += "${INSTALL_PKGS} "

IMAGE_FSTYPES = "tar.gz ext4"

inherit mobiaqua-clean-boot

ROOTFS_POSTPROCESS_COMMAND += "mobiaqua_rootfs_clean_boot_dir ; "
