do_install:append:class-native () {
    rm ${D}/${libdir}/*.dylib
}
