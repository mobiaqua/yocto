require media-rootfs-release.bb

require common-debug-tools.inc

INSTALL_PKGS += "kmscube-dbg evtest v4l-utils yavta"

INSTALL_PKGS:append = " ${INSTALL_PKGS_DEBUG}"

DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
