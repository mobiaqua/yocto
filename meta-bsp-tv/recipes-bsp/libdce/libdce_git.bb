DEPENDS = "libdce-firmware libdrm virtual/kernel"
#libmmrpc

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://libdce.c;beginline=1;endline=31;md5=faefee9f609ed62570c167e9bf6083ee"
#LIC_FILES_CHKSUM = "file://libdce.c;beginline=1;endline=31;md5=0a398cf815b8b5f31f552266cd453dae"

inherit autotools lib_package pkgconfig

PV = "1.0"
PR = "r0"
PR_append = "+gitr-${SRCREV}"

SRCREV = "36533bfb6c18e3536c84511a1e4c5a7cae1bb5bf"
#SRCREV = "7e03a352277dd1ddb079bc59eb4434b4cafb00e8"
SRC_URI = "git://github.com/mobiaqua/libdce.git;protocol=git"

S = "${WORKDIR}/git"

WARN_QA_remove = "ldflags"

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
