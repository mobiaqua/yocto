SUMMARY = "Linux Userspace x86_64 Emulator with a twist"
DESCRIPTION = "Box64 lets you run x86_64 Linux programs (such as games) on non-x86_64 Linux systems, like ARM (host system needs to be 64-bit little-endian)."
HOMEPAGE = "https://box86.org/"
SECTION = "emulation"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a9b8b6dfbea869fad734d566242d5f55"


RDEPENDS:${PN} = "libgomp liblzma"

SRC_URI = "git://github.com/ptitSeb/box64.git;branch=main;protocol=https \
           file://add_omp_set_num_threads.patch \
           file://no_logs.patch \
"

SRCREV = "0f4f274bc7be14899d17685d519827ed0b3cae25"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = "-D ARM_DYNAREC=ON -D CMAKE_BUILD_TYPE=RelWithDebInfo"

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${B}/box64 ${D}${bindir}/box64
}

INSANE_SKIP += "buildpaths"