require recipes-devtools/gdb/gdb-common.inc

inherit gettext pkgconfig

PACKAGES =+ "gdbserver"
FILES:gdbserver = "${bindir}/gdbserver"

PACKAGECONFIG:remove = "python"

require recipes-devtools/gdb/gdb.inc

LTTNGUST = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/../../../meta/recipes-devtools/gdb/gdb:"
