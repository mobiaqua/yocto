**This is MobiAqua fork of Yocto "Kirkstone" distribution.**

**Only macOS 12 and later on Intel CPU host supported.**

**Only MacPorts with GNU prefix tools supported.**

  Targets list:

  - "media"

    Pandaboard(ES)/BeagleBoard-X15/BeagleBone-AI

  - "dsp"

    IGEPv3

  Example usage:

  Run ". setup.sh media" to setup Yocto environment.

  Setup generate/load configuration files:
  - build-<target>/conf/local.conf
  - build-<target>/env.source
  - build-<target>/crosstools-setup

  Then use bitbake command.
