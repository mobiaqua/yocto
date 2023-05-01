require media-rootfs-release.bb

require common-debug-tools.inc

INSTALL_PKGS += "libdce-dbg libmmrpc-dbg libdrm-dbg sgx-pvr-gbm-dbg sgx-pvrsrvinit-dbg"
INSTALL_PKGS += "mesa-dbg evtest omapdrmtest v4l-utils yavta"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
