# MobiAqua: added Intel HW acceleration and nasm
DEPENDS:append:nuc = " nasm-native libva intel-media-driver"

# MobiAqua: added Intel HW acceleration
EXTRA_FFCONF:append:nuc = " \
--enable-vaapi \
--enable-hwaccel=av1_vaapi,h264_vaapi,hevc_vaapi,mpeg2_vaapi,vp9_vaapi,mjpeg_vaapi \
"
