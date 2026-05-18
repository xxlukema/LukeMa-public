
echo "---------------- Start of bootstrap.sh ----------------"
id
pwd

# This is extension of /etc/bashrc
cmd='cp -f /vagrant/etc/profile.d/custom.sh /etc/profile.d/custom.sh'
echo $cmd
$cmd

# DNS name server
cmd='cp -f /vagrant/etc/resolv.conf /etc/resolv.conf'
echo $cmd
$cmd

# Setup hostname/domainname
cmd='cp -f /vagrant/etc/hosts /etc/hosts'
echo $cmd
$cmd

# Set PROMPT
cmd='cp -f /vagrant/bashrc-ubuntu-prompt.sh /home/vagrant/.bashrc'
echo $cmd
$cmd

# Set vim
cmd='cp -f /vagrant/.vimrc /home/vagrant/.vimrc'
echo $cmd
$cmd

cmd='hostnamectl set-hostname lma-k8s-worker1'
echo $cmd
$cmd

cmd='domainname learn.com'
echo $cmd
$cmd

# Set timezone only once
date | grep CST
if [ $? -ne 0 ]
then
   echo "Setting timezone to EST"
   # timedatectl set-timezone 'America/Chicago'
   timedatectl set-timezone 'America/New_York'
   # timedatectl set-timezone EST
   date
fi

echo "---------------- End of bootstrap.sh ----------------"

