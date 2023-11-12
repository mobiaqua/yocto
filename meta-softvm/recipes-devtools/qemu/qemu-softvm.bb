SUMMARY = "Fast open source processor emulator"
DESCRIPTION = "QEMU is a hosted virtual machine monitor: it emulates the \
machine's processor through dynamic binary translation and provides a set \
of different hardware and device models for the machine, enabling it to run \
a variety of guest operating systems"
HOMEPAGE = "http://qemu.org"
LICENSE = "GPL-2.0-only & LGPL-2.1-only"

inherit pkgconfig native

LIC_FILES_CHKSUM = "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
                    file://COPYING.LIB;endline=24;md5=8c5efda6cf1e1b03dcfd0e6c0d271c7f"

PV = "8.0.5"

SRC_URI = "https://download.qemu.org/qemu-${PV}.tar.xz \
           file://silence-libusb-error.patch \
           file://semihost_cmd_qemu.sh \
           "
UPSTREAM_CHECK_REGEX = "qemu-(?P<pver>\d+(\.\d+)+)\.tar"

SRC_URI[sha256sum] = "91d3024d51e441c235dcb1b0c87cb3aab302283166e8d3d5f8282aa06c346be1"

DEPENDS = "glib-2.0-native zlib-native meson-native ninja-native pixman-native libslirp-native usbredir-native"

# Per https://lists.nongnu.org/archive/html/qemu-devel/2020-09/msg03873.html
# upstream states qemu doesn't work without optimization
DEBUG_BUILD = "0"

do_install:append() {
    # Prevent QA warnings about installed ${localstatedir}/run
    if [ -d ${D}${localstatedir}/run ]; then rmdir ${D}${localstatedir}/run; fi

    install -m 0755 ${WORKDIR}/semihost_cmd_qemu.sh ${D}/${bindir}
}

EXTRA_OECONF = " \
    --prefix=${prefix} \
    --bindir=${bindir} \
    --includedir=${includedir} \
    --libdir=${libdir} \
    --mandir=${mandir} \
    --datadir=${datadir} \
    --docdir=${docdir}/${BPN} \
    --sysconfdir=${sysconfdir} \
    --libexecdir=${libexecdir} \
    --localstatedir=${localstatedir} \
    --with-suffix=${BPN} \
    --disable-strip \
    --disable-werror \
    --extra-cflags='${CFLAGS}' \
    --extra-ldflags='${LDFLAGS}' \
    --with-git=/bin/false \
    --with-git-submodules=ignore \
    --meson=meson \
    --target-list=x86_64-softmmu \
    --disable-sdl \
    --disable-png \
    --disable-virtfs \
    --disable-linux-aio \
    --disable-xen \
    --disable-vnc \
    --disable-vnc-sasl \
    --disable-vnc-jpeg \
    --disable-curl \
    --disable-smartcard \
    --disable-curses \
    --disable-gtk \
    --disable-vte \
    --disable-cap-ng \
    --disable-libssh \
    --disable-gcrypt \
    --disable-nettle \
    --disable-opengl \
    --disable-lzo \
    --disable-zstd \
    --disable-gnutls \
    --disable-numa \
    --disable-bzip2 \
    --disable-libiscsi \
    --disable-virglrenderer \
    --disable-spice \
    --disable-spice-protocol \
    --disable-dbus-display \
    --disable-snappy \
    --disable-glusterfs \
    --disable-xkbcommon \
    --disable-libudev \
    --disable-attr \
    --disable-rbd \
    --disable-seccomp \
    --disable-libnfs \
    --disable-libpmem \
    --disable-pa \
    --disable-selinux \
    --disable-bpf \
    --disable-capstone \
    --disable-rdma \
    --disable-vde \
    --disable-brlapi \
    --disable-jack \
    --disable-libdw \
    --disable-fuse \
    --disable-pie \
    --disable-auth-pam \
    --disable-blkio \
    --disable-bochs \
    --disable-canokey \
    --disable-cloop \
    --disable-cocoa \
    --disable-coreaudio \
    --disable-crypto-afalg \
    --disable-dmg \
    --disable-docs \
    --disable-dsound \
    --disable-gettext \
    --disable-gio \
    --disable-guest-agent \
    --disable-hax \
    --disable-iconv \
    --disable-keyring \
    --disable-kvm \
    --disable-l2tpv3 \
    --disable-libdaxctl \
    --disable-libvduse \
    --disable-linux-aio \
    --disable-linux-io-uring \
    --disable-live-block-migration \
    --disable-lzfse \
    --disable-malloc-trim \
    --disable-membarrier \
    --disable-mpath \
    --disable-multiprocess \
    --disable-netmap \
    --disable-nvmm \
    --disable-oss \
    --disable-parallels \
    --disable-qcow1 \
    --disable-qed \
    --disable-qga-vss \
    --disable-replication \
    --disable-sdl \
    --disable-sdl-image \
    --disable-seccomp \
    --disable-selinux \
    --disable-slirp-smbd \
    --disable-sndio \
    --disable-sparse \
    --disable-tpm \
    --disable-u2f \
    --disable-vdi \
    --disable-vduse-blk-export \
    --disable-vfio-user-server \
    --disable-vhost-crypto \
    --disable-vhost-user-blk-server \
    --disable-vhost-vdpa \
    --disable-vhost-kernel \
    --disable-vhost-net \
    --disable-vhost-user \
    --disable-vnc \
    --disable-vvfat \
    --disable-whpx \
    --disable-xen \
    --disable-user \
    --disable-modules \
    --disable-tools \
    --enable-libusb \
    --enable-usb-redir \
    --enable-slirp \
    "

EXTRA_OECONF:append:darwin = "--enable-hvf --enable-cocoa"

S = "${WORKDIR}/qemu-${PV}"

B = "${WORKDIR}/build"

EXTRA_OEMAKE:append = " LD='${LD}' AR='${AR}' OBJCOPY='${OBJCOPY}' LDFLAGS='${LDFLAGS}'"

do_configure() {
    ${S}/configure ${EXTRA_OECONF}
}
do_configure[cleandirs] += "${B}"

do_install () {
    export STRIP=""
    oe_runmake 'DESTDIR=${D}' install
}
