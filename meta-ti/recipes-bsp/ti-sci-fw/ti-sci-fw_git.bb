require recipes-bsp/ti-linux-fw/ti-linux-fw.inc

# MobiAqua: skip it due imggen as source
LICENSE = "TI-TFL"
ERROR_QA:remove = "license-checksum"

# MobiAqua: local bash
SRC_URI += "file://enforce-local-bash.patch"

# MobiAqua: imggen as source
S = "${WORKDIR}/imggen"

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

# MobiAqua: imggen as source
SYSFW_TISCI = "${WORKDIR}/git/ti-sysfw/ti-${SYSFW_PREFIX}-firmware-${SYSFW_SOC}-*.bin"
# MobiAqua: imggen as source
SYSFW_TISCI:beagle64r5 = "${WORKDIR}/git/ti-sysfw/ti-${SYSFW_PREFIX}-firmware-${SYSFW_SOC}-${SYSFW_SUFFIX}.bin"

SYSFW_BINARY = "sysfw-${SYSFW_SOC}-${SYSFW_CONFIG}.itb"
SYSFW_VBINARY = "sysfw-${PV}-${SYSFW_SOC}-${SYSFW_SUFFIX}-${SYSFW_CONFIG}.itb"
SYSFW_IMAGE = "sysfw-${SYSFW_SOC}-${SYSFW_SUFFIX}-${SYSFW_CONFIG}.itb"
SYSFW_SYMLINK ?= "sysfw.itb"

CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure[noexec] = "1"

# MobiAqua: imggen as source
EXTRA_OEMAKE = "\
    CROSS_COMPILE=${TARGET_PREFIX} SOC=${SYSFW_SOC} SOC_TYPE=${SYSFW_SUFFIX} \
    CONFIG=${SYSFW_CONFIG} SYSFW_DIR="${WORKDIR}/git/ti-sysfw" \
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
    # MobiAqua: disabled
    #install -m 644 ${SYSFW_TISCI} ${D}/boot/
}

FILES:${PN} = "/boot"

inherit deploy

do_deploy () {
    install -d ${DEPLOYDIR}

    # MobiAqua: changed to fixed file name 'sysfw.itb'
    if [ -f "${WORKDIR}/imggen/${SYSFW_BINARY}" ]; then
        install -D -m 644 ${WORKDIR}/imggen/${SYSFW_BINARY} ${DEPLOYDIR}/boot/sysfw.itb
    fi
    # MobiAqua: disabled
    #install -m 644 ${SYSFW_TISCI} ${DEPLOYDIR}/boot/
}

addtask deploy before do_build after do_compile
