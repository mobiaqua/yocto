EXTRA_OEMAKE:darwin += "-lncurses"
EXTRA_OEMAKE += "'AR=${AR} rc' 'RANLIB=${RANLIB}'"

do_compile:darwin () {
    oe_runmake macosx
}
