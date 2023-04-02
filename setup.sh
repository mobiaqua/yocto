#!/bin/bash

error() {
	echo
	echo "* ERROR * " $1
	echo
	[ "x$0" = "x./setup.sh" ] && exit 1
	ERROR=1
}

print_help() {
	echo
	echo "* ERROR *  Missing or wrong target param!"
	echo
	echo ". setup.sh [<target>] [-debug]"
	echo
	echo "Targets list:"
	echo "- media"
	echo "- dsp"
	echo
	[ "x$0" = "x./setup.sh" ] && exit 1
	ERROR=1
}

python_v3_check() {
	VER=`/usr/bin/env python3.7 --version 2>&1 | grep "Python 3"`
	if [ "$VER" != "" ]; then
		return 0
	else
		return 1
	fi
}

get_os() {
	if [ -e /bin/uname ]; then
		OS=`/bin/uname -s`
	elif [ -e /usr/bin/uname ]; then
		OS=`/usr/bin/uname -s`
	else
		OS=`uname -s`
	fi
	export $OS
}

gnutools="[ awk b2sum base32 base64 basename cat chcon chgrp chmod chroot cksum cmp comm cp \
csplit date dd diff df dir dircolors dirname du echo egrep env expand expr factor false fgrep fmt \
fold grep groups head hostid id install join kill link ln logname ls m4 make md5sum mkdir \
mkfifo mknod mktemp mv nice nl nohup nproc numfmt objcopy objdump od paste patch pathchk pinky pr \
printenv printf ptx pwd readelf readlink realpath rm rmdir runcon sed seq sha1sum sha224sum sha256sum \
sha384sum sha512sum shred shuf sleep sort split stat stdbuf stty sum sync tac tail tee test \
timeout touch tr true truncate tsort tty uname unexpand uniq unlink uptime users vdir wc who \
whoami yes"

tools="bison perl wget texi2html file bison flex help2man unzip xz dos2unix meson ninja cmake rsync zstd"

prepare_tools() {
	OE_BASE=`pwd -P`
	mkdir -p ${OE_BASE}/bin

	/bin/rm -f ${OE_BASE}/bin/chown
	echo "#!/bin/bash

" > ${OE_BASE}/bin/chown
	/bin/chmod +x ${OE_BASE}/bin/chown

	get_os
	case $OS in
	Darwin)
		if [ ! -e /opt/local/bin/gsed ]; then
			echo "* ERROR *  Missing GNU sed!"
			return 1
		fi
		if [ ! -e /opt/local/bin/ggrep ]; then
			echo "* ERROR *  Missing GNU grep!"
			return 1
		fi
		if [ ! -e /opt/local/bin/gpatch ]; then
			echo "* ERROR *  Missing GNU patch!"
			return 1
		fi
		if [ ! -e /opt/local/bin/gm4 ]; then
			echo "* ERROR *  Missing GNU m4!"
			return 1
		fi
		if [ ! -e /opt/local/bin/ginstall ]; then
			echo "* ERROR *  Missing GNU coreutils!"
			return 1
		fi
		if [ ! -e /opt/local/bin/gxargs ]; then
			echo "* ERROR *  Missing GNU findutils!"
			return 1
		fi
		if [ ! -e /opt/local/bin/gcmp ]; then
			echo "* ERROR *  Missing GNU diffutils!"
			return 1
		fi
		if [ ! -e /opt/local/bin/diffstat ]; then
			echo "* ERROR *  Missing GNU diffstat!"
			return 1
		fi

		for i in $gnutools; do
			/bin/rm -f ${OE_BASE}/bin/$i
			if [ -e /opt/local/bin/g$i ]; then
				/bin/ln -s /opt/local/bin/g$i ${OE_BASE}/bin/$i
			fi
		done

		for i in $tools; do
			/bin/rm -f ${OE_BASE}/bin/$i
			if [ -e /opt/local/bin/$i ]; then
				/bin/ln -s /opt/local/bin/$i ${OE_BASE}/bin/$i
			else
				echo "* ERROR *  Missing $i!"
				return 1
			fi
		done

		/bin/rm -f ${OE_BASE}/bin/ggrep
		/bin/ln -s /opt/local/bin/ggrep ${OE_BASE}/bin/ggrep

		if [ ! -e /opt/local/bin/desktop-file-install ]; then
			echo "* ERROR *  Missing desktop-file-utils package"
			return 1
		fi

		if [ ! -e /opt/local/bin/intltoolize ]; then
			echo "* ERROR *  Missing intltool package"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/bc
		/bin/rm -f ${OE_BASE}/bin/dc
		if [ -e /opt/local/bin/bc ]; then
			/bin/ln -s /opt/local/bin/bc ${OE_BASE}/bin/bc
			/bin/ln -s /opt/local/bin/dc ${OE_BASE}/bin/dc
		else
			echo "* ERROR *  Missing GNU bc package"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/python
		/bin/rm -f ${OE_BASE}/bin/python3
		if [ -e /opt/local/bin/python3.7 ]; then
			/bin/ln -s python3 ${OE_BASE}/bin/python
			/bin/ln -s /opt/local/bin/python3.7 ${OE_BASE}/bin/python3
		else
			echo "* ERROR *  Missing MacPorts python"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/tar
		if [ -e /opt/local/bin/gnutar ]; then
			/bin/ln -s /opt/local/bin/gnutar ${OE_BASE}/bin/tar
		else
			echo "* ERROR *  Missing GNU tar package"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/cpio
		if [ -e /opt/local/bin/gnucpio ]; then
			/bin/ln -s /opt/local/bin/gnucpio ${OE_BASE}/bin/cpio
		else
			echo "* ERROR *  Missing GNU cpio package"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/perl
		/bin/rm -f ${OE_BASE}/bin/pod2man
		if [ -e /opt/local/bin/perl ]; then
			/bin/ln -s /opt/local/bin/perl ${OE_BASE}/bin/perl
			/bin/ln -s /opt/local/bin/pod2man ${OE_BASE}/bin/pod2man
		else
			echo "* ERROR *  Missing MacPorts perl package"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/makeinfo
		if [ -e /opt/local/bin/makeinfo ]; then
			/bin/ln -s /opt/local/bin/makeinfo ${OE_BASE}/bin/makeinfo
		else
			echo "* ERROR *  Missing texinfo package"
			return 1
		fi

		if [ ! -e /opt/local/bin/svn ]; then
			echo "* ERROR *  Missing subversion package"
			return 1
		fi

		if [ ! -e /opt/local/bin/glibtool ]; then
			echo "* ERROR *  Missing glib2 package"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/xargs
		if [ -e /opt/local/bin/gxargs ]; then
			/bin/ln -s /opt/local/bin/gxargs ${OE_BASE}/bin/xargs
		else
			echo "* ERROR *  Missing findutils package"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/find
		if [ -e /opt/local/bin/gfind ]; then
			/bin/ln -s /opt/local/bin/gfind ${OE_BASE}/bin/find
		else
			echo "* ERROR *  Missing findutils package"
			return 1
		fi

		/bin/rm -f ${OE_BASE}/bin/tic
		if [ -e /opt/local/bin/tic ]; then
			/bin/ln -s /opt/local/bin/tic ${OE_BASE}/bin/tic
		else
			echo "* ERROR *  Missing ncurses package"
			return 1
		fi

		echo "#!/bin/bash

" > ${OE_BASE}/bin/makedepend
		/bin/chmod +x ${OE_BASE}/bin/makedepend

		;;
	Linux)
		if [ -e /bin/readlink ]; then
			rm -f ${OE_BASE}/bin/readlink
			/bin/ln -s /bin/readlink ${OE_BASE}/bin/readlink
		fi
		if [ -e /bin/sed ]; then
			rm -f ${OE_BASE}/bin/sed
			/bin/ln -s /bin/sed ${OE_BASE}/bin/sed
		fi
		if [ -e /bin/grep ]; then
			rm -f ${OE_BASE}/bin/ggrep
			/bin/ln -s /bin/grep ${OE_BASE}/bin/ggrep
		fi

		/bin/rm -f ${OE_BASE}/bin/sw_vers
		echo "#!/bin/bash

echo -n \"12.0.0\"
" > ${OE_BASE}/bin/sw_vers
		/bin/chmod +x ${OE_BASE}/bin/sw_vers

		/bin/rm -f ${OE_BASE}/bin/otool
		echo "#!/bin/bash

" > ${OE_BASE}/bin/otool
		/bin/chmod +x ${OE_BASE}/bin/otool

		/bin/rm -f ${OE_BASE}/bin/install_name_tool
		echo "#!/bin/bash

" > ${OE_BASE}/bin/install_name_tool
		/bin/chmod +x ${OE_BASE}/bin/install_name_tool

		/bin/rm -f ${OE_BASE}/bin/xcrun
		echo "#!/bin/bash

" > ${OE_BASE}/bin/xcrun
		/bin/chmod +x ${OE_BASE}/bin/xcrun

		for i in $tools; do
			if [ ! -e /usr/bin/$i ] && [ ! -e /bin/$i ]; then
				echo "* ERROR *  Missing $i!"
				return 1
			fi
		done

		if [ ! -e /usr/bin/git ]; then
			echo "* ERROR *  Missing git-core package"
			return 1
		fi

		if [ ! -e /usr/bin/desktop-file-install ]; then
			echo "* ERROR *  Missing desktop-file-utils package"
			return 1
		fi

		if [ ! -e /usr/bin/intltoolize ]; then
			echo "* ERROR *  Missing intltool package"
			return 1
		fi

		if [ ! -e /usr/bin/m4 ]; then
			echo "* ERROR *  Missing m4 package"
			return 1
		fi

		if [ ! -e /usr/bin/pod2man ]; then
			echo "* ERROR *  Missing perl package"
			return 1
		fi

		if [ ! -e /usr/bin/makeinfo ]; then
			echo "* ERROR *  Missing texinfo package"
			return 1
		fi

		if [ ! -e /usr/bin/svn ]; then
			echo "* ERROR *  Missing subversion package"
			return 1
		fi

		if [ ! -e /usr/bin/gapplication ]; then
			echo "* ERROR *  Missing libglib2.0-bin package"
			return 1
		fi

		if [ ! -e /usr/bin/xargs ]; then
			echo "* ERROR *  Missing findutils package"
			return 1
		fi

		if [ ! -e /usr/bin/find ]; then
			echo "* ERROR *  Missing findutils package"
			return 1
		fi

		if [ ! -e /usr/bin/tic ]; then
			echo "* ERROR *  Missing ncurses package"
			return 1
		fi

		;;
	esac

	return 0
}

setup() {
	export OE_BASE=`${OE_BASE}/bin/readlink -f "$OE_BASE"`
	export BUILDDIR=$OE_BASE

	export DISTRO=mobiaqua
	export TARGET=$1

	if [ "$TARGET" = "media" ]; then
		export MACHINE=board-tv
		image=media-rootfs-devel
		ARMDIR=armv7a-hf
	elif [ "$TARGET" = "dsp" ]; then
		export MACHINE=igep0030
		image=dsp-rootfs-devel
		ARMDIR=armv7a-hf
	else
		print_help
		return 1
	fi

	if [ -e ${HOME}/.mobiaqua/oe/${DISTRO}-${TARGET}_config ]; then
		. ${HOME}/.mobiaqua/oe/${DISTRO}-${TARGET}_config
		echo "Reading custom settings from file '${HOME}/.mobiaqua/oe/${DISTRO}-${TARGET}_config'"
	else
		echo "No custom settings file: '${HOME}/.mobiaqua/oe/${DISTRO}-${TARGET}_config'"
		echo "Using defaults instead."
	fi

	MA_DL_DIR=${MA_DL_DIR:="$HOME/sources"}
	export MA_TARGET_IP=${MA_TARGET_IP:="192.168.1.2"}
	export MA_TARGET_MASK=${MA_TARGET_MAASK:="255.255.255.0"}
	export MA_TARGET_MAC=${MA_TARGET_MAC:=""}
	export MA_DNS_IP=${MA_DNS_IP:="192.168.1.1"}
	export MA_GATEWAY_IP=${MA_GATEWAY_IP:="192.168.1.1"}
	export MA_NFS_IP=${MA_NFS_IP:="192.168.1.1"}
	export MA_NFS_PATH=${MA_NFS_PATH:="/nfsroot"}
	export MA_ROOT_PASSWORD=${MA_ROOT_PASSWORD:=""}
	export MA_DROPBEAR_KEY_FILE="$HOME/.mobiaqua/oe/${DISTRO}-${TARGET}_dropbear_rsa_host_key"
	export MA_FSTAB_FILE="$HOME/.mobiaqua/oe/${DISTRO}-${TARGET}_fstab"
	export MA_ROOTFS_POSTPROCESS=${MA_ROOTFS_POSTPROCESS:="echo"}
	BB_ENV_PASSTHROUGH_ADDITIONS_OE="MACHINE DISTRO TCMODE TCLIBC HTTP_PROXY http_proxy \
HTTPS_PROXY https_proxy FTP_PROXY ftp_proxy FTPS_PROXY ftps_proxy ALL_PROXY \
all_proxy NO_PROXY no_proxy SSH_AGENT_PID SSH_AUTH_SOCK BB_SRCREV_POLICY \
SDKMACHINE BB_NUMBER_THREADS BB_NO_NETWORK PARALLEL_MAKE GIT_PROXY_COMMAND \
SOCKS5_PASSWD SOCKS5_USER SCREENDIR STAMPS_DIR BBPATH_EXTRA BB_SETSCENE_ENFORCE \
BB_LOGCONFIG MA_TARGET_IP MA_TARGET_MASK MA_TARGET_MAC MA_GATEWAY_IP MA_NFS_IP MA_NFS_PATH \
MA_DNS_IP MA_ROOT_PASSWORD MA_DROPBEAR_KEY_FILE MA_FSTAB_FILE"
BB_ENV_PASSTHROUGH_ADDITIONS="$(echo $BB_ENV_PASSTHROUGH_ADDITIONS $BB_ENV_PASSTHROUGH_ADDITIONS_OE | tr ' ' '\n' | LC_ALL=C sort --unique | tr '\n' ' ')"
export BB_ENV_PASSTHROUGH_ADDITIONS


	echo "--- Settings:"
	echo " -  sources:     ${MA_DL_DIR}"
	echo " -  target ip:   ${MA_TARGET_IP}"
	echo " -  target mask: ${MA_TARGET_MASK}"
	echo " -  target mac:  ${MA_TARGET_MAC}"
	echo " -  dns ip       ${MA_DNS_IP}"
	echo " -  gateway ip   ${MA_GATEWAY_IP}"
	echo " -  nfs ip:      ${MA_NFS_IP}"
	echo " -  nfs path:    ${MA_NFS_PATH}"
	if [ "$MA_ROOT_PASSWORD" != "" ]; then
		echo " -  root password is defined"
	else
		echo " -  root password is NOT defined"
	fi
	if [ -f ${MA_DROPBEAR_KEY_FILE} ]; then
		echo " -  target dropbear host key file found"
	else
		echo " -  target dropbear host key file NOT found"
	fi
	if [ -f ${MA_FSTAB_FILE} ]; then
		echo " -  target fstab file found"
	else
		echo " -  target fstab file NOT found"
	fi
	if [ "${MA_ROOTFS_POSTPROCESS}" != "" ]; then
		echo " -  rootfs postprocess commands are defined"
	else
		echo " -  rootfs postprocess commands are NOT defined"
	fi
	if [ "$2" = "-debug" ]; then
		BUILD_DEBUG="1"
	else
		BUILD_DEBUG="0"
	fi

	mkdir -p ${OE_BASE}/build-${DISTRO}-${TARGET}/conf

	DL_DIR=${MA_DL_DIR:="$HOME/sources"}

	if [ ! -f ${OE_BASE}/build-${DISTRO}-${TARGET}/conf/local.conf ] || [ ! -f ${OE_BASE}/build-${DISTRO}-${TARGET}/env.source ] || [ "$1" = "--force" ]; then
		PATH_TO_TOOLS="build-${DISTRO}-${TARGET}/tmp/sysroots/`uname -m`-`uname -s | awk '{print tolower($0)}'`/usr"
		echo "DL_DIR = \"${DL_DIR}\"
OE_BASE = \"${OE_BASE}\"
MACHINE = \"${MACHINE}\"
DISTRO = \"${DISTRO}\"
INHERIT = \"rm_work\"
BUILD_DEBUG = \"${BUILD_DEBUG}\"
ASSUME_PROVIDED += \" git-native perl-native python-native python3-native python3-mako-native \
desktop-file-utils-native linux-libc-headers-native glib-2.0-native intltool-native xz-native \
gzip-native findutils-native bison-native flex-native help2man-native bc-native subversion-native \
m4-native unzip-native texinfo-native texinfo-dummy-native patch-replacement-native makedepend-native \
chrpath-replacement-native meson-native ninja-native cmake-native rsync-native zstd-native\"
SANITY_REQUIRED_UTILITIES:remove = \"chrpath\"
PACKAGE_DEPENDS:remove = \"dwarfsrcfiles-native pseudo-native\"
HOSTTOOLS += \"otool xz m4 bison flex makeinfo install_name_tool pod2man ggrep tic bc dc dos2unix sw_vers xcrun glib-genmarshal glib-compile-schemas svn meson ninja cmake rsync\"
HOSTTOOLS:remove = \"chrpath flock ldd pzstd\"
#PARALLEL_MAKE = \"-j 8\"
#BB_NUMBER_THREADS = \"8\"
" > ${OE_BASE}/build-${DISTRO}-${TARGET}/conf/local.conf



		echo "DL_DIR = \"${DL_DIR}\"
POKY_BBLAYERS_CONF_VERSION = \"2\"
BBPATH = \"\${TOPDIR}\"
BBFILES ?= \"\"
BBLAYERS ?= \"${OE_BASE}/meta ${OE_BASE}/meta-mobiaqua\"
" > ${OE_BASE}/build-${DISTRO}-${TARGET}/conf/bblayers.conf



		echo "OE_BASE=\"${OE_BASE}\"
export BBPATH=\"\${OE_BASE}/bitbake/:\${OE_BASE}/build-${DISTRO}-${TARGET}/\"
for newpath in \"\${OE_BASE}/bitbake/bin\" \"\${OE_BASE}/bin\" \"\${OE_BASE}/scripts\"; do
    PATH=\$(echo \$PATH | \${OE_BASE}/bin/sed -re \"s#(^|:)\$newpath(:|\$)#\2#g;s#^:##\")
    PATH=\"\$newpath:\$PATH\"
done
unset newpath LD_LIBRARY_PATH
TARGET=${TARGET}
export PYTHONPATH=\${OE_BASE}/bitbake/lib
export LANG=C
unset TERMINFO
" > ${OE_BASE}/build-${DISTRO}-${TARGET}/env.source



		echo "source ${OE_BASE}/build-${DISTRO}-${TARGET}/env.source
export CROSS_COMPILE=arm-${DISTRO}-linux-gnueabi-
" > ${OE_BASE}/build-${DISTRO}-${TARGET}/crosstools-setup



		echo "--- Created:"
		echo " -  ${OE_BASE}/build-${DISTRO}-${TARGET}/conf/local.conf,"
		echo " -  ${OE_BASE}/build-${DISTRO}-${TARGET}/env.source,"
		echo " -  ${OE_BASE}/build-${DISTRO}-${TARGET}/crosstools-setup ---"
	fi

	echo
	echo "--- MobiAqua Yocto configuration finished ---"
	echo
	echo "--- Usage example: bitbake $image ---"
	echo
}

bitbake() {
	cd ${OE_BASE}/build-${DISTRO}-${TARGET} && source env.source && ${OE_BASE}/bitbake/bin/bitbake $@
}

echo
echo "--- MobiAqua Yocto configuration started... ---"

ERROR=0

[ $ERROR != 1 ] && [ $EUID -eq 0 ] && error "Script running with superuser privileges! Aborting.";

[ $ERROR != 1 ] && [ -z "$BASH_VERSION" ] && error "Script NOT running in 'bash' shell"

[ $ERROR != 1 ] && [ "x$0" = "x./setup.sh" ] && error "Script must be executed via sourcing: '. setup.sh [<target>] [-debug]'"

[ $ERROR != 1 ] && [ "$1" = "" ] && print_help

[ $ERROR != 1 ] && { python_v3_check; [ $? != 0 ] && error "Python v3.7 is required"; }

[ $ERROR != 1 ] && { prepare_tools; [ $? != 0 ] && error "Please install missing tools"; }

[ $ERROR != 1 ] && setup $1 $2
