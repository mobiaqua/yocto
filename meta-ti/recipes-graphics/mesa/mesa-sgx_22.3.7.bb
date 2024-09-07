# PowerVR Graphics require several patches that have not made their way
# upstream yet. This allows us to build the shims we need without completely
# clobbering mesa.

require recipes-graphics/mesa/mesa.inc

SUMMARY += " (with PowerVR support for TI platforms)"

LIC_FILES_CHKSUM = "file://docs/license.rst;md5=63779ec98d78d823a9dc533a0735ef10"

COMPATIBLE_MACHINE = "igep0030|panda|beagle"

BRANCH = "mesa-22.3-sgx"

SRC_URI = "git://github.com/mobiaqua/mesa-sgx.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"

SRCREV = "9d86c74493ec194ed01958eca71c5532b626df27"
PR = "sgx${SRCPV}"

PACKAGECONFIG:append = " sgx"
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

GALLIUMDRIVERS = "sgx"

do_install:append () {
    # remove pvr custom pkgconfig
    rm -rf ${D}${datadir}/pkgconfig
}

INSANE_SKIP += "buildpaths"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
MESON_BUILDTYPE = "${@['release','debug'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
CFLAGS:append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"
