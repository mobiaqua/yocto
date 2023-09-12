INSTALL_PKGS += "\
                 alsa-utils-amixer alsa-utils-speakertest alsa-utils-alsactl alsa-utils-aplay \
                 e2fsprogs e2fsprogs-mke2fs dosfstools usbutils procps mc \
                 gdbserver gdb valgrind glibc-dbg openssh-sftp-server bluez-hcidump \
"

DEPENDS += "${DEPENDS_DEBUG_TOOLS}"
RDEPENDS += ""
RRECOMMENDS += ""
