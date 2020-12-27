DESCRIPTION = "<description>"

LICENSE = "MIT"

PV = "1.0"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS_append=" virtual/kernel"

INSTALL_PKGS = "\
	kernel-modules alsa-utils alsa-state alsa-utils-alsamixer glibc-gconv-ibm850 glibc-gconv-cp1250 \
	dropbear dbus bluez5 util-linux libstdc++ iw opkg \
"

RRECOMMENDS += ""
RDEPENDS += "ntpdate tzdata tzdata-europe \
"

IMAGE_LINGUAS = " "
IMAGE_INSTALL += "packagegroup-core-boot ${INSTALL_PKGS} "

inherit image