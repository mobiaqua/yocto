SUMMARY = "TI SYSFW/TIFS Firmware packaging"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bdefa24d5cc107236bc35883aa7cd67a"

DEPENDS = "openssl-native u-boot-mkimage-native dtc-native ti-sci-fw"

PR = "${SRCREV}"

SRCREV = "ffae8800a5c81c149835ed1aa5e2fbbe65a49c0d"

SRC_URI = "git://git.ti.com/git/k3-image-gen/k3-image-gen.git;protocol=https;branch=master \
           file://enforce-local-bash.patch \
"

S = "${WORKDIR}/git"

CLEANBROKEN = "1"

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

SYSFW_BINARY = "sysfw-${SYSFW_SOC}-${SYSFW_CONFIG}.itb"

CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure[noexec] = "1"

EXTRA_OEMAKE = "\
    CROSS_COMPILE=${TARGET_PREFIX} SOC=${SYSFW_SOC} SOC_TYPE=${SYSFW_SUFFIX} \
    CONFIG=${SYSFW_CONFIG} SYSFW_DIR="${STAGING_DIR_HOST}${nonarch_base_libdir}/firmware/ti-sysfw" \
"

do_compile() {
    oe_runmake -C ${S}
}

do_install() {
    install -D -m 644 ${S}/${SYSFW_BINARY} ${D}/boot/sysfw.itb
}

FILES:${PN} = "/boot"

inherit deploy

do_deploy () {
    install -D -m 644 ${S}/${SYSFW_BINARY} ${DEPLOYDIR}/boot/sysfw.itb
}

addtask deploy before do_build after do_compile
