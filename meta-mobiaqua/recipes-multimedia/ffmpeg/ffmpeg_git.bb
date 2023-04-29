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

inherit autotools pkgconfig

LEAD_SONAME = "libavcodec.so"

PACKAGES += "${PN}-vhook-dbg ${PN}-vhook"

FILES:${PN} = "${bindir}"
FILES:${PN}-dev = "${includedir}/${PN}"

FILES:${PN}-vhook = "${libdir}/vhook"
FILES:${PN}-vhook-dbg += "${libdir}/vhook/.debug"

PACKAGES += "libav-x264-presets \
             libavcodec  libavcodec-dev  libavcodec-dbg \
             libavdevice libavdevice-dev libavdevice-dbg \
             libavformat libavformat-dev libavformat-dbg \
             libavutil   libavutil-dev   libavutil-dbg \
             libpostproc libpostproc-dev libpostproc-dbg \
             libswscale  libswscale-dev  libswscale-dbg \
             libswresample  libswresample-dev  libswresample-dbg \
             libavfilter libavfilter-dev libavfilter-dbg \
            "

FILES:libav-x264-presets = "${datadir}/*.ffpreset"

FILES:${PN}-dev = "${includedir}"
FILES:libavcodec = "${libdir}/libavcodec*.so.*"
FILES:libavcodec-dev = "${libdir}/libavcodec*.so ${libdir}/pkgconfig/libavcodec.pc ${libdir}/libavcodec*.a"
FILES:libavcodec-dbg += "${libdir}/.debug/libavcodec*"

FILES:libavdevice = "${libdir}/libavdevice*.so.*"
FILES:libavdevice-dev = "${libdir}/libavdevice*.so ${libdir}/pkgconfig/libavdevice.pc ${libdir}/libavdevice*.a"
FILES:libavdevice-dbg += "${libdir}/.debug/libavdevice*"

FILES:libavformat = "${libdir}/libavformat*.so.*"
FILES:libavformat-dev = "${libdir}/libavformat*.so ${libdir}/pkgconfig/libavformat.pc ${libdir}/libavformat*.a"
FILES:libavformat-dbg += "${libdir}/.debug/libavformat*"

FILES:libavutil = "${libdir}/libavutil*.so.*"
FILES:libavutil-dev = "${libdir}/libavutil*.so ${libdir}/pkgconfig/libavutil.pc ${libdir}/libavutil*.a"
FILES:libavutil-dbg += "${libdir}/.debug/libavutil*"

FILES:libpostproc = "${libdir}/libpostproc*.so.*"
FILES:libpostproc-dev = "${libdir}/libpostproc*.so  ${libdir}/pkgconfig/libpostproc.pc ${libdir}/libpostproc*.a ${includedir}/postproc"
FILES:libpostproc-dbg += "${libdir}/.debug/libpostproc*"

FILES:libswscale = "${libdir}/libswscale*.so.*"
FILES:libswscale-dev = "${libdir}/libswscale*.so ${libdir}/pkgconfig/libswscale.pc ${libdir}/libswscale*.a"
FILES:libswscale-dbg += "${libdir}/.debug/libswscale*"

FILES:libswresample = "${libdir}/libswresample*.so.*"
FILES:libswresample-dev = "${libdir}/libswresample*.so ${libdir}/pkgconfig/libswresample.pc ${libdir}/libswresample*.a"
FILES:libswresample-dbg += "${libdir}/.debug/libswresample*"

FILES:libavfilter = "${libdir}/libavfilter*.so.*"
FILES:libavfilter-dev = "${libdir}/libavfilter*.so ${libdir}/pkgconfig/libavfilter.pc ${libdir}/libavfilter*.a"
FILES:libavfilter-dbg += "${libdir}/.debug/libavfilter*"

SRCREV = "27205c0b476a1095bc38759ad9df001e799e4843"

PV = "6.0+git${SRCPV}"
PR = "r1"

SRC_URI = "git://source.ffmpeg.org/ffmpeg.git;branch=release/6.0 \
          "

S = "${WORKDIR}/git"

def cpu(d):
    for arg in (d.getVar('TUNE_CCARGS') or '').split():
        if arg.startswith('-mcpu='):
            return arg[6:]
    return 'generic'

FULL_OPTIMIZATION:append = " -fexpensive-optimizations -mvectorize-with-neon-quad -O4 -ffast-math"

EXTRA_FFCONF ?= ""

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

INSANE_SKIP:${PN} += "already-stripped installed-vs-shipped"

DEBUG_BUILD = "${@['no','yes'][d.getVar('BUILD_DEBUG') == '1']}"

RM_WORK_EXCLUDE += "${@['','${PN}'][d.getVar('BUILD_DEBUG') == '1']}"
