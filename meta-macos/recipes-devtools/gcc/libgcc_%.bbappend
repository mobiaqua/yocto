FILESEXTRAPATHS:prepend := "${THISDIR}/gcc:"

SRC_URI:remove = "file://0016-handle-sysroot-support-for-nativesdk-gcc.patch file://0018-Add-ssp_nonshared-to-link-commandline-for-musl-targe.patch"
SRC_URI += "file://gcc-macos-fix-compile.patch"
