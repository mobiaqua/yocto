#
# Copyright OpenEmbedded Contributors
#
# SPDX-License-Identifier: MIT
#

inherit python_pyo3 python_setuptools_build_meta

DEPENDS += "python3-setuptools-rust-native"

# MobiAqua: vendor as Apple for Rust
CARGO_BUILD_TARGET:darwin="${HOST_ARCH}-apple-${HOST_OS}"

python_setuptools3_rust_do_configure() {
    python_pyo3_do_configure
    cargo_common_do_configure
    python_pep517_do_configure
}

EXPORT_FUNCTIONS do_configure
