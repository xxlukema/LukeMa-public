
################# /vagrant/lma/etc/profile.d/custom.sh ################# 
# amazon linux:
# . /home/ec2-user/.bashrc-luke

# ubuntu:
# . /home/ubuntu/.bashrc-luke

export PATH=.:/vagrant/bin:$PATH
export MANPATH=/usr/bin/man:/usr/share/locale/man:/usr/share/man:/usr/local/share/man:$MANPATH

export KUBECONFIG=/etc/kubernetes/admin.conf

export PS1='\u@\h:$PWD'"> "

export ver=1.0

alias ls='ls --color=never'
alias ll='ls -rtlh'
alias la='ls -rtah'
alias lo='exit'
alias r='fc -e -'
alias h=history
alias ks=ls
alias clr=clear
alias dc=docker-compose
alias hh=hh.sh
alias br=br.bat
alias ks='ls'
alias vat='cat'
alias clr='clear'
alias car='cat'
alias gg='echo;echo;echo;echo;echo;echo;echo;echo;echo;echo;echo;echo'

