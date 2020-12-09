#ifndef __IGEPv5_BOARD_CONFIGURATION_H__
#define __IGEPv5_BOARD_CONFIGURATION_H__

#include <asm/emif.h>

enum emif_id { EMIF0, EMIF1 };

struct emif_regs* get_emif_configuration (enum emif_id t);
struct dmm_lisa_map_regs* get_lisa_configuration (void);
void igepv5_print_banner (void);
const unsigned char* getBoardMacAddr (void);
void init_igepv5_board_configuration (int set_default);
const char* getKernelMem (void);



/* DDR3 Configuration */

/* Register Fields definition to DDR3 531,8 Mhz */
/* Register sdram_config_init = sdram_config = EMIF_SDRAM_CONFIG (0x4C000008) */

#define SDRAM_TYPE				3		/* SDRAM_TYPE : 3 = DDR3, 4 = LPDDR2 */
#define IBANK_POS				0		/* IBANK_POS : */
#define DDR_TERM				1		/* DDR_TERM: DDR3 termination resistor value
                                            set to 0 for disable,
                                            set to 1 for RZQ/4,
                                            set to 2 for RZQ/2,
                                            set to 3 for RZQ/6,
                                            set to 4 for RZQ/12,
                                            set to 5 for RZQ/8 */
#define DDR2_DDQS				1       /* DDR2_DDQS: DDR2 differential DDQS enable.
                                            NOT SUPPORTED. Set to 1 for compatibility */
#define DYN_ODT					0       /* DYN_ODT: DDR3 Dynamic ODT. NOT SUPPORTED.
                                            Set to 0 to turn off dynamic ODT. */
#define DDR_DISABLE_DLL			0       /* Disable DLL select. Set to 1 to disable DLL inside SDRAM */
#define SDRAM_DRIVE				1		/* SDRAM drive strength.For DDR3, set to 0 for RZQ/6 and
                                            set to 1 for RZQ/7. All other values are reserved. */

/* CAS WRITE Latency (Use the lowest value supported for best performance ) */
/* DDR3 CAS Write latency. Value of 0, 1, 2, and 3 (CAS write latency of 5, 6, 7, and 8) are supported. */
#define CWL_5					0
#define CWL_6					1
#define CWL_7					2
#define CWL_8					3
#define CWL						CWL_6

/* CAS Latency (referred to as read latency (RL) in some SDRAM specs)
The value of this field defines the CAS latency to be used when accessing connected SDRAM devices.
Values of 3, 4, 5, 6, 7, and 8 (CAS latency of 3, 4, 5, 6, 7, and 8) are supported for LPDDR2-SDRAM.
Values of 2, 4, 6, 8, 10, 12, and 14 (CAS latency of 5, 6, 7, 8, 9, 10, and 11) are supported for DDR3.
All other values are reserved.
*/
#define CL_5					2
#define CL_6					4
#define CL_7					6
#define CL_8					8
#define CL_9					10
#define CL_10					12
#define CL_11					14
#define CL						CL_7

#define NORROW_MODE				0           /* SDRAM data bus width. Set to 0 for 32-bit and set to 1 for 16-bit.
                                                All other values are reserved. In the current implementation, only 32-bit devices are supported.
                                                Two DDR3 devices are used in parallel to make one 32-bit device */
#define ROWSIZE					6			/* 6 = 15 row bits */
#define IBANK					3			/* 3 = 8 banks */
#define EBANK_CS0_ENABLE		0
#define EBANK_CS0_CS1_ENABLE	1
#ifdef IGEPv5_MEMORY_4G
#define EBANK					EBANK_CS0_CS1_ENABLE	/* External chip select setup. Defines whether SDRAM accesses will use 1 or 2 chip select lines. Set to 0 to use NCS0 only. Set to 1 to use NCS[1:0] */
#else
#define EBANK					EBANK_CS0_ENABLE	/* External chip select setup. Defines whether SDRAM accesses will use 1 or 2 chip select lines. Set to 0 to use NCS0 only. Set to 1 to use NCS[1:0] */
#endif
#define PAGESIZE				2			/* 1024 word page - 10 column bits */

#define SDRAM_CONFIG_1			(SDRAM_TYPE << 29) | (IBANK_POS << 27) | (DDR_TERM << 24) | \
								(DDR2_DDQS << 23) | (DYN_ODT << 21) | (DDR_DISABLE_DLL << 20) | \
								(SDRAM_DRIVE << 18) | (CWL << 16) | (NORROW_MODE << 14) | (CL << 10) | \
								(ROWSIZE << 7) | (IBANK << 4) | (EBANK << 3) | (PAGESIZE)

/* Register sdram_config2 = EMIF_SDRAM_CONFIG (0x4C00000C) */
#define EBANK_POS				0
#define SDRAM_CONFIG_2			(EBANK_POS << 27) | 0

/* Register ref_ctrl = EMIF_SDRAM_REFRESH_CONTROL (0x4C000010) */
#define INITREF_DIS 			0
#define SRT 					0
#define ASR 					0
#define PASR 					0
#define REFRESH_RATE 			4149 /* (531,8 Mhz * 7,8uS) */

#define REFRESH_CONTROL			(INITREF_DIS << 31) | (SRT << 29) | (ASR << 28) | (PASR << 24) | REFRESH_RATE


/* Register sdram_tim1 = EMIF_SDRAM_TIMMING_1 (0x4C000018) */

#define T_RTW					6	/* Minimum number of DDR clock cycles between Read to Write data phases, minus one.*/
#define T_RP					6	/* Minimum number of DDR clock cycles from Precharge to Activate or Refresh, minus one. */
#define T_RCD					6	/* Minimum number of DDR clock cycles from Activate to Read or Write,  minus one. */
#define T_WR					7	/* Minimum number of DDR clock cycles from last Write transfer to Precharge,  minus one. */
#define T_RAS					19	/* Minimum number of DDR clock cycles from Activate to Precharge, minus one. T_RAS value needs to be bigger than or equal to T_RDC value */
#define T_RC					26	/* Minimum number of DDR clock cycles from Activate to Activate,  minus one. */
#define T_RRD					6	/* Minimum number of DDR clock cycles from Activate to Activate for a different bank, minus one. For an 8-bank, this field must be equal to ((tFAW / (4 × tCK)) - 1) */
#define T_WTR					3	/* Minimum number of DDR clock cycles from last Write to Read, minus one. */
#define T_SDRAM_TIM1			(T_RTW << 29) | (T_RP << 25) | (T_RCD << 21) | (T_WR << 17) | (T_RAS << 12) | (T_RC << 6) | (T_RRD << 3) | (T_WTR)

/* Register sdram_tim2 = EMIF_SDRAM_TIMMING_2 (0x4C000020) */
#define T_XP					3	/* Minimum number of DDR clock cycles from power-down exit to any command other than a read command, minus one */
#define T_ODT					0	/* Minimum number of DDR clock cycles from ODT enable to write data driven for DDR3. Must be equal to tAOND */
#define T_XSNR					143	/* Minimum number of DDR clock cycles from Self-Refresh exit to any command other than a Read command, minus one. For DDR3, the value of tXS must be programmed. */
#define T_XSRD					511	/* Minimum number of DDR clock cycles from Self-Refresh exit to a Read command, minus one. For DDR3, the value of tXSDLL must be programmed. */
#define T_RTP					3	/* Minimum number of DDR clock cycles for the last read command to a Precharge command, minus one */
#define T_CKE					2	/* Minimum number of DDR clock cycles between CKE pin changes, minus one. */
#define T_SDRAM_TIM2			(T_XP << 28) | (T_ODT << 25) | (T_XSNR << 16) | (T_XSRD << 6) | (T_RTP << 3) | (T_CKE)

/* Register sdram_tim3 = EMIF_SDRAM_TIMMING_3 (0x4C000028) */
#define	T_PDLL_UL				0	/* Minimum number of DDR clock cycles for PHY DLL to unlock. A value of N will be equal to N x 128 clocks */
#define T_CSTA					2	/* Minimum number of DDR clock cycles between write-to-write or read-to-read data phases to different chip selects, minus one. */
#define T_CKESR					3	/* Minimum number of DDR clock cycles for which SDRAM must remain in Self Refresh, minus one */
#define T_ZQ_ZQCS				63	/* Number of DDR clock cycles for a ZQCS command, minus one. */
#define T_TDQSCKMAX				0	/* Number of DDR clock that satisfies tDQSCKmax for LPDDR2, minus one. */
#define T_RFC					138	/* Minimum number of DDR clock cycles from Refresh or Load Mode to Refresh or Activate, minus one.*/
#define T_RAS_MAX				8	/* Maximum number of REFRESH_RATE intervals from Activate to Precharge command.
										This field must be equal to ((tRASmax / tREFI)-1) rounded down to the next lower integer.
										Value for T_RAS_MAX can be calculated as follows:
											If tRASmax = 120 us and tREFI = 15.7 us,
												then T_RAS_MAX = ((120/15.7)-1) = 6.64. Round down to the next lower integer.
									Therefore, the programmed value must be 6.*/
#define T_SDRAM_TIM3			(T_PDLL_UL << 28) | (T_CSTA << 24) | (T_CKESR << 21) | (T_ZQ_ZQCS << 15) | (T_TDQSCKMAX << 13) | (T_RFC << 4) | T_RAS_MAX

/* Register zq_config = EMIF_SDRAM_OUTPUT_IMPEDANCE_CALIBRATION_CONFIG (0x4C0000C8) */
#ifdef IGEPv5_MEMORY_4G
#define ZQ_CS1EN				1	/* Writing a 1 enables ZQ calibration for CS1. */
#else
#define ZQ_CS1EN				0	/* Writing a 1 enables ZQ calibration for CS1. */
#endif
#define ZQ_CS0EN 				1	/* Writing a 1 enables ZQ calibration for CS0. */
#define ZQ_DUALCALEN 			0	/* ZQ Dual Calibration enable. Allows both ranks to be ZQ calibrated simultaneously.
									   Setting this bit requires both chip selects to have a separate calibration resistor per device. */
#define ZQ_SFEXITEN 			1	/* Writing a 1 enables the issuing of ZQCL on Self-Refresh exit. */
#define ZQ_PDEXITEN 			0	/* Writing a 1 enables the issuing of ZQCL on Active Power-Down, and Precharge Power-Down exit. */
#define ZQ_ZQINIT_MULT 			1	/* Indicates the number of ZQCL durations that make up a ZQINIT duration, minus one. */
#define ZQ_ZQCL_MULT 			3	/* Indicates the number of ZQCS intervals that make up a ZQCL duration, minus one.
									   ZQCS interval is defined by ZQ_ZQCS inEMIF_SDRAM_TIMING_3: see T_SDRAM_TIM3[T_ZQ_ZQCS] */
#define ZQ_REFINTERVAL 			0x190B	/* Number of refresh periods between ZQCS commands */

#define ZQ_CONFIG				(ZQ_CS1EN << 31) | (ZQ_CS0EN << 30) | (ZQ_DUALCALEN << 29) | \
								(ZQ_SFEXITEN << 28) | (ZQ_PDEXITEN << 27) | (ZQ_ZQINIT_MULT << 18) | \
								(ZQ_ZQCL_MULT << 16) | (ZQ_REFINTERVAL)

/* Register emif_ddr_phy_ctlr_1_init (init) = EMIF_DDR_PHY_CONTROL_1 (0x4C0000E4) */
#define RDLVL_MASK_INIT 			0	/* Writing a 1 to this field will mask read data eye training during full leveling command,
										   plus drives reg_phy_use_rd_data_eye_level control low to allow user to use programmed ratio values.
										   Incremental training needs to be disabled using incremental training registers. */
#define RDLVLGATE_MASK_INIT 		0	/* Writing a 1 to this field will mask dqs gate training during full leveling command,
										   plus drives reg_phy_use_rd_dqs_level control low to allow user to use programmed ratio values.
										   Incremental training needs to be disabled using incremental training registers. */
#define WRLVL_MASK_INIT 			0	/* Writing a 1 to this field will mask write leveling training during full leveling command,
										   plus drives reg_phy_use_wr_level control low to allow user to use programmed ratio values.
										   Incremental training needs to be disabled using incremental training registers. */
#define PHY_HALF_DELAYS_INIT 		1	/* Adjust slave delay line delays to support 2×
										   mode 1: 2× mode (MDLL clock is half the rate of PHY) - OPP_NOM
										   mode 0: 1× mode (MDLL clock rate is same as PHY) - OPP_BOOT */
#define PHY_CLK_STALL_LEVEL_INIT 	1	/* Enable variable idle value for delay lines. Enable during normal operations to avoid differential aging in the delay lines. */
#define PHY_DIS_CALIB_RST_INIT 		0	/* Disable the dll_calib (internally generated) signal from resetting the Read Capture FIFO pointers and portions of data PHYs. Debug only. */
#define PHY_INVERT_CLKOUT_INIT		0	/* Inverts the polarity of DRAM clock. This bit must be set to 0 when in LDDDR2 mode.
										   0: core clock is passed on to DRAM
										   1: inverted core clock is passed on to DRAM */
#define PHY_DLL_LOCK_DIFF_INIT		0x10	/* The maximum number of delay line taps variation while maintaining the master DLL lock.
											   When the PHY is in locked state and the variation on the clock exceeds the variation indicated by this field,
											   the lock signal is de-asserted and a dll_calib signal is generated.
											   To prevent the dll_calib signal from being asserted in the middle of traffic when the clock jitter exceeds the variation,
											   this register needs to be set to a value which will ensure that the lock will not be lost. Recommended value is 16. */
#define PHY_FAST_DLL_LOCK_INIT 		0		/* Controls master DLL to lock fast or average logic must be part of locking process.
											   Set to 1 before OPP transition commences, and set back to 0 after OPP transition completes.
											   1: MDLL lock is asserted based on single sample
											   0: MDLL lock is asserted based on average of 16 samples. */
#define READ_LATENCY_INIT 			0x0A	/* This field defines the read latency for the read data from SDRAM in number of DDR clock cycles
											   This field is used by the EMIF as well as the PHY. READ_LATENCY = RL + reg_phy_rdc_we_to_re -1.
											   EMIF uses above equation to calculate reg_phy_rdc_we_to_re and forward it to the PHY.
											   For DDR3, the true RL is used, not the decoded value. See JEDEC spec. */


#define EMIF_DDR_PHY_CONTROL_1_INIT		(RDLVL_MASK_INIT << 27) | (RDLVLGATE_MASK_INIT << 26) | (WRLVL_MASK_INIT << 25) | \
										(PHY_HALF_DELAYS_INIT << 21) | (PHY_CLK_STALL_LEVEL_INIT << 20) | (PHY_DIS_CALIB_RST_INIT << 19) | \
										(PHY_INVERT_CLKOUT_INIT << 18) | (PHY_DLL_LOCK_DIFF_INIT << 10) | (PHY_FAST_DLL_LOCK_INIT << 9) | \
										(READ_LATENCY_INIT)

/* Register emif_ddr_phy_ctlr_1 (init) = EMIF_DDR_PHY_CONTROL_1 (0x4C0000E4) */
#define RDLVL_MASK		 			0	/* Writing a 1 to this field will mask read data eye training during full leveling command,
										   plus drives reg_phy_use_rd_data_eye_level control low to allow user to use programmed ratio values.
										   Incremental training needs to be disabled using incremental training registers. */
#define RDLVLGATE_MASK		 		0	/* Writing a 1 to this field will mask dqs gate training during full leveling command,
										   plus drives reg_phy_use_rd_dqs_level control low to allow user to use programmed ratio values.
										   Incremental training needs to be disabled using incremental training registers. */
#define WRLVL_MASK		 			0	/* Writing a 1 to this field will mask write leveling training during full leveling command,
										   plus drives reg_phy_use_wr_level control low to allow user to use programmed ratio values.
										   Incremental training needs to be disabled using incremental training registers. */
#define PHY_HALF_DELAYS		 		1	/* Adjust slave delay line delays to support 2×
										   mode 1: 2× mode (MDLL clock is half the rate of PHY) - OPP_NOM
										   mode 0: 1× mode (MDLL clock rate is same as PHY) - OPP_BOOT */
#define PHY_CLK_STALL_LEVEL		 	1	/* Enable variable idle value for delay lines. Enable during normal operations to avoid differential aging in the delay lines. */
#define PHY_DIS_CALIB_RST	 		0	/* Disable the dll_calib (internally generated) signal from resetting the Read Capture FIFO pointers and portions of data PHYs. Debug only. */
#define PHY_INVERT_CLKOUT			1	/* Inverts the polarity of DRAM clock. This bit must be set to 0 when in LDDDR2 mode.
										   0: core clock is passed on to DRAM
										   1: inverted core clock is passed on to DRAM */
#define PHY_DLL_LOCK_DIFF			0x10	/* The maximum number of delay line taps variation while maintaining the master DLL lock.
											   When the PHY is in locked state and the variation on the clock exceeds the variation indicated by this field,
											   the lock signal is de-asserted and a dll_calib signal is generated.
											   To prevent the dll_calib signal from being asserted in the middle of traffic when the clock jitter exceeds the variation,
											   this register needs to be set to a value which will ensure that the lock will not be lost. Recommended value is 16. */
#define PHY_FAST_DLL_LOCK	 		0		/* Controls master DLL to lock fast or average logic must be part of locking process.
											   Set to 1 before OPP transition commences, and set back to 0 after OPP transition completes.
											   1: MDLL lock is asserted based on single sample
											   0: MDLL lock is asserted based on average of 16 samples. */
#define READ_LATENCY	 			0x0A	/* This field defines the read latency for the read data from SDRAM in number of DDR clock cycles
											   This field is used by the EMIF as well as the PHY. READ_LATENCY = RL + reg_phy_rdc_we_to_re -1.
											   EMIF uses above equation to calculate reg_phy_rdc_we_to_re and forward it to the PHY.
											   For DDR3, the true RL is used, not the decoded value. See JEDEC spec. */


#define EMIF_DDR_PHY_CONTROL_1	(RDLVL_MASK << 27) | (RDLVLGATE_MASK << 26) | (WRLVL_MASK << 25) | \
								(PHY_HALF_DELAYS << 21) | (PHY_CLK_STALL_LEVEL << 20) | (PHY_DIS_CALIB_RST << 19) | \
								(PHY_INVERT_CLKOUT << 18) | (PHY_DLL_LOCK_DIFF << 10) | (PHY_FAST_DLL_LOCK << 9) | \
								(READ_LATENCY)




/*  DMM memory mapping register
    DMM_LISA_MAP_i (0x4E000040 + (0x4 * i)) where i[0..3]
    31:24 SYS_ADDR: DMM system section address MSB for view mapping i : RESET = 0x00
    23: RESERVED - Write 0
    22:20 SYS_SIZE: DMM system section size for view mapping i
        0x0: 16-MiB section <- RESET
        0x1: 32-MiB section
        0x2: 64-MiB section
        0x3: 128-MiB section
        0x4: 256-MiB section
        0x5: 512-MiB section
        0x6: 1-GiB section
        0x7: 2-GiB section
    19:18 SDRC_INTL: SDRAM controller interleaving mode
        0x0: No interleaving  <- RESET
        0x1: 128-byte interleaving
        0x2: 256-byte interleaving
        0x3: 512-byte interleaving
        The 128-/256-/512-byte interleaving applies only to
        nontiled regions. If accesses are made to tiled regions,
        interleaving is forced to 1kiB. SDRC_INTL is don't care if
        SDRC_MAP is not 0x3 (no interleaving)
    17:16 SDRC_ADDRSPC: SDRAM controller address space for view mapping i (RESET = 0)
    15:10 RESEVED - Write 0
    9:8 SDRC_MAP: SDRAM controller mapping for view mapping i
        0x0: Unmapped <- RESET
        0x1: Mapped on EMIF1 only (not interleaved)
        0x2: Mapped on EMIF2 only (not interleaved)
        0x3: Mapped on EMIF1 and EMIF2 (interleaved)
        To enable interleaving, SDRC_MAP must be 0x3 and
        SDRC_INTL must be a nonzero value.
    7:0 SDRC_ADDR SDRAM controller address MSB for view mapping i (RESET = 0)
*/

#define LISA_MAP_0_SYS_ADDR 		0
#define LISA_MAP_0_SYS_SIZE			0
#define LISA_MAP_0_SDRC_INTL		0
#define LISA_MAP_0_SDRC_ADDRSPC		0
#define LISA_MAP_0_SDRC_MAP 		0
#define LISA_MAP_0_SDRC_ADDR 		0x00
#define DMM_LISA_MAP0				(LISA_MAP_0_SYS_ADDR << 24) | (LISA_MAP_0_SYS_SIZE << 20) | (LISA_MAP_0_SDRC_INTL << 18) | \
									(LISA_MAP_0_SDRC_ADDRSPC << 16) | (LISA_MAP_0_SDRC_MAP << 8) | LISA_MAP_0_SDRC_ADDR

#define LISA_MAP_1_SYS_ADDR 		0
#define LISA_MAP_1_SYS_SIZE			0
#define LISA_MAP_1_SDRC_INTL		0
#define LISA_MAP_1_SDRC_ADDRSPC		0
#define LISA_MAP_1_SDRC_MAP 		0
#define LISA_MAP_1_SDRC_ADDR 		0x00
#define DMM_LISA_MAP1				(LISA_MAP_1_SYS_ADDR << 24) | (LISA_MAP_1_SYS_SIZE << 20) | (LISA_MAP_1_SDRC_INTL << 18) | \
									(LISA_MAP_1_SDRC_ADDRSPC << 16) | (LISA_MAP_1_SDRC_MAP << 8) | LISA_MAP_1_SDRC_ADDR

#define LISA_MAP_2_SYS_ADDR 		0x80    /* SYS_ADDR: 0x80000000*/
#define LISA_MAP_2_SYS_SIZE		7       /* SYS_SIZE: 0x7: 2-GiB section */
#define LISA_MAP_2_SDRC_INTL		3       /* SDRC_INTL: 0x1: 128-byte interleaving */
#define LISA_MAP_2_SDRC_ADDRSPC		0       /* SDRC_ADDRSPC = 0x00000000 */
#define LISA_MAP_2_SDRC_MAP 		3       /* SDRC_MAP: 0x3: Mapped on EMIF1 and EMIF2 (interleaved) */
#define LISA_MAP_2_SDRC_ADDR 		0x00    /* SDRC_ADDR = 0x00000000 */

#define DMM_LISA_MAP2				(LISA_MAP_2_SYS_ADDR << 24) | (LISA_MAP_2_SYS_SIZE << 20) | (LISA_MAP_2_SDRC_INTL << 18) | \
									(LISA_MAP_2_SDRC_ADDRSPC << 16) | (LISA_MAP_2_SDRC_MAP << 8) | LISA_MAP_2_SDRC_ADDR

#define LISA_MAP_2_SYS_ADDR_1G 		0x80    /* SYS_ADDR: 0x80000000*/
#define LISA_MAP_2_SYS_SIZE_1G		6       /* SYS_SIZE: 0x6: 1-GiB section */
#define LISA_MAP_2_SDRC_INTL_1G		0       /* SDRC_INTL: 0x1: 128-byte interleaving */
#define LISA_MAP_2_SDRC_ADDRSPC_1G	0       /* SDRC_ADDRSPC = 0x00000000 */
#define LISA_MAP_2_SDRC_MAP_1G 		1       /* SDRC_MAP: 0x1: Mapped on EMIF1 (no interleaved) */
#define LISA_MAP_2_SDRC_ADDR_1G		0x00    /* SDRC_ADDR = 0x00000000 */

#define DMM_LISA_MAP2_1G			(LISA_MAP_2_SYS_ADDR_1G << 24) | (LISA_MAP_2_SYS_SIZE_1G << 20) | (LISA_MAP_2_SDRC_INTL_1G << 18) | \
									(LISA_MAP_2_SDRC_ADDRSPC_1G << 16) | (LISA_MAP_2_SDRC_MAP_1G << 8) | LISA_MAP_2_SDRC_ADDR_1G


#define LISA_MAP_3_SYS_ADDR 		0xFF    /* SYS_ADDR: 0xFF000000*/
#define LISA_MAP_3_SYS_SIZE		0       /* SYS_SIZE: 0x0: 16-MiB section */
#define LISA_MAP_3_SDRC_INTL		0       /* SDRC_INTL: 0x0: No interleaving */
#define LISA_MAP_3_SDRC_ADDRSPC		2
#define LISA_MAP_3_SDRC_MAP 		1       /* SDRC_MAP: 0x3: Mapped on EMIF1 and EMIF2 (interleaved) */
#define LISA_MAP_3_SDRC_ADDR 		0x00    /* SDRC_ADDR = 0x00000000 */

#define DMM_LISA_MAP3				(LISA_MAP_3_SYS_ADDR << 24) | (LISA_MAP_3_SYS_SIZE << 20) | (LISA_MAP_3_SDRC_INTL << 18) | \
									(LISA_MAP_3_SDRC_ADDRSPC << 16) | (LISA_MAP_3_SDRC_MAP << 8) | LISA_MAP_3_SDRC_ADDR

#endif
