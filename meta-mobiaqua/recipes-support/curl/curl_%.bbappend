PACKAGECONFIG:remove = "ipv6 gnutls"
PACKAGECONFIG:append = " openssl"

RRECOMMENDS:lib${BPN}:remove = "ca-certificates"
