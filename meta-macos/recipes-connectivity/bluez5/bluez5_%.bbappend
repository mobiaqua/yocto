GI_DATA_ENABLED = "False"

PACKAGECONFIG:remove = "obex-profiles"

RDEPENDS:${PN}-testtools:remove = "python3-dbus python3-core"
PACKAGES:remove = "${PN}-testtools"
FILES:${PN}-testtools = ""

INSANE_SKIP:${PN} += "installed-vs-shipped"
