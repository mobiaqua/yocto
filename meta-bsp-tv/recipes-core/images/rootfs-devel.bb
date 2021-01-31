require rootfs-release.bb

require recipes-core/images/common-debug-tools.inc

INSTALL_PKGS += "omapdrmtest kmscube mplayer-mini"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
