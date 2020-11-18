CODEGEN_PYTHON_RDEPENDS = ""

do_install_append() {
        rm -f ${D}${bindir}/gtester-report
}
