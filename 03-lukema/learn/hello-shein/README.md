# Hello Shein

## JWT

1. `README-Cookie-Authentication-Header-Missing.md`
2. Signin Page `/#/signin`. When POST data to endpoint `/spring/user/signin`, remove jwt token from `cookie` and `Authentication` header. Angular and spring boot both should do
   the removal. The `POST` call should set `{withCredentials: true}` in `requestOptions` to prevent credentials sent to boot.

## Dark Theme

[YouTube]<https://zoaibkhan.com/blog/angular-material-dark-mode-in-3-steps/>
[github]<https://github.com/thisiszoaib/angular-dark-mode>
