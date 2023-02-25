require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS = "unifdef-native bison-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.${KORG_ARCHIVE_COMPRESSION}"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-ti-6.2:"

SRC_URI += "\
            file://rpmsg_rpc.h.patch \
            file://rpmsg_socket.h.patch \
            file://drm-omap-add-OMAP_BO-flags-to-affect-b.patch \
           "

SRC_URI[sha256sum] = "74862fa8ab40edae85bb3385c0b71fe103288bce518526d63197800b3cbdecb1"
