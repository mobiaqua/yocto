DEPENDS:remove = "libsndfile1"

# MobiAqua: added optimisations
FULL_OPTIMIZATION:append = " -fexpensive-optimizations -mvectorize-with-neon-quad -O4 -ffast-math"
