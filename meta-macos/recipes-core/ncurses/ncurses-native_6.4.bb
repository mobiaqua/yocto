SUMMARY = "The New Curses library"
DESCRIPTION = "SVr4 and XSI-Curses compatible curses library and terminfo tools including tic, infocmp, captoinfo. Supports color, multiple highlights, forms-drawing characters, and automatic recognition of keypad and function-key sequences. Extensions include resizable windows and mouse support on both xterm and Linux console using the gpm library."
HOMEPAGE = "http://www.gnu.org/software/ncurses/ncurses.html"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=c5a4600fdef86384c41ca33ecc70a4b8;endline=27"
SECTION = "libs"
INC_PR = "r0"
DEPENDS = "gnu-config-native"

inherit autotools binconfig-disabled multilib_header pkgconfig native

# MobiAqua:
FILESEXTRAPATHS:prepend := "${THISDIR}/../../../meta/recipes-core/ncurses/files:"

SRCREV = "1003914e200fd622a27237abca155ce6bf2e6030"

SRC_URI = "git://github.com/ThomasDickey/ncurses-snapshots.git;protocol=https;branch=master \
           file://0001-tic-hang.patch \
           file://0002-configure-reproducible.patch \
           file://0003-gen-pkgconfig.in-Do-not-include-LDFLAGS-in-generated.patch \
           file://exit_prototype.patch \
           file://0001-Fix-CVE-2023-29491.patch \
           file://0001-Updating-reset-code-ncurses-6.4-patch-20231104.patch \
           file://CVE-2023-50495.patch \
           file://CVE-2023-45918.patch \
           "

S = "${WORKDIR}/git"

PR = "${INC_PR}.0"

ENABLE_WIDEC ?= "true"

# Display corruption occurs on 64 bit hosts without these settings
# This was derrived from the upstream debian ncurses which uses
# these settings for 32 and 64 bit hosts.
EXCONFIG_ARGS = " \
    --disable-lp64 \
    --with-chtype='long' \
    --with-mmask-t='long'"

# Helper function for do_configure to allow multiple configurations
# $1 the directory to run configure in
# $@ the arguments to pass to configure
ncurses_configure() {
    mkdir -p $1
    cd $1
    shift
    oe_runconf \
            --without-debug \
            --without-ada \
            --without-gpm \
            --enable-hard-tabs \
            --enable-xmc-glitch \
            --enable-colorfgbg \
            --with-terminfo-dirs='${sysconfdir}/terminfo:${datadir}/terminfo' \
            --enable-static \
            --without-shared \
            --enable-overwrite \
            --disable-big-core \
            --disable-rpath \
            --without-profile \
            --enable-sigwinch \
            --enable-pc-files \
            ${EXCONFIG_ARGS} \
            --disable-stripping \
            --without-cxx-binding \
            --with-abi-version=5 \
            --disable-mixed-case \
            "$@" || return 1
    cd ..
}

PARALLEL_MAKE=""

export BUILD_CCFLAGS = "-I${S}/ncurses -I${S}/include -I${B}/ncurses -I${B}/include ${BUILD_CFLAGS}"
export BUILD_LDFLAGS = ""
export EXTRA_OEMAKE = '"BUILD_LDFLAGS=" "BUILD_CCFLAGS=${BUILD_CCFLAGS}" "CFLAGS=-D_DARWIN_C_SOURCE"'

do_configure() {
    install -m 0755 ${STAGING_DATADIR_NATIVE}/gnu-config/config.guess ${S}
    install -m 0755 ${STAGING_DATADIR_NATIVE}/gnu-config/config.sub ${S}

    # The --enable-pc-files requires PKG_CONFIG_LIBDIR existed
    mkdir -p ${PKG_CONFIG_LIBDIR}

    ncurses_configure "narrowc" || \
	    return 1
    ! ${ENABLE_WIDEC} || \
        ncurses_configure "widec" "--enable-widec" "--without-progs"
}

do_compile() {
    oe_runmake -C narrowc libs
    oe_runmake -C narrowc/progs
    ! ${ENABLE_WIDEC} || \
        oe_runmake -C widec libs
}

# Split original _install_opts to two parts.
# One is the options to install contents, the other is the parameters \
# when running command "make install"
# Note that install.libs will also implicitly install header files,
# so we do not need to explicitly specify install.includes.
# Doing so could in fact result in a race condition, as both targets
# (install.libs and install.includes) would install the same headers
# at the same time

_install_opts = " install.libs install.man "

_install_cfgs = "\
  DESTDIR='${D}' \
  PKG_CONFIG_LIBDIR='${libdir}/pkgconfig' \
"

do_install() {
        # Order of installation is important; widec installs a 'curses.h'
        # header with more definitions and must be installed last hence.
        # Compatibility of these headers will be checked in 'do_test()'.
        oe_runmake -C narrowc ${_install_cfgs} ${_install_opts} \
                install.progs

        # The install.data should run after install.libs, otherwise
        # there would be a race issue in a very critical conditon, since
        # tic will be run by install.data, and tic needs libtinfo.so
        # which would be regenerated by install.libs.
        oe_runmake -C narrowc ${_install_cfgs} \
                install.data


        ! ${ENABLE_WIDEC} || \
            oe_runmake -C widec ${_install_cfgs} ${_install_opts}

        cd narrowc

        # our ncurses has termcap support
        ln -sf libncurses.a ${D}${libdir}/libtermcap.a
        ln -sf curses.h ${D}${includedir}/ncurses.h

        # include some basic terminfo files
        # stolen ;) from gentoo and modified a bit
        for x in ansi console dumb linux rxvt screen screen-256color sun vt52 vt100 vt102 vt200 vt220 xterm-color xterm-xfree86 xterm-256color
        do
                local termfile="$(find "${D}${datadir}/terminfo/" -name "${x}" 2>/dev/null)"
                local basedir="$(basename $(dirname "${termfile}"))"

                if [ -n "${termfile}" ]
                then
                        install -d ${D}${sysconfdir}/terminfo/${basedir}
                        mv ${termfile} ${D}${sysconfdir}/terminfo/${basedir}/
                        ln -s /etc/terminfo/${basedir}/${x} \
                                ${D}${datadir}/terminfo/${basedir}/${x}
                fi
        done
}
