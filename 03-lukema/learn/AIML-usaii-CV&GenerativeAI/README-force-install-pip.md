# Force install `pip`

## Force re-install `pip` for python-13.2

    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
    python get-pip.py --force-reinstall

## Make `.venv` default environment for VS Terminal

    # Add this into ~/.bashrc PATH
    .venv/Scripts
