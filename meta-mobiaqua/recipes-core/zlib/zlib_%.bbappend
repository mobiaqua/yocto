CONF_PARAMS = "-shared"
CNNF_PARAMS_class-native = "-static"

do_configure() {
	./configure --prefix=${prefix} ${CONF_PARAMS} --libdir=${libdir} --uname=GNU
}

do_compile() {
	oe_runmake
}
