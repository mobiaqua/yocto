FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-macos-compilation.patch \
           "

EXTRA_OECONF += "--disable-nls --disable-debug"
