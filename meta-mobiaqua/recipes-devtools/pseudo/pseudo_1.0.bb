
LICENSE = "PD"

ERROR_QA_remove = "license-checksum"

DEFAULT_PREFERENCE = "99"

PROVIDES = "virtual/fakeroot-native"

SRC_URI = "file://pseudo-stub"

S = "${WORKDIR}"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/pseudo-stub ${D}${bindir}/pseudo
}

BBCLASSEXTEND = "native"
