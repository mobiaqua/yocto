EXTRA_OEMAKE:darwin += "-lncurses"

do_compile:darwin () {
    oe_runmake macosx
}
