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

DEPENDS = "openssl"

SRCREV = "12682eba2ef6047f58cb34c07726126025b6e752"

PV = "7.1.0+git${SRCPV}"
PR = "r1"

SRC_URI = "git://source.ffmpeg.org/ffmpeg.git;branch=release/7.1 \
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

EXTRA_FFCONF ?= ""

EXTRA_OECONF = " \
        --enable-shared \
        --enable-pthreads \
        --enable-gpl \
        --enable-nonfree \
        \
        --cross-prefix=${TARGET_PREFIX} \
        --prefix=${prefix} \
        \
        --disable-all \
        --disable-bzlib \
        --enable-openssl \
        --enable-avcodec \
        --enable-avdevice \
        --enable-avformat \
        --enable-avutil \
        --enable-swscale \
        --enable-swresample \
        --enable-avfilter \
        --enable-protocol=file,http,https \
        --enable-bsf=mov2textsub,h264_mp4toannexb,hevc_mp4toannexb,mpeg4_unpack_bframes,mjpeg2jpeg_bsf,pgs_frame_merge_bsf,text2movsub_bsf,\
truehd_core_bsf,eac3_core_bsf \
        --enable-filter=fps \
        --enable-demuxer=matroska,mov,flac,mp3,dts,dtshd,ac3,eac3,wav,spdif,hdr,mpegps,mpegts,avi,m4v,mpegvideo,asf,flv,rm,rtmp,swf,srt,ass,vobsub,\
subviewer1,subviewer,mpsub,microdvd,sami \
        --enable-muxer=spdif,eac3,ac3,dts,truehd,hdr \
        --enable-parser=aac,ac3,dca,flac,av1,h261,h263,h264,hevc,mjpeg,mpegaudio,mpegvideo,mpeg4video,rv30,rv40,vc1,vorbis,\
vp3,vp8,vp9,dvdsub,hdr \
        --enable-decoder=aac,ac3,aic,eac3,dca,truehd,alac,als,flac,flv,h261,h263,h263i,h263p,h264,hevc,mlp,mp1,mp1float,mp2,mp2float,\
mp3,mp3adu,mp3adufloat,mp3float,mp3on4,mp3on4float,mpegvideo,mpeg1video,mpeg2video,mpeg4,msmpeg4v1,msmpeg4v2,msmpeg4v3,mjpeg,\
ffv1,dvvideo,pcm_mulaw,adpcm_ima_qt,ralf,rv10,rv20,rv30,rv40,svq1,sqv3,truehd,vc1,vorbis,vp3,vp5,vp6,vp6a,vp6f,vp7,vp8,vp9,webp,wmapro,wmav1,wmav2,\
wmalossless,wmv1,wmv2,wmv3,theora,ra_144,ra_288,pcm_u16le,pcm_u16be,pcm_s16le,pcm_s16be,pcm_u24le,pcm_u24be,pcm_s24le,\
pcm_s24be,pcm_u32le,pcm_u32be,pcm_s32le,pcm_s32be,pcm_bluray,pcm_dvd,ass,ccaption,dvdsub,microdvd,movtext,mpl2,pgssub,realtext,srt,subrip,\
subviewer,subviewer1,text,vplayer,xsub,hdr \
        --enable-encoder=eac3,ac3,truehd,hdr \
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
