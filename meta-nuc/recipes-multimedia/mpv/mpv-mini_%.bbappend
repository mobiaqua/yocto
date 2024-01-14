DEPENDS:append:nuc = " virtual/libgl libva virtual/egl virtual/libgbm"

EXTRA_OECONF:append:nuc = " --gl=yes --dmabuf-gl=yes --egl-drm=yes --vaapi=yes --vaapi-drm=yes --vaapi-egl=yes"
