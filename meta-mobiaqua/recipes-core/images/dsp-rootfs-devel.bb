require dsp-rootfs-release.bb

require common-debug-tools.inc

INSTALL_PKGS += "evtest"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel-car"
