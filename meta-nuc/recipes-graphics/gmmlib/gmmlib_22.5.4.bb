SUMMARY = "Intel(R) Graphics Memory Management Library"
DESCRIPTION = "The Intel(R) Graphics Memory Management Library provides \
device specific and buffer management for the Intel(R) Graphics \
Compute Runtime for OpenCL(TM) and the Intel(R) Media Driver for VAAPI."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=465fe90caea3edd6a2cecb3f0c28a654"

SRC_URI = " \
            git://github.com/intel/gmmlib.git;protocol=https;branch=master \
            "

SRCREV = "31ee7e6d48a98a2cb8afb87f0d4f2afc97fe06a0"

S = "${WORKDIR}/git"

COMPATIBLE_HOST:x86-x32 = "null"

UPSTREAM_CHECK_GITTAGREGEX = "^intel-gmmlib-(?P<pver>(\d+(\.\d+)+))$"

inherit pkgconfig cmake

EXTRA_OECMAKE += "-DRUN_TEST_SUITE=OFF"

BBCLASSEXTEND = "native nativesdk"