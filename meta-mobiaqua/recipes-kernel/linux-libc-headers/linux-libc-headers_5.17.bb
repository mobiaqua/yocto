require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS = "unifdef-native bison-native"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-ti-5.10:"

SRC_URI += "\
            file://rpmsg_rpc.h.patch \
            file://rpmsg_socket.h.patch \
            file://drm-omap-add-OMAP_BO-flags-to-affect-b.patch \
           "

SRC_URI[sha256sum] = "555fef61dddb591a83d62dd04e252792f9af4ba9ef14683f64840e46fa20b1b1"

do_install_armmultilib () {
	oe_multilib_header asm/auxvec.h asm/bitsperlong.h asm/byteorder.h asm/fcntl.h asm/hwcap.h asm/ioctls.h asm/mman.h asm/param.h asm/perf_regs.h asm/bpf_perf_event.h
	oe_multilib_header asm/posix_types.h asm/ptrace.h asm/setup.h asm/sigcontext.h asm/siginfo.h asm/signal.h asm/stat.h asm/statfs.h asm/swab.h asm/types.h asm/unistd.h
}
