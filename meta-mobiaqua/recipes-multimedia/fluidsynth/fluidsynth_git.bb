require ${BPN}.inc

DEPENDS = "${BPN}-native alsa-lib ncurses glib-2.0"

SRC_URI += " \
    file://0001-Do-not-build-gentables-helper-we-have-to-use-native-.patch \
    file://0002-fluid_synth_nwrite_float-Allow-zero-pointer-for-left.patch \
    file://0003-Use-ARM-NEON-accelaration-for-float-multithreaded-se.patch \
"

# MobiAqua: added "-DCMAKE_SKIP_BUILD_RPATH=TRUE"
EXTRA_OECMAKE = "-Denable-floats=ON -DLIB_SUFFIX=${@d.getVar('baselib').replace('lib', '')} -DCMAKE_SKIP_BUILD_RPATH=TRUE"

do_configure:append() {
    make_tables.exe ${B}/
}

# MobiAqua: remove pulseadio
PACKAGECONFIG ??= ""
PACKAGECONFIG[sndfile] = "-Denable-libsndfile=ON,-Denable-libsndfile=OFF,libsndfile1"
PACKAGECONFIG[jack] = "-Denable-jack=ON,-Denable-jack=OFF,jack"
PACKAGECONFIG[pulseaudio] = "-Denable-pulseaudio=ON,-Denable-pulseaudio=OFF,pulseaudio"
PACKAGECONFIG[portaudio] = "-Denable-portaudio=ON,-Denable-portaudio=OFF,portaudio-v19"
PACKAGECONFIG[profiling] = "-Denable-profiling=ON,-Denable-profiling=OFF"
PACKAGECONFIG[readline] = "-Denable-readline=ON,-Denable-readline=OFF,readline"
