DEPENDS:append:panda = " libdce virtual/libgbm virtual/egl virtual/libgles2"
DEPENDS:append:beagle = " libdce virtual/libgbm virtual/egl virtual/libgles2"

FULL_OPTIMIZATION:append:panda = " -mvectorize-with-neon-quad"
FULL_OPTIMIZATION:append:beagle = " -mvectorize-with-neon-quad"

EXTRA_OECONF:append:panda = " --drm-omap=yes --omap-dce=yes"
EXTRA_OECONF:append:beagle = " --drm-omap=yes --omap-dce=yes"
