require recipes-core/ncurses/ncurses.inc

SRC_URI += "file://0001-tic-hang.patch \
           file://0002-configure-reproducible.patch \
           file://0003-gen-pkgconfig.in-Do-not-include-LDFLAGS-in-generated.patch \
           "
# commit id corresponds to the revision in package version
SRCREV = "a0bc708bc6954b5d3c0a38d92b683c3ec3135260"
S = "${WORKDIR}/git"
EXTRA_OECONF += "--with-abi-version=5"
UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+(\.\d+)+)$"

# This is needed when using patchlevel versions like 6.1+20181013
#CVE_VERSION = "${@d.getVar("PV").split('+')[0]}.${@d.getVar("PV").split('+')[1]}"

# MobiAqua:
FILESEXTRAPATHS:prepend := "${THISDIR}/../../../meta/recipes-core/ncurses/files:"

# MobiAqua: removed 'st st-256color'
ALTERNATIVE:ncurses-terminfo:class-target = ""

# MobiAqua: disabled native for separated native package
BBCLASSEXTEND = ""

# MobiAqua: added '--disable-mixed-case' to match native
EXTRA_OECONF += "--disable-mixed-case"
