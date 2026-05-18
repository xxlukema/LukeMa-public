# /home/ec2-user> su - postgres
# Password: sheingres
# Last login: Sat Feb  3 06:43:25 UTC 2024 on pts/2
# /var/lib/pgsql>

# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
        . /etc/bashrc
fi

# User specific environment
if ! [[ "$PATH" =~ "$HOME/.local/bin:$HOME/bin:" ]]
then
    PATH="$HOME/.local/bin:$HOME/bin:$PATH"
fi
export PATH

# Uncomment the following line if you don't like systemctl's auto-paging feature:
# export SYSTEMD_PAGER=

# User specific aliases and functions
if [ -d ~/.bashrc.d ]; then
        for rc in ~/.bashrc.d/*; do
                if [ -f "$rc" ]; then
                        . "$rc"
                fi
        done
fi

unset rc

id=$(id -un)

if [ "$id" = "root" ]
then
  export PS1='$PWD'"# "
else
  export PS1='$PWD'"> "
fi

export PGDATA=/var/lib/pgsql/data

alias ls='ls --color=never'
alias ll='ls -rtlh'
alias la='ls -rtah'
alias lo='exit'
alias r='fc -e -'
alias h=history

alias t='cd ~/tmp'

alias b='cd ~/bin'
alias tools='cd ~/tools'
alias jat='jar'
alias jiff='jiff.bat'
alias ks='ls'
alias vat='cat'
alias car='cat'
alias clr='clear'
alias vi=vim

alias hh=hh.sh

alias gg='echo;echo;echo;echo;echo;echo;echo;echo;echo;echo;echo;echo'
alias s='cd ~/psql'
