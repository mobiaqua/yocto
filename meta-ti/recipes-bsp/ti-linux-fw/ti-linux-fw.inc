SUMMARY = "Common include for TI Linux firmware"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE.ti;md5=b5aebf0668bdf95621259288c4a46d76"

PV = "2022.01"
INC_PR = "r4"

# Firmware versions
CORESDK_RTOS_VERSION = "08.02.00.04"
PRUETH_FW_AM65X_VERSION = "08.00.00.20"
PRUETH_FW_AM65X_SR2_VERSION = "02.02.12.08"
GOODIX_FW_VERSION = "1.0.0.0"
CADENCE_MHDP_FW_VERSION = "2.1.0"
IMG_DEC_FW_VERSION = "1.0"
CNM_WAVE521_FW_VERSION = "1.0.3"
TI_DM_FW_VERSION = "09.01.02"
TI_SYSFW_VERSION = "09.01.08"

TI_LINUX_FW_SRCREV ?= "9ee2fedb1fb4815f54310dd872d34faf9948c7c1"
SRCREV = "${TI_LINUX_FW_SRCREV}"

BRANCH ?= "ti-linux-firmware"

K3_IMAGE_GEN_SRCREV ?= "150f1956b4bdcba36e7dffc78a4342df602f8d6e"
SRCREV_imggen = "${K3_IMAGE_GEN_SRCREV}"
SRCREV_FORMAT = "imggen"

K3_IMAGE_GEN_BRANCH ?= "master"

SRC_URI = " \
    git://git.ti.com/git/processor-firmware/ti-linux-firmware.git;protocol=https;branch=${BRANCH} \
    git://git.ti.com/git/k3-image-gen/k3-image-gen.git;protocol=https;branch=${K3_IMAGE_GEN_BRANCH};destsuffix=imggen;name=imggen \
"

S = "${WORKDIR}/git"

CLEANBROKEN = "1"

# Make sure that lib/firmware, and all its contents are part of the package
FILES:${PN} = "${nonarch_base_libdir}/firmware"

# This is used to prevent the build system from stripping the firmwares
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

# This is used to prevent the build system from splitting out the firmware debug info into a separate file
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# Disable arch checking as firmware is likely to be a different arch from the Yocto build
INSANE_SKIP:${PN} += "arch"

# Firmware in Linux Firmware has no configure nor build steps
do_compile[noexec] = "1"
do_configure[noexec] = "1"