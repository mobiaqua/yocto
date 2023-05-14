require media-rootfs-release.bb

require common-debug-tools.inc

INSTALL_PKGS += "mesa-dbg kmscube-dbg evtest v4l-utils yavta"

INSTALL_PKGS:append:panda = " \
libdce-dbg libmmrpc-dbg sgx-pvr-gbm-dbg sgx-pvrsrvinit-dbg \
omapdrmtest omapdrmtest-dbg \
"

INSTALL_PKGS:append:beagle = " \
libdce-dbg libmmrpc-dbg sgx-pvr-gbm-dbg sgx-pvrsrvinit-dbg \
omapdrmtest omapdrmtest-dbg \
"

DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
