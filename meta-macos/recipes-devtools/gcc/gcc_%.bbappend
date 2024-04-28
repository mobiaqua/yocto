FILESEXTRAPATHS:prepend := "${THISDIR}/gcc:"

SRC_URI:remove = "file://0017-handle-sysroot-support-for-nativesdk-gcc.patch"
SRC_URI += " file://gcc-macos-fix-compile.patch"
