#!/bin/sh

[ -e /sbin/modprobe ] || exit 1

LOAD_MODULE=/sbin/modprobe

[ "$VERBOSE" != no ] && echo -n "Loading OMAP4 Remote Proc and DCE modules: "

$LOAD_MODULE -q omap_remoteproc
$LOAD_MODULE -q omapdce
