require recipes-devtools/gdb/gdb-cross.inc
require recipes-devtools/gdb/gdb.inc

PACKAGECONFIG:remove = "python"

FILESEXTRAPATHS:prepend := "${THISDIR}/../../../meta/recipes-devtools/gdb/gdb:${THISDIR}/gdb:"

SRC_URI += "file://macos-fix-compilation.patch"

LTTNGUST = ""
