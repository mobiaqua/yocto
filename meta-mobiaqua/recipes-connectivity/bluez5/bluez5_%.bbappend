GI_DATA_ENABLED = "False"

PACKAGECONFIG_remove = "obex-profiles"

RDEPENDS_${PN}-testtools_remove = "python3-dbus python3-core"
PACKAGES_remove = "${PN}-testtools"
FILES_${PN}-testtools = ""

ERROR_QA_remove = "installed-vs-shipped"
