DESCRIPTION = "TI MM RPC"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://MmRpc.c;beginline=1;endline=31;md5=1292eba1c5ee38868a7718ba49bca925"

PV = "1.0"
INC_PR = "r0"

DEPENDS += "virtual/kernel"

SRCREV = "f055cbf0e3644593a1034c6eb2b9d1126d447df2"

SRC_URI = "git://github.com/mobiaqua/ti-libmmrpc.git;protocol=git"

S = "${WORKDIR}/git"

inherit pkgconfig autotools

EXTRA_OECONF += "KERNEL_INSTALL_DIR=${STAGING_KERNEL_DIR}"

do_configure() {
    ( cd ${S}; autoreconf -f -i -s )
    oe_runconf
}

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"
CFLAGS_append = "${@['',' -O0 -g3'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
