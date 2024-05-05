SUMMARY = "Userspace NFS server v3 protocol"
DESCRIPTION = "UNFS3 is a user-space implementation of the NFSv3 server \
specification. It provides a daemon for the MOUNT and NFS protocols, which \
are used by NFS clients for accessing files on the server."
HOMEPAGE = "https://github.com/unfs3/unfs3"
SECTION = "console/network"
LICENSE = "unfs3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9475885294e17c0cc0067820d042792e"

DEPENDS = "flex-native bison-native flex"
# MobiAqua: do not depend on 'libtirpc'
#DEPENDS += "libtirpc"
DEPENDS:append:class-nativesdk = " flex-nativesdk"

S = "${WORKDIR}/git"
SRC_URI = "git://github.com/unfs3/unfs3.git;protocol=https;branch=master \
           file://0001-Better-parsing-errors.patch \
           file://0002-Fix-dependency-order-for-yacc-bison-output.patch \
           file://0003-Proper-dependencies-on-top-level.patch \
           file://0004-Remove-prerequisites-from-install-targets.patch \
           file://0005-daemon.c-Check-exit-code-of-chdir.patch \
           file://0006-fh_cache-fix-stale-nfs-handle-on-rename-problem.patch \
           file://0007-Remove-aclocal.m4.patch \
           file://0008-Ignore-aclocal.m4-in-git.patch \
           file://0009-Retroactively-update-NEWS-for-0.9.23.patch \
           file://0010-Update-version-to-0.10.0.patch \
           file://0011-Update-dist-build-for-markdown-README.patch \
           file://0012-fix-building-on-macOS.patch \
           file://0013-Avoid-redundant-mountclient-files-in-tar.gz.patch \
           file://0014-Sort-tar-ball-file-list.patch \
           file://0015-Include-bootstrapping-files.patch \
           file://0016-Alias-off64_t-to-off_t-on-linux-if-not-defined.patch \
           file://0017-When-detaching-wait-for-the-child-process-to-initial.patch \
           file://0018-locate.c-Include-attr.h.patch \
           file://0019-nfs.c-Allow-max-sa.sun_path-for-a-localdomain-socket.patch \
           file://0020-daemon.c-Add-option-for-tcp-no-delay.patch \
           file://entitlements.plist \
           "
SRCREV = "3fa0568e6ef96e045286afe18444bc28fe93962b"
UPSTREAM_CHECK_GITTAGREGEX = "unfs3\-(?P<pver>\d+(\.\d+)+)"

PV = "0.10.0"

BBCLASSEXTEND = "native nativesdk"

inherit autotools pkgconfig
EXTRA_OECONF:append:class-native = " --sbindir=${bindir}"

do_install:append:darwin () {
    codesign --entitlements ${WORKDIR}/entitlements.plist -f -s - ${D}${bindir}/unfsd
}
