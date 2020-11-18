SOC_FAMILY = "soc-tv"
TARGET_ARCH = "arm"

require conf/machine/include/tune-cortexa9.inc

KERNEL_IMAGETYPE = "uImage"

UBOOT_ENTRYPOINT = "0x80008000"
UBOOT_LOADADDRESS = "0x80008000"

EXTRA_IMAGEDEPENDS += "u-boot"
