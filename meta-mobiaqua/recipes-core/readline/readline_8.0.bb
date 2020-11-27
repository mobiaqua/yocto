require readline.inc

SRC_URI += "file://configure-fix.patch \
            file://norpath.patch"

SRC_URI[archive.md5sum] = "7e6c1f16aee3244a69aba6e438295ca3"
SRC_URI[archive.sha256sum] = "e339f51971478d369f8a053a330a190781acb9864cf4c541060f12078948e461"

# MobiAqua: added static native and disabled examples
EXTRA_OECONF_append_class-native = " --enable-shared=no --disable-install-examples"
EXTRA_OECONF_append_class-nativesdk = " --enable-shared=no --disable-install-examples"
