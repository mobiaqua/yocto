require media-rootfs-release.bb

require common-debug-tools.inc

INSTALL_PKGS += "libdce-dbg libmmrpc-dbg libdrm-dbg libgbm-dbg sgx-pvr-gbm-dbg sgx-pvrsrvinit-dbg"
INSTALL_PKGS += "evtest omapdrmtest v4l-utils yavta kmscube mplayer-mini"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
