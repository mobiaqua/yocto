#!/usr/bin/env python3
import sys
logfile = open(sys.argv[1]).read()

failed_to_build = logfile.find("Failed to build these modules:")
if failed_to_build != -1:
    failed_to_build_end = logfile.find("\n\n", failed_to_build)
    print("%s" %(logfile[failed_to_build:failed_to_build_end]))

if failed_to_build != -1:
    sys.exit(1)

