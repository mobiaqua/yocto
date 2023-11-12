DESCRIPTION = "Quartus II semihosting wrapper scripts."
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

inherit native

DEPENDS = ""

SRC_URI = "file://quartus_base.sh \
           file://install_software.sh \
           file://install_programmer.sh \
          "

S = "${WORKDIR}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}/${bindir}

    install -m 0755 quartus_base.sh ${D}/${bindir}
    install -m 0755 install_software.sh ${D}/${bindir}
    install -m 0755 install_programmer.sh ${D}/${bindir}

    for tool in asm cdb cmd cpf cvp drc eda fit hps jli map pgm pow sh sim stp
    do
        ln -s ../quartus_base.sh ${D}/${bindir}/quartus_${tool}
    done
}
