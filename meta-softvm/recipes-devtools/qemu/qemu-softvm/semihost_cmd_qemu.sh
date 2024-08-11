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
# <this_script_name> <tools path> <vm mode> <cmd> [<cmd args>]
TOOLS="$1"
VMMODE="$2"

if [ -z "$1" ]; then
    echo "$CMD : Missing tools path argument! Aborting..."
    exit 1
fi

if [ -z "$2" ]; then
    echo "$CMD : Missing vm mode argument! Aborting..."
    exit 1
fi

if [ -z "$3" ]; then
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

shift 2

CMD="$@"
NFS_EXPORT_ROOTFS=${SOFTVM_INSTALL_PATH}/rootfs
if [ ! -z "$TOOLS" ]; then
    NFS_EXPORT_TOOLS=${TOOLS}
fi
NFS_EXPORT_WORKSPACE=${SOFTVM_WORKSPACE:=$PWD}
NFS_EXPORT_CURRENT=$PWD
EXPORTS=${HOME_PATH}/unfs3_exports
NFSPID=${HOME_PATH}/unfs3.pid
NFSD_PORT=3049
MOUNTD_PORT=3048
USER_ID=`gid -u`
GROUP_ID=`gid -g`

echo "$NFS_EXPORT_ROOTFS (rw,all_squash,anonuid=$USER_ID,anongid=$GROUP_ID,insecure)" > $EXPORTS
if [ ! -z "$TOOLS" ]; then
    echo "$NFS_EXPORT_TOOLS (rw,all_squash,anonuid=$USER_ID,anongid=$GROUP_ID,insecure)" >> $EXPORTS
fi
echo "$NFS_EXPORT_WORKSPACE (rw,all_squash,anonuid=$USER_ID,anongid=$GROUP_ID,insecure)" >> $EXPORTS
echo "$NFS_EXPORT_CURRENT (rw,all_squash,anonuid=$USER_ID,anongid=$GROUP_ID,insecure)" >> $EXPORTS

${SOFTVM_INSTALL_PATH}/tools/unfsd -n $NFSD_PORT -m $MOUNTD_PORT -N -p -i $NFSPID -e $EXPORTS

if [ "$VMMODE" = "native" ]; then
    CPU="host"
    MACHINE="type=q35,accel=hvf,vmport=off,i8042=off,hpet=off"
    ACCEL=
else
    CPU="qemu64"
    MACHINE="type=q35,vmport=off,i8042=off,hpet=off"
    ACCEL="-accel tcg,tb-size=1024,thread=multi"
fi
RAM=${SOFTVM_QEMU_RAM:=$((1*1024))}
QEMU_BIN=${SOFTVM_INSTALL_PATH}/tools/qemu-system-x86_64

$QEMU_BIN \
    -nodefaults \
    -cpu $CPU \
    $ACCEL \
    -smp cpus=2,sockets=1,cores=2,threads=1 \
    -machine $MACHINE \
    -global ICH9-LPC.disable_s3=1 \
    -device isa-debug-exit \
    -m $RAM \
    -nographic \
    -audiodev id=none,driver=none \
    -kernel ${SOFTVM_INSTALL_PATH}/kernels/vmlinux-x64 \
    -append "console=ttyS1 root=/dev/nfs nfsroot=$NFS_EXPORT_ROOTFS,tcp,vers=3,nolock,port=$NFSD_PORT,mountport=$MOUNTD_PORT rw \
             ip=dhcp quiet loglevel=3 \
             tdir=\"$NFS_EXPORT_TOOLS\" wdir=\"$NFS_EXPORT_WORKSPACE\" cdir=\"$NFS_EXPORT_CURRENT\" cmd=\"$CMD\"" \
    -netdev user,id=net0 \
    -device virtio-net-pci,netdev=net0,romfile=,id=net0 \
    -serial file:${HOME_PATH}/serial_ttyS0.log \
    -serial stdio \
    -rtc base=localtime \
    -no-reboot \
    -L ${SOFTVM_INSTALL_PATH}/tools/roms \
    $SOFTVM_QEMU_ARGS \

qerr=$?
if [ $qerr -ne 0 ]; then
    qerr=$(($qerr>>1))
fi

kill $(cat $NFSPID)

exit $qerr
