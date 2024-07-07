SUMMARY = "Transport-Independent RPC library"
DESCRIPTION = "Libtirpc is a port of Suns Transport-Independent RPC library to Linux"
SECTION = "libs/network"
HOMEPAGE = "http://sourceforge.net/projects/libtirpc/"
BUGTRACKER = "http://sourceforge.net/tracker/?group_id=183075&atid=903784"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=f835cce8852481e4b2bbbdd23b5e47f3 \
                    file://src/netname.c;beginline=1;endline=27;md5=f8a8cd2cb25ac5aa16767364fb0e3c24"

PROVIDES = "virtual/librpc"

SRCREV = "d68523a88ba0a60b949c3bbb2c246be3cfeb0eea"

SRC_URI = "git://git.linux-nfs.org/projects/steved/libtirpc.git;protocol=git;branch=master \
	   file://0001-match-complain-defines-with-linux-defines.patch \
	   file://0002-added-missing-include-for-memset.patch \
	   file://0003-define-endian.h-include-for-macos.patch \
	   file://0004-skip-getpeerid-for-macos-it-s-part-of-unix.patch \
	   file://0005-pthreads-are-also-part-of-macos.patch \
	   file://0006-rpcent-struct-is-part-of-unix-also-macos.patch \
	  "
UPSTREAM_CHECK_URI = "https://sourceforge.net/projects/libtirpc/files/libtirpc/"
UPSTREAM_CHECK_REGEX = "(?P<pver>\d+(\.\d+)+)/"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-gssapi --disable-symvers"

BBCLASSEXTEND = "native nativesdk"

EXTRA_OECONF:append:class-native = " --enable-shared=no"
EXTRA_OECONF:append:class-nativesdk = " --enable-shared=no"
