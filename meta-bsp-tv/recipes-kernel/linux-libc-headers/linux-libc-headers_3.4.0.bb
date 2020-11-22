require linux-libc-headers.inc

DEPENDS = "unifdef-native bison-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-omap4:"

SRCREV = "ti-ubuntu-3.4.0-1491.3"

SRC_URI = "git://dev.omapzoom.org/pub/scm/integration/kernel-ubuntu.git;protocol=git;branch=ti-ubuntu-3.4-stable \
           file://patch-3.4.103.patch \
           file://patch-3.4.103-104.patch \
           file://patch-3.4.104-105.patch \
           file://patch-3.4.105-106.patch \
           file://patch-3.4.106-107.patch \
           file://patch-3.4.107-108.patch \
           file://patch-3.4.108-109.patch \
           file://patch-3.4.109-110.patch \
           file://patch-3.4.110-111.patch \
           file://patch-3.4.111-112.patch \
           file://patch-3.4.112-113.patch \
"

S = "${WORKDIR}/git"

do_configure() {
    cd ${S}
    oe_runmake allnoconfig ARCH=$ARCH
}

do_install() {
    oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix} ARCH=$ARCH

    # The ..install.cmd conflicts between various configure runs
    find ${D}${includedir} -name ..install.cmd | xargs rm -f
    find ${D}${includedir} -name ..install | xargs rm -f
    find ${D}${includedir} -name .install | xargs rm -f

    install -d ${D}/${includedir}/omap
    cp -L ${S}/drivers/staging/omapdrm/omap_drv.h ${D}/${includedir}/omap/
    cp -L ${S}/drivers/staging/omapdce/omap_dce.h ${D}/${includedir}/omap/
    cp -L ${S}/drivers/staging/omapdce/dce_rpc.h ${D}/${includedir}/omap/
}

do_install_armmultilib () {
    oe_multilib_header asm/auxvec.h asm/bitsperlong.h asm/byteorder.h asm/fcntl.h asm/hwcap.h asm/ioctls.h asm/mman.h asm/param.h
    oe_multilib_header asm/posix_types.h asm/ptrace.h asm/setup.h asm/sigcontext.h asm/siginfo.h asm/signal.h asm/stat.h asm/statfs.h asm/swab.h asm/types.h asm/unistd.h
}

DEPENDS_remove = "rsync-native"
