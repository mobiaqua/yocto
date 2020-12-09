/*
 * (C) Copyright 2013
 * ISEE 2007 SL
 * Manel Caro     <mcaro@iseebcn.com>
 *
 * SPDX-License-Identifier:	GPL-2.0+
 */
#include <common.h>
#include <palmas.h>
#include <asm/arch/omap.h>
#include <asm/mach-types.h>
#include <asm/arch/sys_proto.h>
#include <asm/arch/mmc_host_def.h>
#include <tca642x.h>
#include <asm/emif.h>
#include <scsi.h>
#include "board_configuration.h"
//#include "spl_scsi.h"

#include "mux_data.h"

#if defined(CONFIG_USB_EHCI_HCD) || defined(CONFIG_USB_XHCI_OMAP)
#include <usb.h>
#include <asm/gpio.h>
#include <asm/arch/clock.h>
#include <asm/arch/ehci.h>
#include <asm/ehci-omap.h>
#include <asm/arch/sata.h>
#endif

#define PUSH_BUTTON_GPIO            118
#define FACTORY_CONFIG_RESET_SEC    3

DECLARE_GLOBAL_DATA_PTR;

const struct omap_sysinfo sysinfo = {
    "Board: IGEPv5\n"
};

/**
 * @brief tca641x_init - IGEPv5 default values for the GPIO expander
 * input reg, output reg, polarity reg, configuration reg
 */
struct tca642x_bank_info tca642x_init[] = {
    { .input_reg = 0x00,
      .output_reg = 0xFF,
      .polarity_reg = 0x00,
      .configuration_reg = 0x00 },
    { .input_reg = 0x00,
      .output_reg = 0xFF,
      .polarity_reg = 0x00,
      .configuration_reg = 0x00 },
};

enum t_led_color { t_board_red, t_board_yellow, t_push_red, t_push_green, t_push_off };

void set_user_button_leds (enum t_led_color color)
{
    switch(color){
        case t_board_red:
            tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0, 2, 0);
            break;
        case t_board_yellow:
            tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0, 2, 1);
            break;
        case t_push_red:
            tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0, 8, 8);
            tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0, 4, 0);
            break;
        case t_push_green:
            tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0, 4, 4);
            tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0, 8, 0);
            break;
        case t_push_off:
            tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0, 0xC, 0);
            tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0, 0xC, 0);
            break;
    }
}

void init_user_leds (void)
{
    tca642x_set_dir(CONFIG_SYS_I2C_TCA642X_ADDR, 0 , 0xE, 0);
    tca642x_set_val(CONFIG_SYS_I2C_TCA642X_ADDR, 0,  0xE, 0);
}

/**
 * @brief board_init
 *
 * @return 0
 */
int board_init(void)
{
    gpmc_init();
    gd->bd->bi_arch_number = MACH_TYPE_OMAP5_SEVM;
    gd->bd->bi_boot_params = (0x80000000 + 0x100); /* boot param addr */
    /* The initial State put the yellow color to on*/
    tca642x_set_inital_state(CONFIG_SYS_I2C_TCA642X_ADDR, tca642x_init);
    /* We off all leds, then only power supply led is on */
    init_user_leds();
    /* Now we put push button RED -> on */
    set_user_button_leds(t_push_red);
    /* Get Board Configuration from eeprom */
    init_igepv5_board_configuration(0);
    return 0;
}

int board_late_init(void)
{
    omap_sata_init();
    scsi_scan(1);
    return 0;
}

int board_eth_init(bd_t *bis)
{
    return 0;
}

void igepv5_spl_board_init (void)
{
#ifdef CONFIG_SPL_SATA_SUPPORT
    omap_sata_init();
    spl_scsi_scan(1);
#endif
}

#ifdef CONFIG_SPL_BUILD
/* s_init - SPL */
void board_s_init(void)
{
    int cfg_default = 0;
    u32 count = 0;
    while (gpio_get_value(PUSH_BUTTON_GPIO) == 0) {
        udelay(100000);
        count++;
        if(count == (FACTORY_CONFIG_RESET_SEC * 10)) {
            cfg_default = 1;
            break;
        }
    }
    if (cfg_default == 1){
        printf("Factory Reset\n");
    }
    init_igepv5_board_configuration(cfg_default);
}
#endif

#ifdef CONFIG_SYS_EMIF_PRECALCULATED_TIMING_REGS
void emif_get_reg_dump(u32 emif_nr, const struct emif_regs **regs)
{
    if (emif_nr == 1)
        *regs = get_emif_configuration(EMIF0);
    else
        *regs = get_emif_configuration(EMIF1);
}

void emif_get_dmm_regs(const struct dmm_lisa_map_regs **dmm_lisa_regs)
{
    *dmm_lisa_regs = get_lisa_configuration();
}
#endif

#if defined(CONFIG_USB_EHCI_HCD) || defined(CONFIG_USB_XHCI_OMAP)
static void enable_host_clocks(void)
{
	int auxclk;
	int hs_clk_ctrl_val = (OPTFCLKEN_HSIC60M_P3_CLK |
				OPTFCLKEN_HSIC480M_P3_CLK |
				OPTFCLKEN_HSIC60M_P2_CLK |
				OPTFCLKEN_HSIC480M_P2_CLK |
				OPTFCLKEN_UTMI_P3_CLK | OPTFCLKEN_UTMI_P2_CLK);

	/* Enable port 2 and 3 clocks*/
	setbits_le32((*prcm)->cm_l3init_hsusbhost_clkctrl, hs_clk_ctrl_val);

	/* Enable port 2 and 3 usb host ports tll clocks*/
	setbits_le32((*prcm)->cm_l3init_hsusbtll_clkctrl,
			(OPTFCLKEN_USB_CH1_CLK_ENABLE | OPTFCLKEN_USB_CH2_CLK_ENABLE));
#ifdef CONFIG_USB_XHCI_OMAP
	/* Enable the USB OTG Super speed clocks */
	setbits_le32((*prcm)->cm_l3init_usb_otg_ss_clkctrl,
			(OPTFCLKEN_REFCLK960M | OTG_SS_CLKCTRL_MODULEMODE_HW));
#endif

	auxclk = readl((*prcm)->scrm_auxclk1);
	/* Request auxilary clock */
	auxclk |= AUXCLK_ENABLE_MASK;
	writel(auxclk, (*prcm)->scrm_auxclk1);
}
#endif

/**
 * @brief misc_init_r - Configure EVM board specific configurations
 * such as power configurations, ethernet initialization as phase2 of
 * boot sequence
 *
 * @return 0
 */
int misc_init_r(void)
{
	char vMacAddress [30];
#ifdef CONFIG_PALMAS_POWER
	palmas_init_settings();
#endif

	if (!env_get("usbethaddr")) {
		eth_env_set_enetaddr_by_index("usbethaddr", 0, getBoardMacAddr());
	}
	// eth_setenv_enetaddr("mac_addr", getBoardMacAddr());
	sprintf(vMacAddress, "0x%02x,0x%02x,0x%02x,0x%02x,0x%02x,0x%02x", getBoardMacAddr()[0], \
							      getBoardMacAddr()[1], \
							      getBoardMacAddr()[2], \
							      getBoardMacAddr()[3], \
							      getBoardMacAddr()[4], \
							      getBoardMacAddr()[5]);
	env_set("mac_addr", vMacAddress);
	env_set("kernel_mem", getKernelMem());
	return 0;
}

void set_muxconf_regs_essential(void)
{
	do_set_mux((*ctrl)->control_padconf_core_base,
		   core_padconf_array_essential,
		   sizeof(core_padconf_array_essential) /
		   sizeof(struct pad_conf_entry));

	do_set_mux((*ctrl)->control_padconf_wkup_base,
		   wkup_padconf_array_essential,
		   sizeof(wkup_padconf_array_essential) /
		   sizeof(struct pad_conf_entry));
}

void set_muxconf_regs_non_essential(void)
{
	do_set_mux((*ctrl)->control_padconf_core_base,
		   core_padconf_array_non_essential,
		   sizeof(core_padconf_array_non_essential) /
		   sizeof(struct pad_conf_entry));

	do_set_mux((*ctrl)->control_padconf_wkup_base,
		   wkup_padconf_array_non_essential,
		   sizeof(wkup_padconf_array_non_essential) /
		   sizeof(struct pad_conf_entry));
}

void set_muxconf_regs(void)
{
	set_muxconf_regs_essential();
	set_muxconf_regs_non_essential();
}

#if defined(CONFIG_MMC)
int board_mmc_init(bd_t *bis)
{
	omap_mmc_init(0, 0, 0, -1, -1);
	//omap_mmc_init(1, 0, 0, -1, -1);
	return 0;
}
#endif

#if defined(CONFIG_USB_EHCI_HCD) && !defined(CONFIG_SPL_BUILD)
static struct omap_usbhs_board_data usbhs_bdata = {
	.port_mode[0] = OMAP_USBHS_PORT_MODE_UNUSED,
	.port_mode[1] = OMAP_EHCI_PORT_MODE_HSIC,
	.port_mode[2] = OMAP_EHCI_PORT_MODE_HSIC,
};

int ehci_hcd_init(int index, enum usb_init_type init,
		struct ehci_hccr **hccr, struct ehci_hcor **hcor)
{
	int ret;

	enable_host_clocks();

	ret = omap_ehci_hcd_init(index, &usbhs_bdata, hccr, hcor);
	if (ret < 0) {
		puts("Failed to initialize ehci\n");
		return ret;
	}

	return 0;
}

int ehci_hcd_stop(void)
{
	int ret;

	ret = omap_ehci_hcd_stop();
	return ret;
}

void usb_hub_reset_devices(int port)
{
}
#endif

#ifdef CONFIG_USB_XHCI_OMAP
/**
 * @brief board_usb_init - Configure EVM board specific configurations
 * for the LDO's and clocks for the USB blocks.
 *
 * @return 0
 */
int board_usb_init(int index, enum usb_init_type init)
{
	int ret;
#ifdef CONFIG_PALMAS_USB_SS_PWR
	ret = palmas_enable_ss_ldo();
#endif

	enable_host_clocks();

	return 0;
}
#endif
