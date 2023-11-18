require linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS = "unifdef-native bison-native elf-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.${KORG_ARCHIVE_COMPRESSION}"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-6.6:"

SRC_URI += "\
            file://non_linux.patch \
           "

SRC_URI[sha256sum] = "d926a06c63dd8ac7df3f86ee1ffc2ce2a3b81a2d168484e76b5b389aba8e56d0"
