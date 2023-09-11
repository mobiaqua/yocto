require linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS = "unifdef-native bison-native elf-native"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.${KORG_ARCHIVE_COMPRESSION}"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-6.1:"

SRC_URI += "\
            file://non_linux.patch \
           "

SRC_URI[sha256sum] = "2ca1f17051a430f6fed1196e4952717507171acfd97d96577212502703b25deb"
