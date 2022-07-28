SRC_URI:remove = "file://0017-handle-sysroot-support-for-nativesdk-gcc.patch"

do_compile:prepend () {
	export AS_FOR_TARGET="${TARGET_SYS}-as"
	export OBJDUMP_FOR_TARGET="${TARGET_SYS}-objdump"
	export OBJCOPY_FOR_TARGET="${TARGET_SYS}-objcopy"
	export STRIP_FOR_TARGET="${TARGET_SYS}-strip"
}
