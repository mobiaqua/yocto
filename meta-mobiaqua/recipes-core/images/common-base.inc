DESCRIPTION = "<description>"

LICENSE = "MIT"

PV = "1.0"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS:append=" virtual/kernel"

INSTALL_PKGS = "\
	kernel-modules alsa-utils alsa-state alsa-utils-alsamixer glibc-gconv-ibm850 glibc-gconv-cp1250 \
	dropbear dbus util-linux libstdc++ opkg initscripts \
"
DEPEDS += "virtual/gles2"
RRECOMMENDS += ""
RDEPENDS += "chrony tzdata tzdata-europe \
"

IMAGE_LINGUAS = " "
IMAGE_INSTALL += "packagegroup-core-boot ${INSTALL_PKGS} "

inherit image
