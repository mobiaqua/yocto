SUMMARY = "AirPlay and AirPlay 2 audio player"
DESCRIPTION = "Shairport Sync is an AirPlay audio player for Linux and FreeBSD. It plays audio streamed from Apple devices and from AirPlay sources such as OwnTone (formerly forked-daapd)."
HOMEPAGE = "https://github.com/mikebrady/shairport-sync"

LICENSE  = "MIT"
LIC_FILES_CHKSUM = "file://LICENSES;md5=9f329b7b34fcd334fb1f8e2eb03d33ff"

inherit autotools pkgconfig update-rc.d

DEPENDS += "avahi alsa-lib popt libconfig soxr libplist libsodium libgcrypt ffmpeg initscripts xxd-native"
RDEPENDS:${PN} += "nqptp"

SRCREV = "910264e565095115c4889cc0c5428b3165d15e3f"
PV = "${PN}+git${SRCPV}"
SRC_URI = "git://github.com/mikebrady/shairport-sync.git;branch=master;protocol=https \
           file://shairport-sync.d \
           file://ffmpeg7-fixes.patch"

S = "${WORKDIR}/git"

EXTRA_OECONF += "--sysconfdir=/etc --with-alsa --with-soxr --with-avahi --with-ssl=openssl --with-airplay-2"

INITSCRIPT_NAME = "shairport-sync"

do_install:append() {
    if ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','busybox','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -d ${D}${sysconfdir}/rcS.d
        install -m 0755 ${WORKDIR}/shairport-sync.d ${D}${sysconfdir}/init.d/shairport-sync
        ln -sf ../init.d/${INITSCRIPT_NAME} ${D}${sysconfdir}/rcS.d/S51${INITSCRIPT_NAME}
    fi
}