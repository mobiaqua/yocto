require rootfs-release.bb

require recipes-core/images/common-debug-tools.inc

INSTALL_PKGS += "evtest"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel-car"
