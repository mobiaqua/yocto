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

do_install () {
    #'ti335x ti343x ti437x ti443x jacinto6evm ti654x'
    products="ti443x jacinto6evm"
    for product in $products
    do
        target=$product
        if [ "$product" = "jacinto6evm" ]; then
            target=ti572x
        fi
        install -d ${D}${libdir}/gles-${target}
        for library in GLESv1_PVR_MESA GLESv2_PVR_MESA PVRScopeServices dbm glslcompiler pvr_dri_support srv_init srv_um usc
        do
            cp -p ${S}/targetfs/${product}/lib/lib${library}.so.${PV} ${D}${libdir}/gles-${target}/lib${library}.so
            ln -s lib${library}.so ${D}${libdir}/gles-${target}/lib${library}.so.1
        done
    done

    install -d ${D}${sysconfdir}/profile.d
    echo "#!/bin/sh

CPU_VER=\`cat /proc/cpuinfo | grep Hardware | cut -d : -f2\`
case \$CPU_VER in
    \" Generic OMAP4 (Flattened Device Tree)\")
    export LD_LIBRARY_PATH=/usr/lib/gles-ti443x
    ;;
    \" Generic OMAP5 (Flattened Device Tree)\"|\" Generic DRA74X (Flattened Device Tree)\")
    export LD_LIBRARY_PATH=/usr/lib/gles-ti572x
    ;;
esac" > ${D}${sysconfdir}/profile.d/sgx_gles.sh
    chmod 755 ${D}${sysconfdir}/profile.d/sgx_gles.sh
}

PACKAGES = "${PN}"
PACKAGEFUNCS:remove = "package_do_shlibs"

FILES:${PN} = " ${libdir}/gles-*/* ${sysconfdir}/profile.d/*.sh"

INSANE_SKIP:${PN} += "dev-so ldflags useless-rpaths"
INSANE_SKIP:${PN} += "already-stripped dev-deps"

QAPATHTEST[arch] = ""

CLEANBROKEN = "1"
