require recipes-bsp/ti-linux-fw/ti-linux-fw.inc

DEPENDS = "openssl-native u-boot-mkimage-native dtc-native"

CLEANBROKEN = "1"
PR = "${INC_PR}.2"

# Loaded by R5F core
COMPATIBLE_MACHINE = "k3r5"
COMPATIBLE_MACHINE:aarch64 = "null"
COMPATIBLE_MACHINE:beagle64r5 = "beagle64r5"

PACKAGE_ARCH = "${MACHINE_ARCH}"

# Use TI SECDEV for signing
inherit ti-secdev

SYSFW_SOC ?= "unknown"
SYSFW_SUFFIX ?= "unknown"
SYSFW_CONFIG ?= "unknown"

SYSFW_PREFIX = "sci"
SYSFW_PREFIX:beagle64r5 = "fs"

SYSFW_TISCI = "${S}/ti-sysfw/ti-${SYSFW_PREFIX}-firmware-${SYSFW_SOC}-*.bin"
SYSFW_TISCI:beagle64r5 = "${S}/ti-sysfw/ti-${SYSFW_PREFIX}-firmware-${SYSFW_SOC}-${SYSFW_SUFFIX}.bin"

SYSFW_BINARY = "sysfw-${SYSFW_SOC}-${SYSFW_CONFIG}.itb"
SYSFW_VBINARY = "sysfw-${PV}-${SYSFW_SOC}-${SYSFW_SUFFIX}-${SYSFW_CONFIG}.itb"
SYSFW_IMAGE = "sysfw-${SYSFW_SOC}-${SYSFW_SUFFIX}-${SYSFW_CONFIG}.itb"
SYSFW_SYMLINK ?= "sysfw.itb"

CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure[noexec] = "1"

EXTRA_OEMAKE = "\
    CROSS_COMPILE=${TARGET_PREFIX} SOC=${SYSFW_SOC} SOC_TYPE=${SYSFW_SUFFIX} \
    CONFIG=${SYSFW_CONFIG} SYSFW_DIR="${S}/ti-sysfw" \
"

do_compile() {
    cd ${WORKDIR}/imggen/
    oe_runmake
}

do_install() {
    install -d ${D}/boot

    # MobiAqua: changed to fixed file name 'sysfw.itb'
    if [ -f "${WORKDIR}/imggen/${SYSFW_BINARY}" ]; then
        install -D -m 644 ${WORKDIR}/imggen/${SYSFW_BINARY} ${D}/boot/sysfw.itb
    fi
    install -m 644 ${SYSFW_TISCI} ${D}/boot/
}

FILES:${PN} = "/boot"

inherit deploy

do_deploy () {
    install -d ${DEPLOYDIR}

    # MobiAqua: changed to fixed file name 'sysfw.itb'
    if [ -f "${WORKDIR}/imggen/${SYSFW_BINARY}" ]; then
        install -D -m 644 ${WORKDIR}/imggen/${SYSFW_BINARY} ${DEPLOYDIR}/boot/sysfw.itb
    fi
    install -m 644 ${SYSFW_TISCI} ${DEPLOYDIR}/boot/
}

addtask deploy before do_build after do_compile
