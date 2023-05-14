LICENSE_FLAGS = ""

PACKAGECONFIG:remove = "x11"

# MobiAqua: added optimisations
FULL_OPTIMIZATION:append = " -fexpensive-optimizations -O4 -ffast-math"
FULL_OPTIMIZATION:append:panda = " -mvectorize-with-neon-quad"
FULL_OPTIMIZATION:append:beagle = " -mvectorize-with-neon-quad"
