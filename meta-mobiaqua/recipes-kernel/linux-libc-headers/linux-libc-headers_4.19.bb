require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

DEPENDS = "unifdef-native bison-native"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-ti-4.19:"

SRC_URI += "file://omapdrm-rpmsg.patch"

SRC_URI[sha256sum] = "0c68f5655528aed4f99dae71a5b259edc93239fa899e2df79c055275c21749a1"

do_install() {
    oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix} ARCH=$ARCH

    # The ..install.cmd conflicts between various configure runs
    find ${D}${includedir} -name ..install.cmd | xargs rm -f
    find ${D}${includedir} -name ..install | xargs rm -f
    find ${D}${includedir} -name .install | xargs rm -f
}

DEPENDS_remove = "rsync-native"
