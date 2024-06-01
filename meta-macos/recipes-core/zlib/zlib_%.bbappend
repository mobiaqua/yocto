CONF_PARAMS = "-shared"
CNNF_PARAMS:class-native = "-static"

do_configure() {
	${S}/configure --prefix=${prefix} ${CONF_PARAMS} --libdir=${libdir} --uname=GNU
}

do_compile() {
	oe_runmake
}
