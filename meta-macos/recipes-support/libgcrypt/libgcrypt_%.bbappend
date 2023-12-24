# MobiAqua: do not add libcap to dependencies
PACKAGECONFIG:remove = "capabilities"

EXTRA_OECONF:append:class-native = " --enable-shared=no"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no"
