require media-rootfs-release.bb

require common-debug-tools.inc

INSTALL_PKGS += "kmscube-dbg evtest v4l-utils yavta"

INSTALL_PKGS:append:panda = " \
mesa-pvr-dbg libdce-dbg libmmrpc-dbg sgx-pvr-gbm-dbg sgx-pvrsrvinit-dbg \
omapdrmtest omapdrmtest-dbg \
"

INSTALL_PKGS:append:beagle = " \
mesa-pvr-dbg libdce-dbg libmmrpc-dbg sgx-pvr-gbm-dbg sgx-pvrsrvinit-dbg \
omapdrmtest omapdrmtest-dbg \
"

INSTALL_PKGS:append:beagle64 = " \
mesa-pvr-dbg \
"

INSTALL_PKGS:append:nuc = " \
libva-intel-utils \
"

DEPENDS += ""
RDEPENDS += ""
RRECOMMENDS += ""

IMAGE_BASENAME = "rootfs-devel"
