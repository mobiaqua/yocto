SUMMARY = "MPV GUI"
DESCRIPTION = "GUI for mpv."
SECTION = "multimedia"

DEPENDS = " \
    freetype \
    fontconfig \
    libdrm \
    curl \
"

RDEPENDS:${PN} = "ttf-dejavu-sans libcurl"

PV = "1.0+git"
PR = "r1"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "58472186bb05953f6badc9549989c8ca79d2f67c"
SRC_URI = "git://github.com/mobiaqua/mpv-gui.git;protocol=https;branch=main \
           file://mpv-gui-default \
           file://mpv-gui-init \
          "

S = "${WORKDIR}/git"

inherit pkgconfig update-rc.d

INITSCRIPT_NAME = "mpv-gui"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 . stop 10 0 1 6 ."

do_configure() {
    :
}

do_compile () {
    export CXXFLAGS="${CXXFLAGS} `pkg-config --cflags libdrm fontconfig freetype2 libcurl`"
    export LDFLAGS="${LDFLAGS}"
    export LIBS=`pkg-config --libs libdrm fontconfig freetype2 libcurl`
    cd ${S}
    oe_runmake
}

do_install () {
    cd ${S}
    install -d ${D}/${bindir}
    install -m 0755 mpv-gui ${D}${bindir}

    install -d ${D}${sysconfdir}/default
    install -m 0755 ${WORKDIR}/mpv-gui-default ${D}${sysconfdir}/default/mpv-gui

    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/mpv-gui-init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    fi

    if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/mpv-gui-init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
        install -d ${D}${sysconfdir}/rcS.d
        install -d ${D}${sysconfdir}/rc6.d
        ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S99${INITSCRIPT_NAME}
        ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rc6.d/S10${INITSCRIPT_NAME}
    fi
}

FILES:${PN}-dbg = "${bindir}/.debug/mpv-gui"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
