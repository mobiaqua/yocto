#!/bin/sh

CMD=`basename $0`

HOME_PATH=~/.softvm
if [ ! -f $HOME_PATH/config ]; then
    echo "$CMD : Missing confguration file! Aborting..."
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

grep "13.0" ${QUARTUS_ROOT}/tools/altera/quartus/readme.txt > /dev/null 2>&1
if [ $? = 0 ]; then MODE="--64bit"; fi

CMD=`basename $0`

export SOFTVM_WORKSPACE
export SOFTVM_CPU=2
export SOFTVM_RAM=$((8*1024))

if [ $CMD == "quartus_pgm" ]; then
    export SOFTVM_QEMU_ARGS="\
        -device qemu-xhci,id=usb-controller-0 \
        -device usb-host,vendorid=0x09fb,productid=0x6001 \
        -device usb-host,vendorid=0x09fb,productid=0x6002 \
        -device usb-host,vendorid=0x09fb,productid=0x6003 \
        -device usb-host,vendorid=0x09fb,productid=0x6010 \
        -device usb-host,vendorid=0x09fb,productid=0x6810"
    ${SOFTVM_INSTALL_PATH}/tools/semihost_cmd_qemu.sh ${QUARTUS_ROOT}/tools "/opt/tools/altera/quartus/bin/$CMD $@ $MODE"
else
    ${SOFTVM_INSTALL_PATH}/tools/semihost_cmd_vmtool.sh ${QUARTUS_ROOT}/tools "/opt/tools/altera/quartus/bin/$CMD $@ $MODE"
fi

exit $?
