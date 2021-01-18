DESCRIPTION = "External Toolchain."
SECTION = "devtools"
PRIORITY = "optional"
LICENSE = ""
ERROR_QA_remove = "license-checksum"

DEPENDS = "gdb-cross-arm"

do_configure() {
    :
}

do_compile[noexec] = "1"
do_install[noexec] = "1"
