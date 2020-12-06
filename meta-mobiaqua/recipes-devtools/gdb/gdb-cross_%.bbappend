PACKAGECONFIG_remove = "python"

FILESEXTRAPATHS_prepend := "${THISDIR}/gdb:"

SRC_URI += "file://macos-fix-compilation.patch"
