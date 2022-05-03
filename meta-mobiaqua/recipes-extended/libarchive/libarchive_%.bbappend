FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://ignore-owner.patch"

DEPENDS:remove = "e2fsprogs-native zstd"

EXTRA_OECONF:append:class-native = " --enable-shared=no"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no"

# MobiAqua: w/a for missing ext2fs headers
do_configure:prepend() {
	mkdir -p ${STAGING_INCDIR_NATIVE}/ext2fs
}
