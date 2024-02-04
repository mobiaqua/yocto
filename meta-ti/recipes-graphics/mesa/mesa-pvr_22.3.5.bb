# PowerVR Graphics require several patches that have not made their way
# upstream yet. This allows us to build the shims we need without completely
# clobbering mesa.

require recipes-graphics/mesa/mesa.inc

SUMMARY += " (with PowerVR support for TI platforms)"

LIC_FILES_CHKSUM = "file://docs/license.rst;md5=63779ec98d78d823a9dc533a0735ef10"

BRANCH = "powervr/kirkstone/${PV}"

SRC_URI = " \
    git://gitlab.freedesktop.org/StaticRocket/mesa.git;protocol=https;branch=${BRANCH} \
    file://0001-meson.build-check-for-all-linux-host_os-combinations.patch \
    file://0001-meson-misdetects-64bit-atomics-on-mips-clang.patch \
    file://0001-util-format-Check-for-NEON-before-using-it.patch \
    file://0001-freedreno-pm4-Use-unsigned-instead-of-uint-to-fix-mu.patch \
    file://0001-gallium-Fix-build-with-llvm-17.patch \
    file://0001-fix-gallivm-limit-usage-of-LLVMContextSetOpaquePoint.patch \
"

S = "${WORKDIR}/git"

PACKAGECONFIG:append:igep0030 = " sgx"
PACKAGECONFIG:append:panda = " sgx"
PACKAGECONFIG:append:beagle = " sgx"
PACKAGECONFIG:append:beagle64 = " pvr"

# Temporary work around to use a different SRCREV for SGX Mesa, vs Rogue Mesa
# Idea is these two should be the same, but currently a segfault is happening
# on certain platforms if the sgx commit is used.
SRCREV = "${@bb.utils.contains('PACKAGECONFIG', 'sgx', '1be98ba80452ebe38546a7fca26b5a70f2629083', '54fd9d7dea098b6f11c2a244b0c6763dc8c5690c', d)}"
PR = "sgxrgx${SRCPV}"

PACKAGECONFIG[pvr] = "-Dgallium-pvr-alias=tidss,"
PACKAGECONFIG[sgx] = "-Dgallium-sgx-alias=omapdrm,"

PACKAGECONFIG:remove = "video-codecs"
PACKAGECONFIG[video-codecs] = ""
PACKAGECONFIG:remove = "virgl"
PACKAGECONFIG[virgl] = ""
PACKAGECONFIG:remove = "elf-tls"
PACKAGECONFIG[elf-tls] = ""
PACKAGECONFIG:remove = "xvmc"
PACKAGECONFIG[xvmc] = ""

PACKAGE_ARCH = "${MACHINE_ARCH}"

GALLIUMDRIVERS:igep0030 = "sgx"
GALLIUMDRIVERS:panda = "sgx"
GALLIUMDRIVERS:beagle = "sgx"
GALLIUMDRIVERS:beagle64 = "pvr"

do_install:append () {
    # remove pvr custom pkgconfig
    rm -rf ${D}${datadir}/pkgconfig
}

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
MESON_BUILDTYPE = "${@['release','debug'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"
