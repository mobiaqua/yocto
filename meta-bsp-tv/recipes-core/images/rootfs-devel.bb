require rootfs-release.bb

require recipes-core/images/common-debug-tools.inc

INSTALL_PKGS += "sgx-libs-dbg sgx-pvrsrvinit-dbg libdce-dbg libdrm-dbg libgbm-dbg"
# libavcodec-dbg libavformat-dbg libavutil-dbg libavresample-dbg libswscale-dbg libswresample-dbg
INSTALL_PKGS += "omapdrmtest kmscube mplayer-mini"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
