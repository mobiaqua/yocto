DESCRIPTION = "VM tool launch Linux trigger for semihosting."
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

inherit native

SRC_URI = "file://vmtool.m file://vmtool.entitlements file://semihost_cmd_vmtool.sh"

S = "${WORKDIR}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile() {
    gcc -o vmtool vmtool.m -framework Foundation -framework Virtualization
    codesign --entitlements ${WORKDIR}/vmtool.entitlements -f -s - vmtool
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 vmtool ${D}${bindir}
    install -m 0755 ${WORKDIR}/semihost_cmd_vmtool.sh ${D}/${bindir}
}
