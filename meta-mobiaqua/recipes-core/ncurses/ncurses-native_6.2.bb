DESCRIPTION = "Ncurses library"
HOMEPAGE = "http://www.gnu.org/software/ncurses/ncurses.html"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://ncurses/base/version.c;beginline=1;endline=27;md5=cf3c7ab00720a1b83391f49ea9956277"
SECTION = "libs"
INC_PR = "r0"

# MobiAqua: use older 5.4 version for native package

inherit autotools pkgconfig native

SRC_URI = "${GNU_MIRROR}/ncurses/ncurses-5.4.tar.gz \
           file://makefile_tweak.patch \
           file://use_ldflags.patch \
           file://visibility.patch \
           file://fix-macos.patch \
           "

SRC_URI[md5sum] = "069c8880072060373290a4fefff43520"
SRC_URI[sha256sum] = "5abce063cf431790f4e6a801a96c7eea0b33a41ecd0970f6312f52575c083b36"

S = "${WORKDIR}/ncurses-5.4"

PR = "${INC_PR}.0"

EXTRA_OECONF = "\
  --enable-static \
  --without-shared \
  --enable-overwrite \
  --without-debug  \
  --without-ada \
  --disable-rpath \
  --without-profile \
  --without-cxx-binding \
  --with-terminfo-dirs=${sysconfdir}/terminfo:${datadir}/terminfo \
"

PARALLEL_MAKE=""

export BUILD_CCFLAGS = "-I${S}/ncurses -I${S}/include -I${B}/ncurses -I${B}/include ${BUILD_CFLAGS}"
export BUILD_LDFLAGS = ""
export EXTRA_OEMAKE = '"BUILD_LDFLAGS=" "BUILD_CCFLAGS=${BUILD_CCFLAGS}" "CFLAGS=-D_DARWIN_C_SOURCE"'

do_install() {
	autotools_do_install

	# our ncurses has termcap support
	ln -sf libncurses.a ${D}${libdir}/libtermcap.a
	ln -sf curses.h ${D}${includedir}/ncurses.h

	for i in tput tset tic toe infotocap captoinfo infocmp clear reset tack
	do
		rm ${D}${bindir}/${i}
	done

	# include some basic terminfo files
	# stolen ;) from gentoo and modified a bit
	for x in ansi console dumb linux rxvt screen sun vt{52,100,102,200,220} xterm-color xterm-xfree86
        do
                local termfile="$(find "${D}${datadir}/terminfo/" -name "${x}" 2>/dev/null)"
                local basedir="$(basename $(dirname "$termfile"))"

                if [ -n "$termfile" ]
                then
                        install -d ${D}${sysconfdir}/terminfo/$basedir
                        mv $termfile ${D}${sysconfdir}/terminfo/$basedir/
                        ln -s /etc/terminfo/$basedir/${x} \
                                ${D}${datadir}/terminfo/$basedir/${x}
                fi
        done
	# i think we can use xterm-color as default xterm
	if [ -e ${D}${sysconfdir}/terminfo/x/xterm-color ]
	then
		ln -sf xterm-color ${D}${sysconfdir}/terminfo/x/xterm
	fi
        install -d ${D}${sysconfdir}/terminfo/r
	ln -sf rxvt ${D}${sysconfdir}/terminfo/r/rxvt-unicode
}
