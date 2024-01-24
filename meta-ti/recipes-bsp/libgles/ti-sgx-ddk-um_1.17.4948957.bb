DESCRIPTION = "Userspace libraries for PowerVR SGX chipset on TI SoCs"
HOMEPAGE = "https://git.ti.com/graphics/omap5-sgx-ddk-um-linux"
LICENSE = "TI-TSPA"
LIC_FILES_CHKSUM = "file://TI-Linux-Graphics-DDK-UM-Manifest.doc;md5=b17390502bc89535c86cfbbae961a2a8"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "igep0030|panda|beagle"

PR = "r1"

BRANCH = "ti-img-sgx/dunfell/${PV}"

SRC_URI = " \
    git://git.ti.com/git/graphics/omap5-sgx-ddk-um-linux.git;protocol=https;branch=${BRANCH} \
"
SRCREV = "742cf38aba13e1ba1a910cf1f036a1a212c263b6"

TARGET_PRODUCT:panda = "ti443x"
TARGET_PRODUCT:beagle = "ti572x"
TARGET_PRODUCT:igep0030 = "ti335x"

RDEPENDS:${PN} += "libdrm libdrm-omap"

S = "${WORKDIR}/git"

do_compile() {
}

do_install() {
    install -d ${D}${libdir}/gles-${TARGET_PRODUCT}
    for library in GLESv1_PVR_MESA GLESv2_PVR_MESA PVRScopeServices dbm glslcompiler pvr_dri_support srv_init srv_um usc
    do
        if [ "${TARGET_PRODUCT}" = "ti572x" ]; then
            cp -p ${S}/targetfs/jacinto6evm/lib/lib${library}.so.${PV} ${D}${libdir}/gles-${TARGET_PRODUCT}/lib${library}.so
        else
            cp -p ${S}/targetfs/${TARGET_PRODUCT}/lib/lib${library}.so.${PV} ${D}${libdir}/gles-${TARGET_PRODUCT}/lib${library}.so
        fi
        ln -s lib${library}.so ${D}${libdir}/gles-${TARGET_PRODUCT}/lib${library}.so.1
    done

    install -d ${D}${sysconfdir}/profile.d
    echo "#!/bin/sh

export LD_LIBRARY_PATH=/usr/lib/gles-${TARGET_PRODUCT}
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
