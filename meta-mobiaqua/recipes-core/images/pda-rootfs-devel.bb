require pda-rootfs-release.bb

require common-debug-tools.inc

INSTALL_PKGS += "evtest tslib-tests"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
