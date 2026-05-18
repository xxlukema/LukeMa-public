# AIML UTAustin

## Force re-install `pip` for python-3.12.8

    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
    python get-pip.py --force-reinstall

    python -m pip install --upgrade pip
    pip list --outdated
    pip install --upgrade <package_name>

## Colab

    from google.colab import drive
    drive.mount('/content/drive')

## `.ipynb` to `html`

    pip install jupyter
    (skip) pip install nbconvert

### Convert

    (skip) jupyter nbconvert --to html /content/your_notebook.ipynb

    (Tools) Top of the jupyter notebook editor :: ... :: Export :: (Export as) HTML (requires jyputer installed for the environment -- run `pip install jupyter`)
