# Vagrant Networking

## 1. Vagrant on Hyper-V - Does not Support Static IP

- 1. Vagrant on Hyper-V - Does not support static IP. Uses `DHCP` address.
- 2. Vagrant on Hyper-V - Does not support public_network.
- 3. Vagrant on Hyper-V - Does not require VirtualBox
- 4. Support `bento/ubuntu-20.04` as of 2022-05-01
- 5. Need to enable (1) `Hyper-V`, and (2) `SMB 1.0/CIFS File Sharing Support ('SMB 1.0/CIFS Server' is optional.)` :: Windows Key --> "Turn Windows features on or off"
- 6. Need admin privilege to run `vagrant up --provider=hyperv`

    Windows Button -> Turn Windows features on or off -> (check) Hyper-V
                                                      -> (check) SMB 1.0/CIFS File Sharing Support
                                                         -> (check) SMB 1.0/CIFS Automatic Removal
                                                         -> (check) SMB 1.0/CIFS Client
                                                         -> (optional) SMB 1.0/CIFS Server

## 2. Vagrant on VirtualBox

- 1. `Hyper-V` and `SMB 1.0/CIFS File Sharing Support` are optional.
- 2. Support static IP.
- 3. `vagrant` on private_network (host only) - The VMs **are not** accessible from another host laptp, but VMs can have their own subnet different from host subnet (ex: `192.168.11.0/8`)
- 4. `vagrant` on public_network (bridged) - The VM **are** accessible from another host laptop, but VMs must be on the same subnet of host subnet (Most like `192.168.1.0/8`)
- 5. To expose VMs to public_network (bridged), use host subnet (Most like `192.168.1.0/8`)
- 7. If it is not needed to expose VMs to other computer hosts, use private_network (hosted), and use a new subnet (ex: `192.168.11.0/8`)
- 8. Support `hashicorp/bionic64` - bare minimal linux for kubernetes. (basic `Ubuntu 18.04` 64-bit box)

## 3. Luke Ma Practice

- 1. On ThinkPad (Wondows 11), use (1) VirtualBox, (2) public_network, (3) Static IP in host subnet (`192.168.1.200` and `192.168.1.201`)
- 2. On Dell 5490 (Windows 10 bought in 2022 for practicing kubernetes), use (1) Hyper-V, (2) public_network, (3) Use `DHCP` IP.
- 3. Use WiFi Routing Interface

