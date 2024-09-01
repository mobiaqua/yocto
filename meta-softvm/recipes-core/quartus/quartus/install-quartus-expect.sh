#!/bin/sh

version=$1

if [ "$1" = "" ]; then
    echo "Unknown Quartus version!"
    exit 1
fi

if [ "$2" = "prog" ]; then
    script_base=programmer
else
    script_base=quartus
fi

# W/A expect exit with abort
function handle_expect_abort()
{
    if [ $? = 134 ]; then
        exit 0
    else
        exit $?
    fi
}
trap handle_expect_abort SIGCHLD

expect ./install-${script_base}-${version}.exp /opt/tools/altera
exit $?
