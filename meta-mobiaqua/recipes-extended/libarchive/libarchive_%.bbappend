FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://ignore-owner.patch"

DEPENDS_remove = "e2fsprogs-native"

EXTRA_OECONF_append_class-native = " --enable-shared=no"
EXTRA_OECONF_append_class-nativesdk = " --enable-shared=no"

# MobiAqua: w/a for missing ext2fs headers
do_configure_prepend() {
	mkdir -p ${STAGING_INCDIR_NATIVE}/ext2fs
}
