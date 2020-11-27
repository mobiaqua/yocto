require recipes-devtools/gdb/gdb.inc
require recipes-devtools/gdb/gdb-${PV}.inc

LTTNGUST = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/../../../meta/recipes-devtools/gdb/${PN}:"
