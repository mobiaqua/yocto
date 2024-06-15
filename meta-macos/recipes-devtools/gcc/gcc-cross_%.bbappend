FILESEXTRAPATHS:prepend := "${THISDIR}/gcc:"

SRC_URI:remove = "file://0016-handle-sysroot-support-for-nativesdk-gcc.patch file://0018-Add-ssp_nonshared-to-link-commandline-for-musl-targe.patch"

do_configure:prepend () {
	# MobiAqua: linker in Xcode 15 is broken
	# W/A: Temporary use classic linker mode
	if [ `uname` == "Darwin" ] && [ `echo $(uname -r) | cut -d. -f1` -ge 23 ] ; then
		export LDFLAGS="${LDFLAGS} -Wl,-ld_classic"
	fi
}

do_compile:prepend () {
	export AS_FOR_TARGET="${TARGET_SYS}-as"
	export OBJDUMP_FOR_TARGET="${TARGET_SYS}-objdump"
	export OBJCOPY_FOR_TARGET="${TARGET_SYS}-objcopy"
	export STRIP_FOR_TARGET="${TARGET_SYS}-strip"
}
