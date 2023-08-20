require readline.inc

SRC_URI += "file://configure-fix.patch \
            file://norpath.patch \
           "

# MobiAqua:
FILESEXTRAPATHS:prepend := "${THISDIR}/../../../meta/recipes-core/readline/files:${THISDIR}/../../../meta/recipes-core/readline/${PN}:"

SRC_URI[archive.sha256sum] = "7589a2381a8419e68654a47623ce7dfcb756815c8fee726b98f90bf668af7bc6"

# MobiAqua: added static native and disabled examples
EXTRA_OECONF:append:class-native = " --enable-shared=no --disable-install-examples"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no --disable-install-examples"
