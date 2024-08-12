SUMMARY = "A suite of basic system administration utilities"
HOMEPAGE = "https://en.wikipedia.org/wiki/Util-linux"
DESCRIPTION = "Util-linux includes a suite of basic system administration utilities \
commonly found on most Linux systems.  Some of the more important utilities include \
disk partitioning, kernel message management, filesystem creation, and system login."

SECTION = "base"

LICENSE = "GPL-1.0-or-later & GPL-2.0-or-later & LGPL-2.1-or-later & BSD-2-Clause & BSD-3-Clause & BSD-4-Clause & MIT"
LICENSE:${PN}-fcntl-lock = "MIT"
LICENSE:${PN}-fdisk = "GPL-1.0-or-later"
LICENSE:${PN}-libblkid = "LGPL-2.1-or-later"
LICENSE:${PN}-libfdisk = "LGPL-2.1-or-later"
LICENSE:${PN}-libmount = "LGPL-2.1-or-later"
LICENSE:${PN}-libsmartcols = "LGPL-2.1-or-later"

LIC_FILES_CHKSUM = "file://README.licensing;md5=ddd58b6c94da86ff4f03e91208eb9cfc \
                    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://Documentation/licenses/COPYING.GPL-2.0-or-later;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://Documentation/licenses/COPYING.LGPL-2.1-or-later;md5=4fbd65380cdd255951079008b364516c \
                    file://Documentation/licenses/COPYING.BSD-3-Clause;md5=58dcd8452651fc8b07d1f65ce07ca8af \
                    file://Documentation/licenses/COPYING.BSD-4-Clause-UC;md5=263860f8968d8bafa5392cab74285262 \
                    file://libuuid/COPYING;md5=6d2cafc999feb2c2de84d4d24b23290c \
                    file://libmount/COPYING;md5=7c7e39fb7d70ffe5d693a643e29987c2 \
                    file://libblkid/COPYING;md5=693bcbbe16d3a4a4b37bc906bc01cc04 \
                    file://libfdisk/COPYING;md5=693bcbbe16d3a4a4b37bc906bc01cc04 \
                    file://libsmartcols/COPYING;md5=693bcbbe16d3a4a4b37bc906bc01cc04 \
                    "

FILESEXTRAPATHS:prepend := "${THISDIR}/util-linux:"
MAJOR_VERSION = "${@'.'.join(d.getVar('PV').split('.')[0:2])}"
SRC_URI = "${KERNELORG_MIRROR}/linux/utils/util-linux/v${MAJOR_VERSION}/util-linux-${PV}.tar.xz \
           file://configure-sbindir.patch \
           file://macos-fix.patch \
           "

SRC_URI[sha256sum] = "d78b37a66f5922d70edf3bdfb01a6b33d34ed3c3cafd6628203b2a2b67c8e8b3"

inherit autotools gettext pkgconfig gtk-doc native

DEPENDS = "ncurses readline"

EXTRA_OECONF = "\
    --disable-agetty \
    --disable-cramfs \
    --disable-eject \
    --disable-fallocate \
    --disable-fsck \
    --disable-kill \
    --disable-libblkid \
    --disable-liblastlog2 \
    --disable-libmount \
    --disable-libuuid \
    --disable-login \
    --disable-nologin \
    --disable-losetup \
    --disable-mount \
    --disable-mountpoint \
    --disable-partx \
    --disable-pg \
    --disable-pg-bell \
    --disable-pivot_root \
    --disable-schedutils \
    --disable-silent-rules \
    --disable-su \
    --disable-runuser \
    --disable-sulogin \
    --disable-switch_root \
    --disable-unshare \
    --disable-wall \
    --enable-shared=no \
    --without-audit \
    --without-python \
    --disable-bfs \
    --disable-makeinstall-chown \
    --disable-minix \
    --disable-newgrp \
    --disable-use-tty-group \
    --disable-vipw \
    --disable-raw \
    --disable-hwclock-gplv3 \
    --without-cap-ng \
    --disable-setpriv \
    --without-udev \
    --without-cryptsetup \
    --disable-chfn-chsh \
    --without-selinux \
    --enable-libuuid \
    usrsbin_execdir='${sbindir}' \
    --libdir='${libdir}' \
"

EXTRA_OEMAKE = "ARCH=${TARGET_ARCH} CPU= CPUOPT= 'OPT=${CFLAGS}'"

ALLOW_EMPTY:${PN} = "1"
FILES:${PN} = ""

# dm-verity support introduces a circular build dependency, so util-linux-libuuid is split out for target builds
# Need to build libuuid for uuidgen, but then delete it and let the other recipe ship it
do_install:append () {
        rm -rf ${D}${includedir}/uuid ${D}${libdir}/pkgconfig/uuid.pc ${D}${libdir}/libuuid* ${D}${base_libdir}/libuuid*
}
