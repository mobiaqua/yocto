LICENSE_FLAGS = ""

PACKAGECONFIG:remove = "x11"

# MobiAqua: added optimisations
FULL_OPTIMIZATION:append = " -fexpensive-optimizations -mvectorize-with-neon-quad -O4 -ffast-math"
