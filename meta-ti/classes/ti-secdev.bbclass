# Helper class to prepare correct environment for signing with TI Security Development Tools

# K3 SECDEV scripts use OpenSSL
DEPENDS:append:beagle64 = " openssl-native"
DEPENDS:append:beagle64r5 = " openssl-native"

# Use package version of TI SECDEV for K3 if one is not provided through the environment
DEPENDS:append:beagle64 = "${@ '' if d.getVar('TI_SECURE_DEV_PKG_K3') else ' ti-k3-secdev-native' }"
DEPENDS:append:beagle64r5 = "${@ '' if d.getVar('TI_SECURE_DEV_PKG_K3') else ' ti-k3-secdev-native' }"
TI_K3_SECDEV_INSTALL_DIR = "${STAGING_DIR_NATIVE}${datadir}/ti/ti-k3-secdev"
TI_SECURE_DEV_PKG:beagle64 = "${@ d.getVar('TI_SECURE_DEV_PKG_K3') or d.getVar('TI_K3_SECDEV_INSTALL_DIR') }"
TI_SECURE_DEV_PKG:beagle64r5 = "${@ d.getVar('TI_SECURE_DEV_PKG_K3') or d.getVar('TI_K3_SECDEV_INSTALL_DIR') }"

# The SECDEV scripts may need their own location provided through the environment
export TI_SECURE_DEV_PKG
