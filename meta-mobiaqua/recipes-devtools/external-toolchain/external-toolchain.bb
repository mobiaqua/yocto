DESCRIPTION = "External Toolchain."
SECTION = "devtools"
PRIORITY = "optional"
LICENSE = ""
ERROR_QA:remove = "license-checksum"

do_configure() {
    :
}

do_compile[noexec] = "1"
do_install[noexec] = "1"
