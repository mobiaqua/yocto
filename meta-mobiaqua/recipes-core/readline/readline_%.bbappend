LDFLAGS_remove = "-Wl,--version-script=${WORKDIR}/rl-native.map"

EXTRA_OECONF_append_class-native = " --enable-shared=no"
EXTRA_OECONF_append_class-nativesdk = " --enable-shared=no"
