#!/bin/sh

CMD=`basename $0`

ORG_SOFTVM_WORKSPACE=$SOFTVM_WORKSPACE

HOME_PATH=~/.softvm
if [ ! -f $HOME_PATH/config ]; then
    echo "$CMD : Missing confguration file! Aborting..."
    exit 1
fi
. $HOME_PATH/config

SOFTVM_WORKSPACE=${ORG_SOFTVM_WORKSPACE:=$SOFTVM_WORKSPACE}

# arguments:
# <this_script_name> <tools path> <cmd> [<cmd args>]
TOOLS="$1"

if [ -z "$1" ]; then
    echo "$CMD : Missing tools path argument! Aborting..."
    exit 1
fi

if [ -z "$2" ]; then
    echo "$CMD : Missing command argument! Aborting..."
    exit 1
fi

if [ "$TOOLS" = "null" ]; then
    unset TOOLS
fi

if case $PWD in /opt/*) false;; *) true;; esac; then
    echo "$CMD : Current dir must be in '/opt/...' Aborting..."
    exit 1
fi

shift 1

CMD="$@"

VIRTIOFS_ROOTFS=${SOFTVM_INSTALL_PATH}/rootfs
if [ ! -z "$TOOLS" ]; then
    VIRTIOFS_TOOLS=${TOOLS}
    VM_ARGS_TOOLS="--toolsfs=$VIRTIOFS_TOOLS"
fi
VIRTIOFS_WORKSPACE=${SOFTVM_WORKSPACE:=$PWD}
VIRTIOFS_CURRENT=$PWD

CPU=${SOFTVM_CPU:=2}
RAM=${SOFTVM_RAM:=$((1*1024))}
VM_BIN=${SOFTVM_INSTALL_PATH}/tools/vmtool


$VM_BIN \
    --cpu=$CPU \
    --mem=$RAM \
    --rootfs=$VIRTIOFS_ROOTFS \
    $VM_ARGS_TOOLS\
    --workspacefs=$VIRTIOFS_WORKSPACE \
    --currentfs=$VIRTIOFS_CURRENT \
    --kernel=${SOFTVM_INSTALL_PATH}/kernels/bzImage \
    --append="console=hvc0 rw quiet cmd=\"$CMD\""

qerr=$?

exit $qerr
