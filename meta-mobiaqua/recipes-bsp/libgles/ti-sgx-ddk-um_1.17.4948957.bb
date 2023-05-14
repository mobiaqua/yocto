DESCRIPTION = "Userspace libraries for PowerVR SGX chipset on TI SoCs"
HOMEPAGE = "https://git.ti.com/graphics/omap5-sgx-ddk-um-linux"
LICENSE = "TI-TSPA"
LIC_FILES_CHKSUM = "file://TI-Linux-Graphics-DDK-UM-Manifest.doc;md5=b17390502bc89535c86cfbbae961a2a8"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "panda|beagle|igep0030"

PR = "r1"

BRANCH = "ti-img-sgx/dunfell/${PV}"

SRC_URI = "git://git.ti.com/git/graphics/omap5-sgx-ddk-um-linux.git;protocol=https;branch=${BRANCH}"

SRCREV = "742cf38aba13e1ba1a910cf1f036a1a212c263b6"

RDEPENDS:${PN} += "libdrm libdrm-omap"

S = "${WORKDIR}/git"

do_install:panda () {
    install -d ${D}${libdir}/gles-ti443x
    for library in GLESv1_PVR_MESA GLESv2_PVR_MESA PVRScopeServices dbm glslcompiler pvr_dri_support srv_init srv_um usc
    do
        cp -p ${S}/targetfs/ti443x/lib/lib${library}.so.${PV} ${D}${libdir}/gles-ti443x/lib${library}.so
        ln -s lib${library}.so ${D}${libdir}/gles-ti443x/lib${library}.so.1
    done

    install -d ${D}${sysconfdir}/profile.d
    echo "#!/bin/sh

export LD_LIBRARY_PATH=/usr/lib/gles-ti443x
" > ${D}${sysconfdir}/profile.d/sgx_gles.sh
    chmod 755 ${D}${sysconfdir}/profile.d/sgx_gles.sh
}

do_install:beagle () {
    install -d ${D}${libdir}/gles-ti572x
    for library in GLESv1_PVR_MESA GLESv2_PVR_MESA PVRScopeServices dbm glslcompiler pvr_dri_support srv_init srv_um usc
    do
        cp -p ${S}/targetfs/jacinto6evm/lib/lib${library}.so.${PV} ${D}${libdir}/gles-ti572x/lib${library}.so
        ln -s lib${library}.so ${D}${libdir}/gles-ti572x/lib${library}.so.1
    done

    install -d ${D}${sysconfdir}/profile.d
    echo "#!/bin/sh

export LD_LIBRARY_PATH=/usr/lib/gles-ti572x
" > ${D}${sysconfdir}/profile.d/sgx_gles.sh
    chmod 755 ${D}${sysconfdir}/profile.d/sgx_gles.sh
}


PACKAGES = "${PN}"
PACKAGEFUNCS:remove = "package_do_shlibs"

FILES:${PN} = " ${libdir}/gles-*/* ${sysconfdir}/profile.d/*.sh"

INSANE_SKIP:${PN} += "dev-so ldflags useless-rpaths"
INSANE_SKIP:${PN} += "already-stripped dev-deps"

QAPATHTEST[arch] = ""

CLEANBROKEN = "1"
