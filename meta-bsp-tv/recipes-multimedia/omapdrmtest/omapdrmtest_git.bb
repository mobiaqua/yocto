DEPENDS = "libdce libdrm ffmpeg libgbm virtual/egl"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://viddec3test.c;beginline=1;endline=16;md5=c391f44e40a29096285e3121923041df"

inherit autotools pkgconfig

PV = "1.0"
PR = "r0"
PR_append = "+gitr-${SRCREV}"

SRCREV = "c287ddf9a8346ff5d18df7ab70cfd7bc438b62e5"
SRC_URI = "git://git.ti.com/glsdk/omapdrmtest.git;protocol=git \
           file://0004-display-kmscube-align-width-on-128-bytes-to-please-Ducat.patch \
           file://0005-Hack-disp-kmscube-reduce-u-v-by-10.patch \
           file://disable-v4l-vpe.patch \
           file://revert-to-old-dce.patch \
           file://display-kmscube_revert.patch \
           file://viddec3test_revert.patch \
           file://use-hdmi.patch \
           file://drop-cube.patch \
"

S = "${WORKDIR}/git"

EXTRA_OECONF = "LDFLAGS='${LDFLAGS} -lm -lavcodec'"

ERROR_QA_remove = "ldflags"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"
CFLAGS_append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG', d, 1) == '1']}"

do_rm_work() {
        if [ "${DEBUG_BUILD}" == "no" ]; then
                cd ${WORKDIR}
                for dir in *
                do
                        if [ `basename ${dir}` = "temp" ]; then
                                echo "Not removing temp"
                        else
                                echo "Removing $dir" ; rm -rf $dir
                        fi
                done
        fi
}
