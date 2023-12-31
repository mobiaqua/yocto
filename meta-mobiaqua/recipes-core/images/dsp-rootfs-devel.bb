require dsp-rootfs-release.bb

require common-debug-tools.inc

INSTALL_PKGS += "evtest mtd-utils"
DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
