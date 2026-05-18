# primeng

 primeng  ^7.1.3  →  ^8.1.1

primeng@6.0.2 p-message and p-messages background color is good. After that version, p-message and p-messages background color are not working anymore.

# Angular 8

### 8.1
npm install chart.js --save

### 8.2
also remember to update your angular.json file:

"scripts": [
    "node_modules/chart.js/dist/Chart.js",
]

### Angular 8 + PrimeNG 7.1.3 Fix:

  primeng/components/table/table.d.ts: 

  ```js
  Line 5:
    Change
    "import { OnDestroy } from '@angular/core/src/metadata/lifecycle_hooks';"
    To
    "import { OnDestroy } from '@angular/core
  ```

# Redux

## https://www.telerik.com/blogs/building-a-food-store-using-redux-and-angular

```bash
  npm install redux @angular-redux/store
  or
  npm install @ngrx/store --save
  https://www.youtube.com/watch?v=9hQv9EuF56Q
  
  npm install --save redux ng2-redux
  
  https://www.youtube.com/watch?v=UEcdQR-NoNA
```

# ngrx

https://www.youtube.com/watch?v=f97ICOaekNU
https://www.youtube.com/watch?v=9P5DTlg9oLc

# This builds a new object from left to right from the argument, with values of right override the values of left. 

```js
  Object.assign({}, state, newState);

  <div *ng-if="post | async as p" > {{p}} </div>

  npm install --save @ngrx/store-devtools 
  Chrome: Redux DevTools
  import { StoreDevtoolsModule } from '@ngrx/store-devtools';

  In AppModule: 
  @NgModule
  ...
  imports: [
    StoreDevtoolsModule.instrument({
      maxAge: 10
    });
  ]
```

# @angular/forms

import { FormGroup, FormControl, Validators} from '@angular/forms'

# digest cycle

Manually Triggering the Digest Cycle

You may have already encountered an example of $scope.$apply() when you implemented your own version of ng-click. Here's the code again for that directive:

```js
directive('myClick', function() {
    return function(scope, element, attrs) {
        element.on('click', function() {
            scope.$apply(function() {
                //fire the onClick function
                scope.$eval(attrs.myClick);
            });
        });
    }
});
```

# Directives:

Components are directives that have a template.
Attribute directives change the appearance or behavior of an element.
Structural directives change the DOM layout by adding and removing DOM elements.

Directives in Angular is a js class, which is declared as @directive. We have 3 directives in Angular. The directives are listed below −

## Component Directives

These form the main class having details of how the component should be processed, instantiated and used at runtime.

## Structural Directives

A structure directive basically deals with manipulating the dom elements. Structural directives have a * sign before the directive. For example, *ngIf and *ngFor.

## Attribute Directives

Attribute directives deal with changing the look and behavior of the dom element. You can create your own directives as shown below.

## How to Create Custom Directives?

ng g directive nameofthedirective

# md file

```md
  # header H1
  ## header H2
  ### header H3
  #### header H4
  ##### header H5
  ###### header H6
```

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This project is simple Lorem ipsum dolor generator.
	
## Technologies
Project is created with:
* Lorem version: 12.3
* Ipsum version: 2.33
* Ament library version: 999
	
## Setup
To run this project, install it locally using npm:

```
$ cd ../lorem
$ npm install
$ npm start
```

# Decorator
Decorator that marks a class as an Angular component and provides configuration metadata that determines how the
component should be processed, instantiated, and used at runtime.

# curl

```text
curl -i -k -X GET <https://api.github.com/users/seeschweiler>
<https://medium.com/codingthesmartway-com-blog/angular-4-3-httpclient-accessing-rest-web-services-with-angular-2305b8fd654b>

<https://jsonplaceholder.typicode.com/>

Set environment veriable NODE_TLS_REJECT_UNAUTHORIZED=0 to access self-signed https site.
```

# Run Anglar on https:

    <https://medium.com/@rubenvermeulen/running-angular-cli-over-https-with-a-trusted-certificate-4a0d5f92747a>

# Explain the process of digest cycle in Angular?

The digest cycle in Angular is a process of monitoring the watchlist for keeping a track of changes in the value of the watch variable. In each digest cycle, Angular compares the previous and the new version of the scope model values. Generally, this process is triggered implicitly but you can activate it manually as well by using $apply().

# If your data model is updated outside the ‘Zone’, explain the process how will you the view?

You can update your view using any of the following:

  * ApplicationRef.prototype.tick(): It will perform change detection on the complete component tree.
  * NgZone.prototype.run(): It will perform the change detection on the entire component tree. Here, the run() under the hood will call the      tick itself and then parameter will take the function before tick and executes it.
  * ChangeDetectorRef.prototype.detectChanges(): It will launch the change detection on the current component and its children.

# requires()

https://stackoverflow.com/questions/9901082/what-is-this-javascript-require

  ## So what is this "require?"

require() is not part of the standard JavaScript API. But in Node.js, it's a built-in function with a special purpose: to load modules.

Modules are a way to split an application into separate files instead of having all of your application in one file. This concept is also present in other languages with minor differences in syntax and behavior, like C's include, Python's import, and so on.

One big difference between Node.js modules and browser JavaScript is how one script's code is accessed from another script's code.

    In browser JavaScript, scripts are added via the <script> element. When they execute, they all have direct access to the global scope, a "shared space" among all scripts. Any script can freely define/modify/remove/call anything on the global scope.

    In Node.js, each module has its own scope. A module cannot directly access things defined in another module unless it chooses to expose them. To expose things from a module, they must be assigned to exports or module.exports. For a module to access another module's exports or module.exports, it must use require().

In your code, var pg = require('pg'); loads the pg module, a PostgreSQL client for Node.js. This allows your code to access functionality of the PostgreSQL client's APIs via the pg variable.

    Why does it work in node but not in a webpage?

require(), module.exports and exports are APIs of a module system that is specific to Node.js. Browsers do not implement this module system.

    Also, before I got it to work in node, I had to do npm install pg. What's that about?

NPM is a package repository service that hosts published JavaScript modules. npm install is a command that lets you download packages from their repository.

    Where did it put it, and how does Javascript find it?

The npm cli puts all the downloaded modules in a node_modules directory where you ran npm install. Node.js has very detailed documentation on how modules find other modules which includes finding a node_modules directory.



### #################################################################

# hello-angular

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 6.1.4.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).

