DESCRIPTION = "TI MM RPC"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://MmRpc.c;beginline=1;endline=31;md5=1292eba1c5ee38868a7718ba49bca925"

PV = "1.0"
INC_PR = "r0"

DEPENDS += "virtual/kernel"

SRCREV = "cc633bd24ea64eafcd51189ee1606303ee7da625"

SRC_URI = "git://github.com/mobiaqua/ti-libmmrpc.git;protocol=git"

S = "${WORKDIR}/git"

inherit pkgconfig autotools

EXTRA_OECONF += "KERNEL_INSTALL_DIR=${STAGING_KERNEL_DIR}"

do_configure() {
    ( cd ${S}; autoreconf -f -i -s )
    oe_runconf
}

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
