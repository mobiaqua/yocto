SUMMARY = "Userspace NFS server v3 protocol"
DESCRIPTION = "UNFS3 is a user-space implementation of the NFSv3 server \
specification. It provides a daemon for the MOUNT and NFS protocols, which \
are used by NFS clients for accessing files on the server."
HOMEPAGE = "https://github.com/unfs3/unfs3"
SECTION = "console/network"
LICENSE = "unfs3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9475885294e17c0cc0067820d042792e"

DEPENDS = "flex-native bison-native flex"
DEPENDS += "libtirpc"
DEPENDS:append:class-nativesdk = " flex-nativesdk"

S = "${WORKDIR}/git"
SRC_URI = "git://github.com/unfs3/unfs3.git;protocol=https;branch=master \
           file://0001-nfs.c-Allow-max-sa.sun_path-for-a-localdomain-socket.patch \
           file://0002-daemon.c-Add-option-for-tcp-no-delay.patch \
           file://0003-force-ipv4.patch \
           file://entitlements.plist \
           "
SRCREV = "9ed6a635fafc43fd97d8977a9ea4f075dde8c96e"
UPSTREAM_CHECK_GITTAGREGEX = "unfs3\-(?P<pver>\d+(\.\d+)+)"

PV = "0.10.0"

BBCLASSEXTEND = "native nativesdk"

inherit autotools pkgconfig
EXTRA_OECONF:append:class-native = " --sbindir=${bindir}"

do_install:append:darwin () {
    codesign --entitlements ${WORKDIR}/entitlements.plist -f -s - ${D}${bindir}/unfsd
}
