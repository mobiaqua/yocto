FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-macos-compilation.patch \
            file://phdr-fix.patch \
           "

EXTRA_OECONF += "--disable-nls --disable-debug"
