DESCRIPTION = "External Toolchain."
SECTION = "devtools"
PRIORITY = "optional"
LICENSE = ""
ERROR_QA:remove = "license-checksum"

DEPENDS:append = " make-native "

do_configure() {
    :
}

do_compile[noexec] = "1"
do_install[noexec] = "1"
