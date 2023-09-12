DESCRIPTION = "External Toolchain."
SECTION = "devtools"
PRIORITY = "optional"
LICENSE = ""
ERROR_QA:remove = "license-checksum"

DEPENDS:panda = "gdb-cross-arm"
DEPENDS:beagle = "gdb-cross-arm"
DEPENDS:beagle64 = "gdb-cross-aarch64"

do_configure() {
    :
}

do_compile[noexec] = "1"
do_install[noexec] = "1"
