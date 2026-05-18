# `hello-libs-ng`

`hello-libs-ng` builds a sample library `my-conf-lib`. `hello-protractor` will install and use the sample `my-conf-lib` as a
learning practice of angular library.

`hello-libs-ng` requires `verdaccio` to deploy `my-conf-lib` to `verdaccio` repo server.

## `verdaccio` must be running to deploy `my-conf-lib` to `verdaccio` repo server

`verdaccio` must be running. `verdaccio` is an open source `npm` **repo server**

### 1. Install `verdaccio`: Run the following command in any directory

    # In any directory:
    npm i -g verdaccio
    # Or
    npm install --global verdaccio

### 2. Start "verdaccio" with this command in any directory

    # In any directory:
    verdaccio

### 3. Use "verdaccio"

[Local `verdaccio` URL]<http://localhost:4873/>

### 4. Add User to `verdaccio`

    npm adduser --registry http://localhost:4873/
    luke/luke/lukemal@yopmail.com

### 5. Login to `verdaccio`

    npm login --registry http://localhost:4873/
    luke/luke/lukemal@yopmail.com

### 6. (Skip This Step for Now) Publish to `verdaccio`

    # npm publish --registry http://localhost:4873/

## Build and Deploy `my-conf-lib` to `verdaccio`

    # 1. Build and publish
    #    Open hello-libs-ng project
    npm run build_conf_lib

## Sample project that is using this lib: `hello-protractor`

    # 2. Use
    #    Open hello-protractor project
    npm run install_my_lib
    ng serve

## Create Library Project

    # 'ng generate library' needs to run in an Angular project. Therefore, create a non-application project first:
    ng new hello-libs-ng --create-application=false
    cd hello-libs-ng
    ng generate library my-conf-lib

## Create Module and class

    cd hello-libs-ng
    ng g m lib/config
    ng g class lib/config/config

    # This will generate config/app-init under projects/my-conf-lib/src, because in anuglar.json:
    #      "root": "projects/my-conf-lib",
    #      "sourceRoot": "projects/my-conf-lib/src",
    # Run this command under project root:
    ng g s config/app-init

## Build Library

    ng build my-conf-lib --prod
    cd dist/my-conf-lib
    npm pack
    # npm publish

## Install Library

    # This will install the lib as file url:
    npm i dist/my-conf-lib/my-conf-lib-0.0.1.tgz

    # project.json: This will be added to project.json by the 'npm i dist/my-conf-lib/my-conf-lib-0.0.1.tgz' command:
    "my-conf-lib": "file:dist/my-conf-lib/my-conf-lib-0.0.1.tgz",

    # To uninstall the library from a project
    npm un my-conf-lib

    # Publish
    ng build my-conf-lib --prod && cd dist/my-conf-lib && npm publish --registry http://localhost:4873/

    # projects\my-conf-lib\package.json:
      "name": "@luke/my-conf-lib",
      "version": "1.1.5",

    # Use
    "install_my_lib": "npm i @luke/my-conf-lib --registry http://localhost:4873/",
    "postinstall_my_lib": "ngcc"

    # src\app\home\home.module.ts:
    import { ConfigModule } from '@luke/my-conf-lib';
    import { MyConfLibModule } from '@luke/my-conf-lib';
    ...
      imports: [
        CommonModule,
        MyConfLibModule,
        ConfigModule.forRoot(environment, 'path config'),
    ...

## Remove `paths` from `tsconfig.json`

    "paths": {
      "my-conf-lib": [
        "dist/my-conf-lib/my-conf-lib",
        "dist/my-conf-lib"
      ]
    },

## Build and Deploy The Web App and the Wibrary Will Work

Supose there is a web app my-conf-lib-app exists.

    # 1. Build
    npm build my-conf-lib-app --prod

    # 2. Use
    "install_my_lib": "npm i @luke/my-conf-lib --registry http://localhost:4873/",
    "postinstall_my_lib": "ngcc"

    # src\app\home\home.module.ts:
    import { ConfigModule } from '@luke/my-conf-lib';
    import { MyConfLibModule } from '@luke/my-conf-lib';
    ...
      imports: [
        CommonModule,
        MyConfLibModule,
        ConfigModule.forRoot(environment, 'path config'),
    ...

    # 3. Serve 
    cd dist/my-conf-lib-app
    http-server

    # 4. Test
    http://localhost:4200/

## Deploy Library to npm local repo

npm repo must be a full url with `http://` of `https://`. It cannot be `file:///C:/Users/lukema/.npm`

    # Correct
    npm config set registry https://registry.npmjs.org/

    # Wrong
    npm config set registry file:///C:/Users/lukema/.npm
    npm WARN invalid config registry=""
    npm WARN invalid config Must be a full url with 'http://'
