DESCRIPTION = "External SDK."
SECTION = "devtools"
PRIORITY = "optional"
LICENSE = ""
ERROR_QA:remove = "license-checksum"

DEPENDS = "ffmpeg zlib freetype fontconfig alsa-lib ncurses \
libsdl2 libsdl2-net virtual/libgbm virtual/egl virtual/libgles2 libvorbis libogg zlib curl libmad \
mpeg2dec flac libjpeg-turbo libpng libtheora faad2 fluidsynth readline liba52 libmpg123 libass lua"

do_configure() {
    :
}

do_compile[noexec] = "1"
do_install[noexec] = "1"
