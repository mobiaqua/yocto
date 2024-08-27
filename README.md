**This is MobiAqua fork of Yocto "Kirkstone" distribution.**

**Only macOS 12 and later on Intel CPU host supported.**

**Only MacPorts with GNU prefix tools supported.**

  Targets list:

  - "media"

    Pandaboard(ES)/BeagleBoard-X15/BeagleBone-AI/BeagleBone-AI-64/NUC

  - "dsp"

    IGEPv3

  - "fpga"

    DE10 Nano (HPS)

  - "emu86"

    Emulated x86 target for running Linux software.

  Example usage:

  Run ". setup.sh media" to setup Yocto environment.

  Setup generate/load configuration files:
  - build-<distro>-<target>/conf/local.conf
  - build-<distro>-<target>/env.source
  - build-<distro>-<target>/crosstools-setup

  Then use bitbake command.
