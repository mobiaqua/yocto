DESCRIPTION = "External SDK."
SECTION = "devtools"
PRIORITY = "optional"
LICENSE = ""
ERROR_QA_remove = "license-checksum"

DEPENDS = "ffmpeg zlib freetype fontconfig alsa-lib libmpg123 ncurses gdb-cross-arm \
libsdl2 libsdl2-net virtual/egl libvorbis libogg curl libmad mpeg2dec flac \
libjpeg-turbo libpng libtheora faad2 fluidsynth readline"
DEPENDS_append_board-tv = " libdce libdrm libgbm"

do_configure() {
    :
}

do_compile[noexec] = "1"
do_install[noexec] = "1"
