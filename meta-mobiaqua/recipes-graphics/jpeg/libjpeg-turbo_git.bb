DESCRIPTION = "libjpeg-turbo is a derivative of libjpeg that uses SIMD instructions (MMX, SSE2, NEON) to accelerate baseline JPEG compression and decompression"
HOMEPAGE = "http://libjpeg-turbo.org/"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://cdjpeg.h;endline=13;md5=8184bcc7c4ac7b9edc6a7bc00f231d0b \
                    file://jpeglib.h;endline=16;md5=f67d70e547a2662c079781c72f877f72 \
                    file://djpeg.c;endline=11;md5=c59e19811c006cb38f82d6477134d314 \
"

# MobiAqua: older version without cmake
DEFAULT_PREFERENCE = "99"

PV = "8d+git-${SRCREV}"

SRCREV = "1309ccb485789b3ce8a7d8513b83d824a4922e28"

SRC_URI = "git://github.com/libjpeg-turbo/libjpeg-turbo.git;protocol=git;branch=1.5.x"

S = "${WORKDIR}/git"

# Drop-in replacement for jpeg
PROVIDES = "jpeg"
RPROVIDES_${PN} += "jpeg"
RREPLACES_${PN} += "jpeg"
RCONFLICTS_${PN} += "jpeg"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-jpeg8 "

PACKAGES =+ "jpeg-tools libturbojpeg"

DESCRIPTION_jpeg-tools = "The jpeg-tools package includes the client programs for access libjpeg functionality.  These tools allow for the compression, decompression, transformation and display of JPEG files."
FILES_jpeg-tools = "${bindir}/*"

FILES_libturbojpeg = "${libdir}/libturbojpeg.so"
INSANE_SKIP_libturbojpeg = "dev-so"

LEAD_SONAME = "libjpeg.so.8"
