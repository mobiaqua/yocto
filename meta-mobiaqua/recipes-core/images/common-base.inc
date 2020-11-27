DESCRIPTION = "<description>"

LICENSE = "MIT"

PV = "1.0"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS_append=" virtual/kernel"

INSTALL_PKGS = "\
	${VIRTUAL-RUNTIME_initscripts} ${VIRTUAL-RUNTIME_init_manager} ${VIRTUAL-RUNTIME_login_manager} \
	base-files base-passwd busybox kernel-modules netbase alsa-utils alsa-state \
	alsa-utils-alsamixer glibc-gconv-ibm850 glibc-gconv-cp1250 ifupdown \
	dropbear dbus bluez5 util-linux libstdc++ libgcc iw \
"

RRECOMMENDS += ""
RDEPENDS += "ntpdate tzdata tzdata-europe"

IMAGE_LINGUAS = " "
IMAGE_INSTALL += "${INSTALL_PKGS} "

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

inherit image
