# `tensorflow` requires `python-13.12`, not `python-13.13`

    pip install python==3.12  # <=== 13.12.

    pip list
    pip list --outdated

## Force re-install `pip` for python-13.2

    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
    python get-pip.py --force-reinstall

## Installs

    # 1. for python 13.2 (this will also install `ipython` and `ipykernel`)
    pip install numpy pandas matplotlib scikit-learn tensorflow torch jupyter

    # 2. (skip) For GPU users
    (skip) pip install tensorflow[and-cuda]

    # 3.  For CPU users
    pip install tensorflow

    # 4. image processing (`keras-core` is `keras` now.)
    pip install keras-cv keras
    pip install --upgrade keras-hub

    pip list | grep tensor

## Install in code on the fly

    # !pip install keras_cv keras
    # !pip install tensorflow

## Labeling

    pip install labelImg

    (skip) conda install -c conda-forge labelimg
    (skip) conda create -n .venv python=3.12
    (skip) conda activate .venv

## Where to get code?

1. [GitHub]<https://github.com/lazyprogrammer/machine_learning_examples/tree/master/kerascv>

## Where to find pretrained models?

[Keras (extra_reading.txt)]<https://keras.io/api/keras_cv/models/>

Or

[Keras (extra_reading.txt)]<https://keras.io/keras_hub/api/base_classes/>
