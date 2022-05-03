require scummvm.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}/scummvm-${PV}"

FILESPATHPKG:prepend = "scummvm-${PV}:"

SRC_URI[sha256sum] = "bfee179bd1a49d69cf8a367a8549585702ddf80f550cd85f35ff23e674de1099"
