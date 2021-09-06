require rootfs-release.bb

require recipes-core/images/common-debug-tools.inc

INSTALL_PKGS += "libdce-dbg libdrm-dbg libgbm-dbg"
# sgx-libs-dbg sgx-pvrsrvinit-dbg
INSTALL_PKGS += "evtest omapdrmtest kmscube mplayer-mini"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
