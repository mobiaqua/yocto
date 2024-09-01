DESCRIPTION = "Quartus II semihosting wrapper scripts."
LICENSE = "MIT"
ERROR_QA:remove = "license-checksum"

inherit native

DEPENDS = ""

SRC_URI = "file://quartus_base.sh \
           file://install-quartus-expect.sh \
           file://install_software-13.0.1.232.sh \
           file://install_programmer-13.0.1.232.sh \
           file://install_software-17.0.0.595.sh \
           file://install_programmer-17.0.0.595.sh \
           file://install-programmer-13.0.1.232.exp \
           file://install-quartus-13.0.1.232.exp \
           file://install-programmer-17.0.0.595.exp \
           file://install-quartus-17.0.0.595.exp \
          "

S = "${WORKDIR}"

CLEANBROKEN = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}/${datadir}/bin
    install -d ${D}/${datadir}/installer

    install -m 0755 quartus_base.sh ${D}/${datadir}
    install -m 0755 install-quartus-expect.sh ${D}/${datadir}/installer

    install -m 0755 install_software-13.0.1.232.sh ${D}/${datadir}/installer/
    install -m 0755 install_programmer-13.0.1.232.sh ${D}/${datadir}/installer/

    install -m 0755 install_software-17.0.0.595.sh ${D}/${datadir}/installer/
    install -m 0755 install_programmer-17.0.0.595.sh ${D}/${datadir}/installer/

    install -m 0755 install-quartus-13.0.1.232.exp ${D}/${datadir}/installer/
    install -m 0755 install-programmer-13.0.1.232.exp ${D}/${datadir}/installer/

    install -m 0755 install-quartus-17.0.0.595.exp ${D}/${datadir}/installer/
    install -m 0755 install-programmer-17.0.0.595.exp ${D}/${datadir}/installer/

    for tool in asm cdb cmd cpf cvp drc dse eda fit hps jbcc jli map npp pgm pow sh si sta stp
    do
        ln -s ../quartus_base.sh ${D}/${datadir}/bin/quartus_${tool}
    done
}
