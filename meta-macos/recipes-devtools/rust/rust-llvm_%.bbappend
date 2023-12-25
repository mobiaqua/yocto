FILESEXTRAPATHS:append := "${THISDIR}/${PN}:"

SRC_URI += "file://wa_uuid_macos.patch;striplevel=2 \
            file://fix_linker_macos.patch;striplevel=2 \
           "

EXTRA_OECMAKE:append = " -DBUILD_SHARED_LIBS=OFF \
                         -DLLVM_BUILD_LLVM_DYLIB=OFF \
                         -DLLVM_LINK_LLVM_DYLIB=OFF \
                         -DLLVM_ENABLE_SHARED=OFF \
                         -DLLVM_ENABLE_MODULES=OFF \
                         -DLLVM_ENABLE_PLUGINS=OFF"
