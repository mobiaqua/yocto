require rootfs-release.bb

require recipes-core/images/common-debug-tools.inc

INSTALL_PKGS += "libdce-dbg libmmrpc-dbg libdrm-dbg libgbm-dbg sgx-pvr-gbm-dbg sgx-pvrsrvinit-dbg gles-dummy-dbg"
INSTALL_PKGS += "evtest omapdrmtest kmscube mplayer-mini"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
