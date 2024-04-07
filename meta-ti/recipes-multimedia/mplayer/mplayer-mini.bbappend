DEPENDS:append:panda = " libdce libdrm virtual/libgbm virtual/egl virtual/libgles2"
DEPENDS:append:beagle = " libdce libdrm virtual/libgbm virtual/egl virtual/libgles2"
DEPENDS:append:beagle64 = " libdrm virtual/libgbm virtual/egl virtual/libgles2"

FULL_OPTIMIZATION:append:panda = " -mvectorize-with-neon-quad"
FULL_OPTIMIZATION:append:beagle = " -mvectorize-with-neon-quad"

EXTRA_OECONF:append:panda = " --omapdce=yes"
EXTRA_OECONF:append:beagle = " --omapdce=yes"
