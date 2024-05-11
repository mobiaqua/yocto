require recipes-devtools/gdb/gdb-cross.inc
require recipes-devtools/gdb/gdb.inc

PACKAGECONFIG:remove = "python"

FILESEXTRAPATHS:prepend := "${THISDIR}/../../../meta/recipes-devtools/gdb/gdb:${THISDIR}/gdb:"

SRC_URI += "file://macos-fix-compilation.patch"

LTTNGUST = ""

do_configure:prepend () {
	# MobiAqua: linker in Xcode 15 is broken
	# W/A: Temporary use classic linker mode
	if [ `uname` == "Darwin" ] && [ `echo $(uname -r) | cut -d. -f1` -ge 23 ] ; then
		export LDFLAGS="${LDFLAGS} -Wl,-ld_classic"
	fi
}
