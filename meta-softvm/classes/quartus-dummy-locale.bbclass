mobiaqua_dummy_locale() {
	echo "#!/bin/sh

echo \"C\"
" > ${IMAGE_ROOTFS}/usr/bin/locale
	chmod +x ${IMAGE_ROOTFS}/usr/bin/locale
}
