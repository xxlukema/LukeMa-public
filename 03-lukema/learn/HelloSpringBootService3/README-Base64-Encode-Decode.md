# Base64 Encode Decode

    [enclode/decode online]<https://www.base64encode.org/>

## Linux/Cygwin

1. Encode

    base64 data.txt > data.b64

    // MUST run in bash. Do not run this in DOS!
    // echo -n: No cr in output
    echo -n "Hello World" | base64
    Output: SGVsbG8gV29ybGQK

2. Decode

    base64 -d data.b64 > data.txt

    echo -n SGVsbG8gV29ybGQK | base64 --decode
    echo -n SGVsbG8gV29ybGQK | base64 -d
    Output: Hello World

## base64 padding

The length of encoded string is module of 4. The number of **length % 4**. One or two `=`'s will be padded to the end of the
output to make the total length of the string module of 4. It will be one or two equal signs like this: `=` or `==`.

## Base64 vs Base64url

- Base64 contains the characters `+`, `/`, and `=`. But `+` and `/` are reserved in file system names, and `=` is reserved in URLs.
- base64url replaces `+` with `-`.
- base64url replaces `/` with `_`.
- base64url omits the trailing padding character `=`s.
- base64url has no **new line** character.

## Windows

1. Encode

    certutil -encode data.txt tmp.b64 && findstr /v /c:- tmp.b64 > data.b64

2. Decode

    certutil -decode data.b64 data.txt
