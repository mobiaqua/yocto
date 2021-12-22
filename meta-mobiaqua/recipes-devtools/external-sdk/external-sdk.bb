DESCRIPTION = "External SDK."
SECTION = "devtools"
PRIORITY = "optional"
LICENSE = ""
ERROR_QA_remove = "license-checksum"

DEPENDS = "ffmpeg zlib freetype fontconfig alsa-lib libmpg123 ncurses gdb-cross-arm \
libsdl2 libsdl2-net virtual/egl virtual/libgles2 libvorbis libogg zlib curl libmad \
mpeg2dec flac libjpeg-turbo libpng libtheora faad2 fluidsynth readline liba52"
DEPENDS_append_board-tv = " libdce libdrm libgbm libmmrpc"

do_configure() {
    :
}

do_compile[noexec] = "1"
do_install[noexec] = "1"
