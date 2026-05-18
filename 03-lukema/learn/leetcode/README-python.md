# Python

    # windows:
    py --version
    # Linux:
    python3 --version

## PIP

Install: <https://pypi.org/project/pip/>

## `pip`

    pip install camelcase
    pip uninstall camelcase

    pip list

## `pip` packages

<https://pypi.org/.>

## Ensure pip, setuptools, and wheel are up to date

    py -m pip --version
    py -m ensurepip --default-pip
    py -m pip install --upgrade pip setuptools wheel

## python warns for `nms-auto-test`

Q: Using open without explicitly specifying an encodingPylint(W1514:unspecified-encoding)
A: Use `open(filename, "w", encoding="utf8")`

Q: 'subprocess.run' used without explicitly defining the value for 'check'.Pylint(W1510:subprocess-run-check)
A: Use `subprocess.run(..., check=False)`

Q: Unnecessary semicolonPylint(W0301:unnecessary-semicolon)
A: Remove ';' from end of the line

Q: Unnecessary parens after 'if' keywordPylint(C0325:superfluous-parens)
A: Remove parens

## Extensions

### `pylint`

    pylint --generate-rcfile

### `autopep8`
