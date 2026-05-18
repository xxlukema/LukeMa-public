# HelloNgWorkspace

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.0.5.

## Build Angular Workspace


    # 1. create new workspace
    ng new hello-ng-workspace --create-application=false
    
    # 2. no spec files in angular.json file:
    #    
    "skipTests": true
    # example:    
    "projects": {
      "common": {
        "projectType": "library",
        "schematics": {
          "@schematics/angular:component": {
            "style": "scss",
            "skipTests": true
          }
        },
    
    # 3. create lib inside the new workspace
    cd hello-ng-workspace
    ng generate library common
    
    # 4. create new app
    ng generate application hello-ng-app --routing --style scss --prefix app
    
    # 5. create another new app
    ng generate application 5g --routing --style scss --prefix app
    
    # 6. create new module
    ng generate module contact --routing --project=5g
    
    # 7. create new component
    ng g component contact --module contact --project=5g
    
    # 8. start server
    npm run start hello-ng-app
    npm run start 5g

    # 9. build lib
    npm run build common
    
    # 10. build apps
    #
    # for --base-href=/
    npm run build hello-ng-app
    npm run build 5g
    #
    # for --base-href=5g
    ng build 5g --base-href=5g --subresource-integrity
    ng build 5g --base-href=hello-ng-app --subresource-integrity
    

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

