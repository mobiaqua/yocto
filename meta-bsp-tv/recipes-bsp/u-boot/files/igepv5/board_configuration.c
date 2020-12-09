#include <common.h>
#include <i2c.h>
#include <asm/emif.h>
#include <asm/arch/sys_proto.h>
#include <linux/time.h>
#include "board_configuration.h"

#define DIE_ID_REG_BASE     (OMAP54XX_L4_CORE_BASE + 0x2000)
#define DIE_ID_REG_OFFSET   0x200

#define IGEPv5_MAGIC_ID     0x37014651

struct igepv5_test {
    union {
        u32 result;
        u8 padding [256];
    }v;
};

struct igepv5_eeprom_config {
    u32 magic_id;                           /* eeprom magic id */
    u32 crc32;                              /* eeprom crc32 with crc variable set to 0 */
    /* board identification */
    char board_name [20];                   /* board name */
    char board_model [25];                  /* board model name */
    char board_version [20];                /* board version */
    char board_revision [20];
    char board_manufacturer [30];           /* board manufacturer */
    u32 board_serial_no;                    /* Serial Number */
    char board_Assembly_OF[30];             /* Manufacturing OF */
    char manf_date[20];
    /* board setup */
    unsigned char device_mac[6];            /* Ethernet Mac Address */
    struct emif_regs emif0;                 /* emif0 configuration */
    struct emif_regs emif1;                 /* emif1 configuration */
    struct dmm_lisa_map_regs lisa_regs;     /* lisa map & MA Registers */
    /* fab test result */
    struct igepv5_test fabTestResult;
}__attribute__((packet));

struct igepv5_eeprom_config igepv5_config = {
    .magic_id = IGEPv5_MAGIC_ID,
    .crc32 = 0,
    .board_name = "IGEPv5",
    .board_model = "IGEP0050-FULL-NI",
    .board_revision = "Engineering Sample",
    .board_version = "RB0",
    .board_manufacturer = "(c) ISEE (www.isee.biz)",
    .board_serial_no = 0,
    .manf_date = "10:38 10/11/2014",
    .emif0 = {
        .sdram_config_init              = SDRAM_CONFIG_1,
        .sdram_config                   = SDRAM_CONFIG_1,
        .sdram_config2                  = SDRAM_CONFIG_2,
        .ref_ctrl                       = REFRESH_CONTROL,
        .sdram_tim1                     = T_SDRAM_TIM1,
        .sdram_tim2                     = T_SDRAM_TIM2,
        .sdram_tim3                     = T_SDRAM_TIM3,
        .read_idle_ctrl                 = 0x00050000,
        .zq_config                      = ZQ_CONFIG,
        .temp_alert_config              = 0x00000000,
        .emif_ddr_phy_ctlr_1_init       = EMIF_DDR_PHY_CONTROL_1_INIT,
        .emif_ddr_phy_ctlr_1            = EMIF_DDR_PHY_CONTROL_1,
        .emif_ddr_ext_phy_ctrl_1        = 0x04040100,
        .emif_ddr_ext_phy_ctrl_2        = 0x00000000,
        .emif_ddr_ext_phy_ctrl_3        = 0x00000000,
        .emif_ddr_ext_phy_ctrl_4        = 0x00000000,
        .emif_ddr_ext_phy_ctrl_5        = 0x4350D435,
        .emif_rd_wr_lvl_rmp_win         = 0x00000000,
        .emif_rd_wr_lvl_rmp_ctl         = 0x80000000,
        .emif_rd_wr_lvl_ctl             = 0x00000000,
        .emif_rd_wr_exec_thresh         = 0x40000305
    },
    .emif1 = {
        .sdram_config_init              = SDRAM_CONFIG_1,
        .sdram_config                   = SDRAM_CONFIG_1,
        .sdram_config2                  = SDRAM_CONFIG_2,
        .ref_ctrl                       = REFRESH_CONTROL,
        .sdram_tim1                     = T_SDRAM_TIM1,
        .sdram_tim2                     = T_SDRAM_TIM2,
        .sdram_tim3                     = T_SDRAM_TIM3,
        .read_idle_ctrl                 = 0x00050000,
        .zq_config                      = ZQ_CONFIG,
        .temp_alert_config              = 0x00000000,
        .emif_ddr_phy_ctlr_1_init       = EMIF_DDR_PHY_CONTROL_1_INIT,
        .emif_ddr_phy_ctlr_1            = EMIF_DDR_PHY_CONTROL_1,
        .emif_ddr_ext_phy_ctrl_1        = 0x04040100,
        .emif_ddr_ext_phy_ctrl_2        = 0x00000000,
        .emif_ddr_ext_phy_ctrl_3        = 0x00000000,
        .emif_ddr_ext_phy_ctrl_4        = 0x00000000,
        .emif_ddr_ext_phy_ctrl_5        = 0x4350D435,
        .emif_rd_wr_lvl_rmp_win         = 0x00000000,
        .emif_rd_wr_lvl_rmp_ctl         = 0x80000000,
        .emif_rd_wr_lvl_ctl             = 0x00000000,
        .emif_rd_wr_exec_thresh         = 0x40000305
    },
    .lisa_regs = {
        .dmm_lisa_map_0 = DMM_LISA_MAP0,
        .dmm_lisa_map_1 = DMM_LISA_MAP1,
        .dmm_lisa_map_2 = DMM_LISA_MAP2_1G,    /* DMM_LISA_MAP2 = 4 GiB (is_ma_hm_interleave=1), DMM_LISA_MAP2_1G = 1 GiB (is_ma_hm_interleave=0)*/	
        .dmm_lisa_map_3 = DMM_LISA_MAP3,
        .is_ma_present  = 0x1,
    }
};

int igepv5_write_setup (uint8_t s_addr, const char* data, u32 size)
{
    u32 i;
    u32 remain = size % 32;
    u32 blocks = size / 32;
    for (i=0; i < blocks; i++){
        if(i2c_write(CONFIG_SYS_I2C_IGEPV5_CFG_BUS_ADDR, s_addr + (i*32), 2, (uint8_t*) data + (i*32), 32)){
            // printf("igepv5 write setup failed at: (%d) %d - %d\n", i, blocks, remain);
            return -1;
        }
        udelay(5000);
    }
    if(remain > 0){
        if(i2c_write(CONFIG_SYS_I2C_IGEPV5_CFG_BUS_ADDR, s_addr + (i*32), 2, (uint8_t*) data + (i*32), remain))
            return -1;
        else
            udelay(5000);
    }
    return 0;
}

int igepv5_read_setup (uint8_t s_addr, char* data, u32 size)
{
    u32 i;
    u32 remain = size % 32;
    u32 blocks = size / 32;
    for (i=0; i < blocks; i++){
        if(i2c_read(CONFIG_SYS_I2C_IGEPV5_CFG_BUS_ADDR, s_addr + (i*32), 2, (uint8_t*) data + (i*32), 32)){
            return -1;
        }
    }
    if(remain > 0)
        if(i2c_read(CONFIG_SYS_I2C_IGEPV5_CFG_BUS_ADDR, s_addr + (i*32), 2, (uint8_t*) data + (i*32), remain))
            return -1;
    return 0;
}

/* check eeprom magic */
/* 0: ok and 1 : error */
int check_magic (void)
{
    u32 magic_id = 0;
    if(igepv5_read_setup(0, (char*) &magic_id, sizeof(magic_id)))
        return -1;
    return (magic_id != IGEPv5_MAGIC_ID);
}

void set_factory_defaults (void)
{
    unsigned int reg;

    /* Generate the Mac Address */
    reg = DIE_ID_REG_BASE + DIE_ID_REG_OFFSET;
    /*
    * create a fake MAC address from the processor ID code.
    * first byte is 0x02 to signify locally administered.
    */
    igepv5_config.device_mac[0] = 0x02;
    igepv5_config.device_mac[1] = readl(reg + 0x10) & 0xff;
    igepv5_config.device_mac[2] = readl(reg + 0xC) & 0xff;
    igepv5_config.device_mac[3] = readl(reg + 0x8) & 0xff;
    igepv5_config.device_mac[4] = readl(reg) & 0xff;
    igepv5_config.device_mac[5] = (readl(reg) >> 8) & 0xff;
}

void set_factory_reset (void)
{
    set_factory_defaults();
    // Calculate crc32
    igepv5_config.crc32 = crc32(0, (const unsigned char*) &igepv5_config, sizeof(struct igepv5_eeprom_config));
    // Save Buffer in eeprom
    if(!igepv5_write_setup (0, (char*) &igepv5_config, sizeof(struct igepv5_eeprom_config)))
        printf("Reset factory done\n");
}

int check_eeprom (void)
{
    i2c_init(CONFIG_SYS_OMAP24_I2C_SPEED, CONFIG_SYS_OMAP24_I2C_SLAVE);
    /* Check if baseboard eeprom is available */
    if (i2c_probe(CONFIG_SYS_I2C_IGEPV5_CFG_BUS_ADDR)) {
            debug("Could not probe the EEPROM at 0x%x\n",
                   CONFIG_SYS_I2C_IGEPV5_CFG_BUS_ADDR);
            return -1;
    }
    return 0;
}

void init_igepv5_board_configuration (int set_default)
{
    u32 crc_value = 0;
    u32 crc_save_value = 0;
    int org_bus_num;
    org_bus_num = i2c_get_bus_num();

    if(check_eeprom() != 0){
        set_factory_defaults();
        printf("eeprom not found, using defaults\n");
        goto error;
    }

    i2c_set_bus_num(CONFIG_SYS_I2C_IGEPV5_CFG_BUS_NUM);

    if(set_default == 1)
        set_factory_reset();

    // Check eeprom magic
    if(check_magic() != 0) {
        /* use default configuration */
        set_factory_defaults();
        printf("eeprom check magic failed, using defaults\n");
        goto error;
    }

    // Read eeprom configuration
    if(igepv5_read_setup(0, (char*) &igepv5_config, sizeof(struct igepv5_eeprom_config))){
        printf("eeprom read setup failed, try a reset factory\n");
        hang();
    }

    // Verify crc32
    crc_save_value = igepv5_config.crc32;
    igepv5_config.crc32 = 0;
    crc_value = crc32(0, (const unsigned char*) &igepv5_config, sizeof(struct igepv5_eeprom_config));
    if(crc_save_value != crc_value){
        printf("eeprom crc32 check failed, try a reset factory\n");
        hang();
    }

error:
    i2c_set_bus_num(org_bus_num);
}

/* Configuration interface */

struct emif_regs* get_emif_configuration (enum emif_id t)
{
	struct emif_regs* regs = NULL;
	switch(t){
		case EMIF0:
			regs = &igepv5_config.emif0;
			break;
		case EMIF1:
			regs = &igepv5_config.emif1;
			break;
		default:
			regs = &igepv5_config.emif0;
			break;
	}
	return regs;
}

struct dmm_lisa_map_regs* get_lisa_configuration ()
{
	return &igepv5_config.lisa_regs;
}

const char* get_memory_from_config (void)
{
    u32 reg = get_lisa_configuration()->dmm_lisa_map_2;
    reg >>= 8;
    reg &= 0x00000003;
    if(reg == 1)
        return "1GiB";
    else if(reg == 3)
        return "4GiB";
    return "0GiB";
}

const unsigned char* getBoardMacAddr (void)
{
    return igepv5_config.device_mac;
}

const char* getKernelMem (void)
{
    u32 reg = get_lisa_configuration()->dmm_lisa_map_2;
    reg >>= 8;
    reg &= 0x00000003;
    if(reg == 1)
        return "mem=1008M@0x80000000";
    else if(reg == 3)
        return "mem=2032M@0x80000000 mem=2048M@0x300000000";
    return "";
}
