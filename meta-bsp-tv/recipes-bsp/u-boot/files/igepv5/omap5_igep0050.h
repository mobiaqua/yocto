/* SPDX-License-Identifier: GPL-2.0+ */
/*
 * (C) Copyright 2013
 * Texas Instruments Incorporated.
 * Sricharan R	  <r.sricharan@ti.com>
 *
 * Configuration settings for the TI EVM5430 board.
 * See ti_omap5_common.h for omap5 common settings.
 */

#ifndef __CONFIG_IGEP0050_H
#define __CONFIG_IGEP0050_H

#include <configs/ti_omap5_common.h>

#undef  CONFIG_EXTRA_ENV_SETTINGS
#define CONFIG_EXTRA_ENV_SETTINGS \
	DEFAULT_LINUX_BOOT_ENV \
	DEFAULT_MMC_TI_ARGS \
	DEFAULT_FIT_TI_ARGS \
	DEFAULT_COMMON_BOOT_TI_ARGS \
	DEFAULT_FDT_TI_ARGS \
	DFUARGS \
	NETARGS \
	NANDARGS \
	"findmac="\
		"if test $board_name = igep0050; then " \
			"setenv mac_addr ${mac_addr}; fi; " \

#define CONFIG_OMAP5_IGEPv5

#define CONFIG_SYS_NS16550_COM3		UART3_BASE

/* eeprom board configuration */
#define CONFIG_IGEPV5_CFG_EEPROM
#define CONFIG_SYS_I2C_IGEPV5_CFG_BUS_NUM   0
#define CONFIG_SYS_I2C_IGEPV5_CFG_BUS_ADDR  0x50

#define CONFIG_SYS_MMC_ENV_DEV 0

/* Required support for the TCA642X GPIO we have on the uEVM */
#define CONFIG_TCA642X
#define CONFIG_TCA641X
#define CONFIG_SYS_I2C_TCA642X_BUS_NUM 3
#define CONFIG_SYS_I2C_TCA642X_ADDR 0x21

/* USB UHH support options */

#define CONFIG_USB_MAX_CONTROLLER_COUNT 3

#define CONFIG_EHCI_HCD_INIT_AFTER_RESET

#define CONFIG_OMAP_EHCI_PHY2_RESET_GPIO 80
#define CONFIG_OMAP_EHCI_PHY3_RESET_GPIO 79

#define CONFIG_OMAP_USBLAN_ENABLE_GPIO 78
#define CONFIG_OMAP_USBLAN_RESET_GPIO  15

/* Enabled commands */

/* USB Networking options */

#define CONFIG_ENV_OVERWRITE

#endif /* __CONFIG_IGEP0050_H */
