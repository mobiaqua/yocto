DESCRIPTION = "Filter Pseudo arguments and pass comamnd arguments to shell"
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

DEFAULT_PREFERENCE = "99"

PROVIDES = "virtual/fakeroot-native"

SRC_URI = "file://pseudo-stub.c"

S = "${WORKDIR}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile() {
	${CC} -o pseudo-stub pseudo-stub.c ${CFLAGS} ${LDFLAGS}
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 pseudo-stub ${D}${bindir}/pseudo
}

BBCLASSEXTEND = "native"
