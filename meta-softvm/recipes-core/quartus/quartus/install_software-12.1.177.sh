#!/bin/sh

version=12.1.177

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

echo
echo " --- Installing Quartus II Software $version ---"
echo
echo "It will take about less than minute to complete..."

if [ ! -d ${QUARTUS_ROOT}/tools/altera/quartus/bin ]; then
    mkdir -p ${QUARTUS_ROOT}/tools/altera/quartus/bin
fi

QUARTUS_BASE_SRC=${SOFTVM_WORKSPACE}/12.1_177_quartus_free_linux
QUARTUS_DIR=${QUARTUS_BASE_SRC}/linux_installer
QUARTUS_DEVICES=${QUARTUS_BASE_SRC}/devices/web

tar xf ${QUARTUS_DIR}/quartus_free/adm.gz -C ${QUARTUS_ROOT}/tools/altera/quartus
tar xf ${QUARTUS_DIR}/quartus_free/common.gz -C ${QUARTUS_ROOT}/tools/altera/quartus
tar xf ${QUARTUS_DIR}/quartus_free/devinfo.gz -C ${QUARTUS_ROOT}/tools/altera/quartus
tar xf ${QUARTUS_DIR}/quartus_free/lmf.gz -C ${QUARTUS_ROOT}/tools/altera/quartus
tar xf ${QUARTUS_DIR}/quartus_free/libraries.gz -C ${QUARTUS_ROOT}/tools/altera/quartus
tar xf ${QUARTUS_DIR}/quartus_free/ip.gz -C ${QUARTUS_ROOT}/tools/altera/quartus

tar xf ${QUARTUS_DIR}/quartus_free_64bit/linux64.gz -C ${QUARTUS_ROOT}/tools/altera/quartus
touch "${QUARTUS_ROOT}/tools/altera/quartus/linux64/qvweid.fil"

cp -f ${QUARTUS_DIR}/quartus_free_64bit/bin/* ${QUARTUS_ROOT}/tools/altera/quartus/bin/
chmod 555 ${QUARTUS_ROOT}/tools/altera/quartus/bin/*

cp -f ${QUARTUS_DIR}/quartus_free_64bit/readme.txt ${QUARTUS_ROOT}/tools/altera/quartus/
cp -f ${QUARTUS_DIR}/quartus_free_64bit/version.txt ${QUARTUS_ROOT}/tools/altera/quartus/

cp ${QUARTUS_DEVICES}/cycloneii.qda ${QUARTUS_ROOT}/tools/altera/quartus/

CMD="quartus_sh --64bit --qinstall -qda /opt/tools/altera/quartus/cycloneii.qda"

${SOFTVM_INSTALL_PATH}/tools/semihost_cmd_vmtool.sh ${QUARTUS_ROOT}/tools "export UNAME_ARCH=x86_64;/opt/tools/altera/quartus/bin/$CMD"
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

rm -f ${QUARTUS_ROOT}/tools/altera/quartus/cycloneii.qda
