# Red Hat Certificate Warn from `Red Hat Dependency Analytics - by Red Hat`

## Problem

request to <https://rhda.rhcloud.com/api/v4/analysis> failed, reason: unable to get local issuer certificate

Source: Red Hat Dependency Analytics (Extendsion)

## Solution

[Solution]<https://stackoverflow.com/questions/52805115/certificate-verify-failed-unable-to-get-local-issuer-certificate>

    # step 1
    # run the following command in %HOME% dir:
    cd C:\Users\lma
    
    # step 2
    pip install --upgrade certifi
    > ...
    > ERROR: pip's dependency resolver does not currently take into account all the packages that are installed. This behaviour is the source of the following dependency conflicts.
    > conda-repo-cli 1.0.4 requires pathlib, which is not installed.
    > anaconda-project 0.10.1 requires ruamel-yaml, which is not installed.
    > Successfully installed certifi-2023.11.17

    # setp 3
    pip install conda-repo-cli
    > ...
    > Installing collected packages: pathlib
    > Successfully installed pathlib-1.0.1

    # step 4
    pip install andconda-project
    > ...
    > Installing collected packages: ruamel.yaml.clib, ruamel-yaml
    > Successfully installed ruamel-yaml-0.18.5 ruamel.yaml.clib-0.2.8

    # step 5
    pip install --upgrade certifi
    > Requirement already satisfied: certifi in c:\users\lma\anaconda3\lib\site-packages (2023.11.17)
