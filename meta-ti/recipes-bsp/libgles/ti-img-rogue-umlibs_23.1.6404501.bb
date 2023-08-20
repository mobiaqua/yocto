DESCRIPTION = "Userspace libraries for PowerVR Rogue GPU on TI SoCs"
HOMEPAGE = "http://git.ti.com/graphics/ti-img-rogue-umlibs"
LICENSE = "TI-TFL"
ERROR_QA:remove = "license-checksum"

inherit bin_package

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "beagle64"

PR = "r1"

BRANCH = "linuxws/kirkstone/k6.1/${PV}"
SRC_URI = "git://git.ti.com/git/graphics/ti-img-rogue-umlibs.git;protocol=https;branch=${BRANCH}"
SRCREV = "49958a8e820a321d22ac3c635bd21d4a3118c006"
S = "${WORKDIR}/git/targetfs/j721e_linux/lws-generic/release"

RDEPENDS:${PN} += "libdrm"

do_install:beagle64 () {
    install -d ${D}${libdir}/gles-j721e
    for library in GLESv1_CM_PVR_MESA GLESv2_PVR_MESA PVRScopeServices glslcompiler pvr_dri_support srv_um sutu_display ufwriter usc
    do
        cp -p ${S}/usr/lib/lib${library}.so.${PV} ${D}${libdir}/gles-j721e/lib${library}.so
        ln -s lib${library}.so ${D}${libdir}/gles-j721e/lib${library}.so.1
    done

    install -d ${D}${base_libdir}/firmware
    cp ${S}/lib/firmware/* ${D}${base_libdir}/firmware/

    install -d ${D}${sysconfdir}/profile.d
    echo "#!/bin/sh

export LD_LIBRARY_PATH=/usr/lib/gles-j721e
" > ${D}${sysconfdir}/profile.d/pvr_gles.sh
    chmod 755 ${D}${sysconfdir}/profile.d/pvr_gles.sh
}

PACKAGES = " \
    ${PN}-firmware \
    ${PN} \
"

FILES:${PN}-firmware = "${base_libdir}/firmware/*"
INSANE_SKIP:${PN}-firmware += "arch"

INSANE_SKIP:${PN} += "already-stripped dev-so"
