DESCRIPTION ?= "TrueType font package ${PN}"
SECTION = "fonts"
PRIORITY = "optional"
# don't use RRECOMMENDS_${PN} here because sometimes each font is packaged to separate package and each one needs this dep to call update-fonts
RRECOMMENDS_${PN} += "font-update-common"

# we don't need a compiler nor a c library for these fonts
INHIBIT_DEFAULT_DEPS = "1"

inherit allarch

do_install() {
    install -d ${D}${datadir}/fonts/truetype/
    find ./ -name '*.tt[cf]' -exec install -m 0644 {} ${D}${datadir}/fonts/truetype/ \;
}

pkg_postinst_append_${PN}() {
    update-fonts
}

pkg_postrm_append_${PN}() {
    update-fonts
}

PACKAGE_ARCH = "all"
