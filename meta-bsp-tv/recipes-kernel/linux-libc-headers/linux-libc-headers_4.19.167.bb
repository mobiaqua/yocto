require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

DEPENDS = "unifdef-native bison-native"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-omap4.19:"

SRC_URI += "\
        file://omapdrm-rpmsg.patch \
"

SRC_URI[sha256sum] = "312c67677c75adc4324078c5afb11411df379d5573ad0a429b3f4919f26bed01"

do_install() {
    oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix} ARCH=$ARCH

    # The ..install.cmd conflicts between various configure runs
    find ${D}${includedir} -name ..install.cmd | xargs rm -f
    find ${D}${includedir} -name ..install | xargs rm -f
    find ${D}${includedir} -name .install | xargs rm -f
}

DEPENDS_remove = "rsync-native"
