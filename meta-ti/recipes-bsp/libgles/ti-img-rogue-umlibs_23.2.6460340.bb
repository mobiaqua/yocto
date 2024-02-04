DESCRIPTION = "Userspace libraries for PowerVR Rogue GPU on TI SoCs"
HOMEPAGE = "http://git.ti.com/graphics/ti-img-rogue-umlibs"
LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=7232b98c1c58f99e3baa03de5207e76f"

inherit bin_package

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "beagle64"

PR = "r2"

BRANCH = "linuxws/kirkstone/k6.1/${PV}"
SRC_URI = "git://git.ti.com/git/graphics/ti-img-rogue-umlibs.git;protocol=https;branch=${BRANCH}"
SRCREV = "c2671c6eaf85ec6a1183c023bbb4d6e9e288fc10"
S = "${WORKDIR}/git/targetfs/${TARGET_PRODUCT}/${PVR_WS}/${PVR_BUILD}"

TARGET_PRODUCT:beagle64 = "j721e_linux"
PVR_BUILD = "release"
PVR_WS = "lws-generic"

RDEPENDS:${PN} = " \
    libdrm \
    ${PN}-firmware \
"

do_install:beagle64 () {
    install -d ${D}${libdir}/${TARGET_PRODUCT}
    for library in GLESv1_CM_PVR_MESA GLESv2_PVR_MESA PVRScopeServices glslcompiler pvr_dri_support srv_um sutu_display ufwriter usc
    do
        cp -p ${S}/usr/lib/lib${library}.so.${PV} ${D}${libdir}/${TARGET_PRODUCT}/lib${library}.so
        ln -s lib${library}.so ${D}${libdir}/${TARGET_PRODUCT}/lib${library}.so.1
    done

    install -d ${D}${base_libdir}/firmware
    cp ${S}/lib/firmware/* ${D}${base_libdir}/firmware/

    install -d ${D}${sysconfdir}/profile.d
    echo "#!/bin/sh

export LD_LIBRARY_PATH=/usr/lib/${TARGET_PRODUCT}
" > ${D}${sysconfdir}/profile.d/pvr_gles.sh
    chmod 755 ${D}${sysconfdir}/profile.d/pvr_gles.sh
}

PACKAGES = " \
    ${PN}-firmware \
    ${PN} \
"

# required firmware
FILES:${PN}-firmware = "${base_libdir}/firmware/*"
INSANE_SKIP:${PN}-firmware += "arch"

INSANE_SKIP:${PN} += "already-stripped dev-so"
