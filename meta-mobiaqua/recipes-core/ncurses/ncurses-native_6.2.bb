DESCRIPTION = "Ncurses library"
HOMEPAGE = "http://www.gnu.org/software/ncurses/ncurses.html"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://ncurses/base/version.c;beginline=1;endline=27;md5=5526f2f3a29edc95538b368a4771edda"
SECTION = "libs"
INC_PR = "r0"

inherit pkgconfig native binconfig-disabled

SRC_URI = "${GNU_MIRROR}/ncurses/ncurses-6.2.tar.gz \
           file://0001-tic-hang.patch \
           file://0002-configure-reproducible.patch \
           "

SRC_URI[md5sum] = "e812da327b1c2214ac1aed440ea3ae8d"
SRC_URI[sha256sum] = "30306e0c76e0f9f1f0de987cf1c82a5c21e1ce6568b9227f7da5b71cbea86c9d"

S = "${WORKDIR}/ncurses-6.2"

PR = "${INC_PR}.0"

EXTRA_OECONF = "\
  --prefix=${prefix} \
  --disable-lp64 \
  --with-chtype='long' \
  --with-mmask-t='long' \
  --enable-static \
  --without-shared \
  --enable-overwrite \
  --without-debug  \
  --without-ada \
  --disable-rpath \
  --without-profile \
  --without-cxx-binding \
  --disable-mixed-case \
  --with-terminfo-dirs=${sysconfdir}/terminfo:${datadir}/terminfo \
"

PARALLEL_MAKE=""

export BUILD_CCFLAGS = "-I${S}/ncurses -I${S}/include -I${B}/ncurses -I${B}/include ${BUILD_CFLAGS}"
export BUILD_LDFLAGS = ""
export EXTRA_OEMAKE = '"BUILD_LDFLAGS=" "BUILD_CCFLAGS=${BUILD_CCFLAGS}" "CFLAGS=-D_DARWIN_C_SOURCE"'

do_configure() {
    install -m 0755 ${STAGING_DATADIR_NATIVE}/gnu-config/config.guess ${S}
    install -m 0755 ${STAGING_DATADIR_NATIVE}/gnu-config/config.sub ${S}
    ./configure ${EXTRA_OECONF}
}

do_install() {
	oe_runmake install DESTDIR=${D}

	# our ncurses has termcap support
	ln -sf libncurses.a ${D}${libdir}/libtermcap.a
	ln -sf curses.h ${D}${includedir}/ncurses.h

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
}
