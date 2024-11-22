DESCRIPTION = "Userspace libraries for PowerVR SGX chipset on TI SoCs"
HOMEPAGE = "https://git.ti.com/graphics/omap5-sgx-ddk-um-linux"
LICENSE = "TI-TSPA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7232b98c1c58f99e3baa03de5207e76f"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "igep0030|panda|beagle"

PR = "r1"

BRANCH = "${PV}/mesa/glibc-2.35"

SRC_URI = " \
    git://git.ti.com/git/graphics/omap5-sgx-ddk-um-linux.git;protocol=https;branch=${BRANCH} \
"
SRCREV = "84a396a4fb379f10931421e489ac8a199d6a9f2c"

TARGET_PRODUCT:panda = "ti443x_linux"
TARGET_PRODUCT:beagle = "ti572x_linux"
TARGET_PRODUCT:igep0030 = "ti335x_linux"

RDEPENDS:${PN} += "libdrm libdrm-omap"

S = "${WORKDIR}/git"

do_compile() {
}

do_install() {
    install -d ${D}${libdir}/gles-${TARGET_PRODUCT}
    for library in GLESv1_PVR_MESA GLESv2_PVR_MESA PVRScopeServices dbm glslcompiler pvr_dri_support srv_init srv_um usc
    do
        cp -p ${S}/targetfs/${TARGET_PRODUCT}/lws-generic/release/usr/lib/lib${library}.so.${PV} ${D}${libdir}/gles-${TARGET_PRODUCT}/lib${library}.so
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
