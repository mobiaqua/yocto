DESCRIPTION = "Quartus II semihosting wrapper scripts."
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

inherit native

DEPENDS = ""

SRC_URI = "file://quartus_base.sh \
           file://install_software.sh \
           file://install_programmer.sh \
           file://install-quartus-expect.sh \
           file://install-programmer-13.0.1.232.exp \
           file://install-quartus-13.0.1.232.exp \
          "

S = "${WORKDIR}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}/${bindir}/installer/

    install -m 0755 quartus_base.sh ${D}
    install -m 0755 install_software.sh ${D}/installer/
    install -m 0755 install_programmer.sh ${D}/installer/
    install -m 0755 install-quartus-expect.sh ${D}/installer/
    install -m 0755 install-programmer-13.0.1.232.exp ${D}/installer/
    install -m 0755 install-quartus-13.0.1.232.exp ${D}/installer/

    for tool in asm cdb cmd cpf cvp drc eda fit hps jli map pgm pow sh sim stp
    do
        ln -s ../quartus_base.sh ${D}/${bindir}/quartus_${tool}
    done
}
