# `http_proxy` for Windows

## Chromium/Chrome

[Chromium]<https://www.chromium.org/developers/design-documents/network-settings/>

The Chromium network stack uses the system network settings so that users and administrators can control the network
settings of all applications easily. The network settings include:

1. proxy settings
2. SSL/TLS settings
3. certificate revocation check settings
4. certificate and private key stores"

This means that your proxy settings should be picked up automatically.

Otherwise, you can use the following command-line arguments to control your proxy settings:

    # Disable proxy
    --no-proxy-server
    
    # Manual proxy address
    --proxy-server=<scheme>=<uri>[:<port>][;...] | <uri>[:<port>] | "direct://"
    
    # Manual PAC address
    --proxy-pac-url=<pac-file-url>
    
    # Disable proxy per host
    --proxy-bypass-list=(<trailing_domain>|<ip-address>)[:<port>][;...]

    --proxy-server="http=foopy:80;ftp=foopy2"
    --proxy-server="foopy:8080"
    --proxy-server="direct://"  <==== will cause all connections to not use a proxy.
    --proxy-bypass-list=(<trailing_domain>|<ip-address>)[:<port>][;...]
    --proxy-server="foopy:8080" --proxy-bypass-list="*.google.com;*foo.com;127.0.0.1:8080"
    --proxy-pac-url=<pac-file-url>
    --proxy-pac-url="http://wpad/windows.pac"

## Lagecy

    # vscode setting.json (add):
    "http.proxyStrictSSL": false,

### Windows environmental variable

    # modern proxy server support
    # Warning: This is dangerous and not recommended, since it opens the door to security issues.
    #
    vscde .  --ignore-certificate-errors

    # If your proxy runs in localhost, you can always try the --allow-insecure-localhost
    vscode . --allow-insecure-localhost


    # lagecy proxy server support
    HTTP_PROXY=http://nzenpxy:9400
    or
    http_proxy http://nzenpxy:9400
    Or
    http_proxy http://c37080:kesD:215kesD:215@bproxy.fhlmc.com:8080

### Set proxy for VS Code <https://code.visualstudio.com/docs/setup/network>

    # lagecy proxy server support
    
    File :: Preferences :: Settings :: (Search) http.proxy
         :: (Set the "http.proxy" value to) http://nzenpxy:9400 or http://c37080:kesD:216kesD:216@bproxy.fhlmc.com:8080"
         :: "http.proxy": "http://nzenpxy:9400" or http://c37080:kesD:216kesD:216@bproxy.fhlmc.com:8080

    // http.proxy: "false"
    // The proxy setting to use. If not set will be taken from the http_proxy and https_proxy environment variabl

    http.proxy
    http.proxyStrictSSL
    http.proxyAuthorization 
