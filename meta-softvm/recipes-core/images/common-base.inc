DESCRIPTION = "<description>"

LICENSE = "MIT"

PV = "1.0"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS:append=" virtual/kernel"

INSTALL_PKGS = ""
DEPEDS += ""
RRECOMMENDS += ""
RDEPENDS += ""

IMAGE_LINGUAS = " "
IMAGE_INSTALL += "base-files qemu-exit vm-exit cmdline-parse expect ${VIRTUAL-RUNTIME_init_manager} ${INSTALL_PKGS} "

inherit image
