# PowerVR Graphics require several patches that have not made their way
# upstream yet. This allows us to build the shims we need without completely
# clobbering mesa.

require recipes-graphics/mesa/mesa.inc

SUMMARY += " (with PowerVR support for TI platforms)"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

LIC_FILES_CHKSUM = "file://docs/license.rst;md5=9a383ee9f65a4e939d6630e9b067ff58"

DEPENDS += "pvr-mesa-dri-support-stub"

BRANCH = "powervr/kirkstone/22.0"

SRC_URI = " \
    git://gitlab.freedesktop.org/StaticRocket/mesa.git;protocol=https;branch=${BRANCH} \
    file://0001-meson.build-check-for-all-linux-host_os-combinations.patch \
    file://0002-meson.build-make-TLS-ELF-optional.patch \
    file://0001-meson-misdetects-64bit-atomics-on-mips-clang.patch \
    file://0001-futex.h-Define-__NR_futex-if-it-does-not-exist.patch \
    file://0001-util-format-Check-for-NEON-before-using-it.patch \
    file://0001-Revert-egl-wayland-deprecate-drm_handle_format-and-d.patch \
    file://remove-outdated-debug-code.patch \
"

S = "${WORKDIR}/git"

SRCREV = "782d20add4ac718a9a23efda7ef3e20b1aa94335"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
MESON_BUILDTYPE = "${@['release','debug'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"

GALLIUMDRIVERS:panda = "sgx"
GALLIUMDRIVERS:beagle = "sgx"
GALLIUMDRIVERS:beagle64 = "pvr"
PACKAGECONFIG:append:panda = " sgx"
PACKAGECONFIG:append:beagle = " sgx"
PACKAGECONFIG:append:beagle64 = " pvr"
PACKAGECONFIG:remove = "video-codecs virgl"
PACKAGECONFIG[video-codecs] = ""
PACKAGECONFIG[virgl] = ""
PACKAGECONFIG[sgx] = "-Dgallium-sgx-alias=omapdrm,"
PACKAGECONFIG[pvr] = "-Dgallium-pvr-alias=tidss,"

PV = "22.0.3+pvr"

do_install:append () {
    # remove pvr custom pkgconfig
    rm -rf ${D}${datadir}/pkgconfig
}
