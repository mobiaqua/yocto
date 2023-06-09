SUMMARY = "A complete, cross-platform solution to record, convert and stream audio and video."
DESCRIPTION = "FFmpeg is the leading multimedia framework, able to decode, encode, transcode, \
               mux, demux, stream, filter and play pretty much anything that humans and machines \
               have created. It supports the most obscure ancient formats up to the cutting edge."
HOMEPAGE = "https://www.ffmpeg.org/"
SECTION = "libs"

LICENSE = "GPL-2.0-or-later & LGPL-2.1-or-later & ISC & MIT & BSD-2-Clause & BSD-3-Clause & IJG"
LICENSE:${PN} = "GPL-2.0-or-later"
LICENSE:libavcodec = "GPL-2.0-or-later"
LICENSE:libavdevice = "GPL-2.0-or-later"
LICENSE:libavfilter = "GPL-2.0-or-later"
LICENSE:libavformat = "GPL-2.0-or-later"
LICENSE:libavutil = "GPL-2.0-or-later"
LICENSE:libpostproc = "GPL-2.0-or-later"
LICENSE:libswresample = "GPL-2.0-or-later"
LICENSE:libswscale = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004 \
                    file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

# MobiAqua: custom ffpmeg
DEFAULT_PREFERENCE = "99"

# MobiAqua: added Intel HW acceleration and nasm
DEPENDS:nuc = "nasm-native libva intel-media-driver"

SRCREV = "27205c0b476a1095bc38759ad9df001e799e4843"

PV = "6.0+git${SRCPV}"
PR = "r1"

SRC_URI = "git://source.ffmpeg.org/ffmpeg.git;branch=release/6.0 \
          "

S = "${WORKDIR}/git"

# Should be API compatible with libav (which was a fork of ffmpeg)
# libpostproc was previously packaged from a separate recipe
PROVIDES = "libav libpostproc"

inherit autotools pkgconfig

def cpu(d):
    for arg in (d.getVar('TUNE_CCARGS') or '').split():
        if arg.startswith('-mcpu='):
            return arg[6:]
    return 'generic'

FULL_OPTIMIZATION:append = " -fexpensive-optimizations -O4 -ffast-math"
FULL_OPTIMIZATION:append:panda = " -mvectorize-with-neon-quad"
FULL_OPTIMIZATION:append:beagle = " -mvectorize-with-neon-quad"

EXTRA_FFCONF ?= ""
# MobiAqua: added Intel HW acceleration
EXTRA_FFCONF:append:nuc = " \
--enable-vaapi \
--enable-hwaccel=av1_vaapi,h264_vaapi,hevc_vaapi,mpeg2_vaapi,mpeg4_vaapi,vp8_vaapi,vp9_vaapi,wmv3_vaapi,mjpeg_vaapi \
"

EXTRA_OECONF = " \
        --enable-shared \
        --enable-pthreads \
        --enable-gpl \
        \
        --cross-prefix=${TARGET_PREFIX} \
        --prefix=${prefix} \
        \
        --disable-all \
        --disable-bzlib \
        --enable-avcodec \
        --enable-avdevice \
        --enable-avformat \
        --enable-avutil \
        --enable-swscale \
        --enable-swresample \
        --enable-avfilter \
        --enable-protocol=file \
        --enable-bsf=mov2textsub,h264_mp4toannexb,hevc_mp4toannexb,mpeg4_unpack_bframes \
        --enable-demuxer=matroska,mov,flac,mp3,wav,mpegps,mpegts,avi,m4v,mpegvideo,asf,flv,rm,rtmp,swf,srt,ass \
        --enable-parser=aac,ac3,dca,flac,h261,h263,h264,hevc,mjpeg,mpegaudio,mpegvideo,mpeg4video,rv30,rv40,vc1,vorbis,\
vp3,vp8,vp9\
        --enable-decoder=aac,ac3,aic,eac3,dca,alac,als,flac,flv,h261,h263,h263i,h263p,h264,hevc,mlp,mp1,mp1float,mp2,mp2float,\
mp3,mp3adu,mp3adufloat,mp3float,mp3on4,mp3on4float,mpegvideo,mpeg1video,mpeg2video,mpeg4,msmpeg4v1,msmpeg4v2,msmpeg4v3,mjpeg,\
ffv1,dvvideo,pcm_mulaw,adpcm_ima_qt,ralf,rv10,rv20,rv30,rv40,svq1,sqv3,truehd,vc1,vorbis,vp3,vp5,vp6,vp6a,vp6f,vp7,vp8,vp9,webp,wmapro,wmav1,wmav2,\
wmalossless,wmv1,wmv2,wmv3,theora,ra_144,ra_288,pcm_u16le,pcm_u16be,pcm_s16le,pcm_s16be,pcm_u24le,pcm_u24be,pcm_s24le,\
pcm_s24be,pcm_u32le,pcm_u32be,pcm_s32le,pcm_s32be,pcm_bluray,pcm_dvd \
        --ld="${CCLD}" \
        --cc="${CC}" \
        --cxx="${CXX}" \
        --arch=${TARGET_ARCH} \
        --cpu=${@cpu(d)} \
        --target-os="linux" \
        --enable-cross-compile \
        --extra-cflags="${CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}" \
        --extra-ldflags="${LDFLAGS}" \
        --sysroot="${STAGING_DIR_TARGET}" \
        --libdir=${libdir} \
        --shlibdir=${libdir} \
        --datadir=${datadir}/ffmpeg \
        --pkg-config=pkg-config \
        --enable-hardcoded-tables \
        ${EXTRA_FFCONF} \
"

EXTRA_OEMAKE = "V=1"

do_configure() {
    ${S}/configure ${EXTRA_OECONF}
}

# patch out build host paths for reproducibility
do_compile:prepend:class-target() {
        sed -i -e "s,${WORKDIR},,g" ${B}/config.h
}

PACKAGES =+ "libavcodec \
             libavdevice \
             libavfilter \
             libavformat \
             libavutil \
             libpostproc \
             libswresample \
             libswscale"

FILES:libavcodec = "${libdir}/libavcodec${SOLIBS}"
FILES:libavdevice = "${libdir}/libavdevice${SOLIBS}"
FILES:libavfilter = "${libdir}/libavfilter${SOLIBS}"
FILES:libavformat = "${libdir}/libavformat${SOLIBS}"
FILES:libavutil = "${libdir}/libavutil${SOLIBS}"
FILES:libpostproc = "${libdir}/libpostproc${SOLIBS}"
FILES:libswresample = "${libdir}/libswresample${SOLIBS}"
FILES:libswscale = "${libdir}/libswscale${SOLIBS}"

INSANE_SKIP:${PN} += "already-stripped installed-vs-shipped"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
