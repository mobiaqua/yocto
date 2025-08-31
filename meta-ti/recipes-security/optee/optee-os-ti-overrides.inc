# Use TI SECDEV for signing
inherit ti-secdev

EXTRA_OEMAKE:remove = "CFG_MAP_EXT_DT_SECURE=y"

EXTRA_OEMAKE:append:k3 = " ${@ 'CFG_CONSOLE_UART='+ d.getVar('OPTEE_K3_USART') if d.getVar('OPTEE_K3_USART') else ''}"
EXTRA_OEMAKE:append:k3 = " ${@ 'CFG_TZDRAM_START='+ d.getVar('OPTEE_K3_TZDRAM_START') if d.getVar('OPTEE_K3_TZDRAM_START') else ''}"

EXTRA_OEMAKE:append:am62xx = " CFG_TEE_CORE_LOG_LEVEL=1"
EXTRA_OEMAKE:append:am62lxx = " CFG_TEE_CORE_LOG_LEVEL=1"
EXTRA_OEMAKE:append:am62pxx = " CFG_TEE_CORE_LOG_LEVEL=1"
EXTRA_OEMAKE:append:am62axx = " CFG_TEE_CORE_LOG_LEVEL=1"
EXTRA_OEMAKE:append:am62dxx = " CFG_TEE_CORE_LOG_LEVEL=1"
EXTRA_OEMAKE:append:j722s = " CFG_TEE_CORE_LOG_LEVEL=1"

do_compile:append:k3() {
    cp ${B}/core/tee-pager_v2.bin ${B}/bl32.bin
    cp ${B}/core/tee.elf ${B}/bl32.elf
}

# Signing procedure for legacy HS devices
optee_sign_legacyhs() {
    ( cd ${B}/core/; \
        ${TI_SECURE_DEV_PKG}/scripts/secure-binary-image.sh tee.bin tee.bin.signed; \
        normfl=`echo ${OPTEEFLAVOR} | tr "_" "-"`
        mv tee.bin.signed ${B}/$normfl.optee; \
    )

    if [ "${OPTEEPAGER}" = "y" ]; then
        oe_runmake -C ${S} clean
        oe_runmake -C ${S} all CFG_TEE_TA_LOG_LEVEL=0 CFG_WITH_PAGER=y
        ( cd ${B}/core/; \
            ${TI_SECURE_DEV_PKG}/scripts/secure-binary-image.sh tee.bin tee.bin.signed; \
            normfl=`echo ${OPTEEFLAVOR} | tr "_" "-"`
            mv tee.bin.signed ${B}/$normfl-pager.optee; \
        )
    fi
}

do_compile:append:ti43x() {
    optee_sign_legacyhs
}

do_compile:append:dra7xx() {
    optee_sign_legacyhs
}

do_install:append() {
    install -m 644 ${B}/*.optee ${D}${nonarch_base_libdir}/firmware/ || true
    install -m 644 ${B}/bl32.bin ${D}${nonarch_base_libdir}/firmware/ || true
    install -m 644 ${B}/bl32.elf ${D}${nonarch_base_libdir}/firmware/ || true

    # MobiAqua: re-add 'ta'
    # Install embedded TAs
    mkdir -p ${D}${nonarch_base_libdir}/optee_armtz/
    install -D -p -m0444 ${B}/ta/*/*.ta ${D}${nonarch_base_libdir}/optee_armtz/
}

optee_deploy_legacyhs() {
    cd ${DEPLOYDIR}/
    for f in optee/*.optee; do
        ln -sf $f ${DEPLOYDIR}/
    done
}

do_deploy:append:ti43x() {
    optee_deploy_legacyhs
}

do_deploy:append:dra7xx() {
    optee_deploy_legacyhs
}

# MobiAqua: empty deploy
do_deploy:k3() {
    :
}

# MobiAqua: re-add 'ta'
FILES:${PN} += "${nonarch_base_libdir}/optee_armtz/"

# Make the OS depend on embedded TAs
# MobiAqua: disabled 'ta'
#RDEPENDS:${PN} += "${PN}-ta"

# This is needed for bl32.elf
INSANE_SKIP:${PN}:append:k3 = " textrel"
