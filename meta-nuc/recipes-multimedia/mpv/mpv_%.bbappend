DEPENDS:append:nuc = " virtual/libgl libva"

EXTRA_OECONF:append:nuc = " --enable-gl --enable-vaapi --enable-vaapi-drm"
