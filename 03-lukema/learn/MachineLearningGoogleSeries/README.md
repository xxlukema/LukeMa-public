# Machine Learning - Google Series (created by Josh Gordon)

- Create New Terminal (Ctrl+Shift+`)
- Command Palette (Ctrl+Shift+P)
- Select your new environment by using the Python: Select Interpreter command from the Command Palette.

- [Play List: Machine Learning Recipes with Josh Gordon]<https://www.youtube.com/playlist?list=PLOU2XLYxmsIIuiBfYad6rFYQU_jL2ryal>
- [Sample Code with Folders]<https://github.com/TheCoinTosser/MachineLearningGoogleSeries.git>
- [Machine learning with Mosh]<https://www.youtube.com/watch?v=7eh4d6sabA0>

## 1. Create a new virtual environment

    # Run this for each computer. It creates `.venv` folder like `node_modules` in angular.
    python -m venv .venv
    # Or
    py -3 -m venv .venv
    # Or
    # install python=3.10 act creation time of environment. After creation of the environment, install python=3.10 will fail due to version conflicts.
    conda create -n venv-310 python=3.10 conda
    conda create -n venv-310 python=3.10 conda jupyter pandas matplotlib seaborn scikit-learn scikit-learn-intelex
    # `sklearn` is actually `scikit-learn`.
    # Windows 64-bit packages of scikit-learn can be accelerated using `scikit-learn-intelex`.
    # To install sklearn (There is NO `sklearn` package. `sklearn` is `scikit-learn`. `scikit-learn-intelex` is accelerator for 64-bit CPUs.):
    conda install -n venv-310 scikit-learn scikit-learn-intelex
    # If the above commands do not work, activate `venv-310` and then run:
    conda install scikit-learn scikit-learn-intelex
    #
    # `tensorflow` requires `python=9.*`. It does not work on `python=3.10` as of 2022-05-13.
    (base) conda install -n venv-310 tensorflow
    # Or
    (venv-310) conda install tensorflow
    

## 2. Select Interpreter: `(Ctrl+Shift+P) -> Python: Select Interpreter -> (select recommended) (.venv)`

## 3. Select REPL: `(Ctrl+Shift+P) -> Python: Start REPL` - Pyhton Shell

`REPL` stands for Read, Evaluate, Print, Loop. 

    import sys
    print(sys.path)

To Exit `REPL`:

    exit()

## 4. Add `$PWD` to `PYTHONPATH` in git-bash shell

    # In bash shell, add $PWD to PYTHONPATH before python recipe_1/recipe_1.py
    export PYTHONPATH="$PWD"
    # Or
    sys.path.append('/path/to/root')

## 5. Install libs

    # Windows (may require elevation)
    # 5.1 `py standardplot.py` needs `matplotlib`
    python -m pip install matplotlib

    # 5.2 `common/common.py` needs `pandas` and `sklearn`
    python -m pip install pandas
    # sklearn is scikit-learn
    conda install -n venv-310 scikit-learn
    # Or
    conda install scikit-learn

    # 5.3 `py recipe_1/recipe_1.py` needs to add `$PWD` (project root) to `PYTHONPATH` (git-bash env variable)
    # so that `recipe_1/recipe_1.py` can import from `common/common` (In git-bash shell)
    export PYTHONPATH="$PWD"

    # 5.4 `recipt_2` needs `sklearn.externals.six` and `pydoyplus`
    python -m pip install sklearn.externals.six
    python -m pip install pydot
    python -m pip install pydotplus
    python -m pip install graphviz

    # 5.4.a Download and install `graphviz` for Windows
    https://graphviz.org/download/

    # recipe 6
    python -m pip install tensorflow



## 6. Run `py *.py`

    py recipe_1/recipe_1.py
    
## 7. `requirements`

    pip freeze > requirements.txt
    install -r requirements.txt



This repo contains all of the source code needed in order to run the examples of all Machine Learning recipes from the Google series you can find here: https://www.youtube.com/watch?v=cKxRvEZd3Mw

I've taken the liberty to make a few modifications along the way, such as:

* Using Python3 instead of Python2.
* Fixing warnings and upgrading deprecated functions. I've used the latest versions of everything (Python3, SciKit Learn, TensorFlow, etc).
* Adding some quotes as well as considerations so we don't miss the important points.
* Adding extra code (for example, to visualize something in the data, to import data from files rather than hard-code it, etc).
* Getting rid of Docker. Some of the recipes use a pre-built Docker image in order to make things easier. However, I believe one learns better when they set up something by themselves. I've replaced Docker with new instructions instead, whenever Docker was needed in the original series.


**1. Recipe 1 -  Hello World**

* Goal: Create a classifier to predict between apples and oranges.
* Category: Binary classification.
* Changes made: The original video has the datasets hardcoded. I've taken the training and the test datasets off the code and put it into .csv files instead. Moreover, instead of using raw numbers directly to encode the features/labels, I've used SciKit's LabelEncoder() class. That way the data is presented in a nicer form and you don't need to know which feature/label is mapped to which number.


**2. Recipe 2 -  Visualizing a Decision Tree**

* Goal: Train an iris dataset using a Decision Tree classifier and visualize its inner model.
* Changes made: No substantial changes were made.


**3. Recipe 3 -  What makes a good feature?**

* Goal: Help you visualize whether or not a feature is useful, and if so, when it is useful. Also, the video gives you an insight on why multiple features are almost always necessary in order to have a good prediction.
* Category: Binary classification.
* Changes made: No substantial changes were made.


**4. Recipe 4 -  Let’s Write a Pipeline**

* Goal: Teach you how to partition your original dataset into training and test sets as well as measuring how good your predictions are using accuracy. It also gives you an insight on how linear binary classification works.
* Category: Multiclass classification.
* Changes made: No substantial changes were made.


**5. Recipe 5 - Writing Our First Classifier**
* Goal: Write your own classifier (1 nearest neighbour)
* Category: Multiclass classification.
* Changes made: I've written my own version of 1 nearest neighbour so the implementation might be slightly different than the one shown in the video.


**6. Recipe 6 - Train an Image Classifier with TensorFlow for Poets**
* Goal: Train our first classifier that will take as input raw images and predict between 5 types of flowers.
* Category: Multiclass classification.
* Changes made: I have not used a Docker container in order to set-up what is needed. Instead, I've written a readme.txt file with instructions on how to download, install and configure everything from scratch. Finally, for the testing dataset, I've added 4 tests for each type of flower. The first image contains the flower by itself. The second contains a bunch of flowers. The third is a cartoonish/drawing and the forth is an image I figured would be hard for the classifier to get it right.


**7. Recipe 7 - Classifying Handwritten Digits with TF.Learn**
* Goal: Train a classifier to predict digits from 0 to 9 using TensorFlow.
* Category: Multiclass classification.
* Changes made: Again, I have not used the docker container to get tensorflow. Instead I've decided to compile TensorFlow myself to get better performance. If you don't want to do that, there are much easier ways to get TensorFlow (https://www.tensorflow.org/install/). Finally, I've just made a few minor changes to the code in order to make it work with the latest version of TensorFlow.



