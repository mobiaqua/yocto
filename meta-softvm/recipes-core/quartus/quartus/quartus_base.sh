#!/bin/sh

CMD=`basename $0`

HOME_PATH=~/.softvm
if [ ! -f $HOME_PATH/config ]; then
    echo "$CMD : Missing confgiration file! Aborting..."
    exit 1
fi
. $HOME_PATH/config


SCRIPT_BASE=`dirname $0`

if [ "$SCRIPT_BASE" = "." ] || [ -z "$SCRIPT_BASE" ]; then
    QUARTUS_ROOT=`dirname \`pwd\``
else
    QUARTUS_ROOT=`dirname ${SCRIPT_BASE}`
fi

if [ "$QUARTUS_ROOT" = ".." ]; then
    QUARTUS_ROOT=`dirname \`pwd\``
fi


if [ ! -d ${QUARTUS_ROOT}/tools ]; then
    mkdir -p ${QUARTUS_ROOT}/tools
fi


CMD=`basename $0`
VMMODE=native

export NFS_WORKSPACE
export SOFTVM_QEMU_ARGS="-device qemu-xhci,id=usb-controller-0 \
-device usb-host,vendorid=0x09fb,productid=0x6001 \
-device usb-host,vendorid=0x09fb,productid=0x6002 \
-device usb-host,vendorid=0x09fb,productid=0x6003 \
-device usb-host,vendorid=0x09fb,productid=0x6010 \
-device usb-host,vendorid=0x09fb,productid=0x6810"
export SOFTVM_QEMU_RAM=$((8*1024))

${SOFTVM_INSTALL_PATH}/tools/semihost_cmd_qemu.sh ${QUARTUS_ROOT}/tools $VMMODE "/opt/tools/altera/quartus/bin/$CMD $@ --64bit"
exit $?
