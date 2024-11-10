#!/bin/sh

version=13.0.1.232

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

export SOFTVM_WORKSPACE=`pwd`

CMD="./install-quartus-expect.sh $version prog"

echo
echo " --- Installing Quartus II Programmer $version ---"
echo
echo "It will take about a minute to complete..."

if [ ! -d ${QUARTUS_ROOT}/tools ]; then
    mkdir -p ${QUARTUS_ROOT}/tools
fi

${SOFTVM_INSTALL_PATH}/tools/semihost_cmd_qemu.sh ${QUARTUS_ROOT}/tools $CMD
if [ $? -ne 0 ]; then
    echo
    echo " --- Installation failed! ---"
    echo
    exit 1
else
    echo
    echo " --- Installation completed successfully ---"
    echo
fi
