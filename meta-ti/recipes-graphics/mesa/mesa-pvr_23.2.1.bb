# PowerVR Graphics require several patches that have not made their way
# upstream yet. This allows us to build the shims we need without completely
# clobbering mesa.

require recipes-graphics/mesa/mesa.inc

SUMMARY += " (with PowerVR support for TI platforms)"

LIC_FILES_CHKSUM = "file://docs/license.rst;md5=63779ec98d78d823a9dc533a0735ef10"

COMPATIBLE_MACHINE = "beagle64"

BRANCH = "powervr/${PV}"

SRC_URI = " \
    git://gitlab.freedesktop.org/StaticRocket/mesa.git;protocol=https;branch=${BRANCH} \
    file://0001-meson.build-check-for-all-linux-host_os-combinations.patch \
    file://0001-meson-misdetects-64bit-atomics-on-mips-clang.patch \
    file://0001-gallium-Fix-build-with-llvm-17.patch \
    file://0001-meson-Disable-cmake-dependency-detector-for-llvm.patch \
"

S = "${WORKDIR}/git"

# MobiAqua: removed dynamic 'pvr', 'sgx' and added fixed 'pvr'
PACKAGECONFIG:append = "pvr"

SRCREV = "b12290126ba6a154f0e8b0a8c8b2b0d64f98e427"

PACKAGECONFIG[pvr] = "-Dgallium-pvr-alias=tidss,"

# MobiAqua: added 'virgl'
PACKAGECONFIG:remove = "video-codecs"
PACKAGECONFIG[video-codecs] = ""
PACKAGECONFIG:remove = "virgl"
PACKAGECONFIG[virgl] = ""
PACKAGECONFIG:remove = "elf-tls"
PACKAGECONFIG[elf-tls] = ""
PACKAGECONFIG:remove = "xvmc"
PACKAGECONFIG[xvmc] = ""

PACKAGE_ARCH = "${MACHINE_ARCH}"

GALLIUMDRIVERS:append = ",pvr"

VULKAN_DRIVERS:append = ",pvr"

do_install:append () {
    # remove pvr custom pkgconfig
    rm -rf ${D}${datadir}/pkgconfig
}

FILES:${PN}-dev += "${datadir}/mesa/wayland-drm.xml"
FILES:mesa-vulkan-drivers += "${libdir}/libpvr_mesa_wsi.so"

# MobiAqua: disabled
#RRECOMMENDS:mesa-megadriver:append:class-target = " ${@d.getVar('PREFERRED_PROVIDER_virtual/gpudriver')}"
