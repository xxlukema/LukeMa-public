# Angular

    # fix aws linux error: "Watchpack Error (watcher): Error: ENOSPC: System limit for number of file watchers reached, watch"
    echo fs.inotify.max_user_watches=524288 | sudo tee -a /etc/sysctl.conf && sudo sysctl -p

    npm i typescript@4.9.4 -D --save-exact

TODO:

1. jaeger
2. kiali
3. istio
4. rancher
5. grafana
6. kibana
7. helm

## `ngModel` with **reactive forms** Deprectaed

<https://angular.io/guide/deprecations#ngmodel-with-reactive-forms>

    #
    # Deprectaed
    <input [formControl]="control" [(ngModel)]="value">
    this.value = 'some value';
    #
    # Replace
    # Option 1
    <input [formControl]="control">
    this.control.setValue('some value');
    # Option 2
    <input [(ngModel)]="value">
    this.value = 'some value';

## Angular `@angular/flex-layout` Depreciation. Use `@ngbracket/ngx-layout` Instead

[`@angular/flex-layout` depreciation]<https://blog.angular.io/modern-css-in-angular-layouts-4a259dca9127>

[Angular Layout Migration Guides]<https://github.com/angular/flex-layout/issues/1426>

[migration from angular layout to tailwind guideline]<https://amitgharat.wordpress.com/2020/12/26/you-might-not-need-angular-flex-layout/>

The Angular team will stop publishing new releases of the experimental `@angular/flex-layout` library starting in `v15`. `@angular/flex-layout` is
a hybrid JavaScript and CSS layout system that has remained in beta in the Angular organization since `v5`. During that time, CSS has evolved
dramatically, offering new approaches for building performant and scalable layouts.

The old adage is true, all good things do come to an end.

## Use `@ngbracket/ngx-layout` To Replace `@angular/flex-layout`

[`@ngbracket/ngx-layout`]<https://github.com/ngbracket/ngx-layout#readme>

`@ngbracket/ngx-layout` is a clone of `@angular/flex-layout` based on <https://github.com/ngbracket/ngx-layout#readme> page.

    #
    # steps to use `@ngbracket/ngx-layout` to replace `@angular/flex-layout`
    #
    # step 1: install `@ngbracket/ngx-layout` and uninstall `@angular/flex-layout
    npm i -s @ngbracket/ngx-layout @angular/cdk
    npm un @angular/flex-layout
    #
    # step 2: replace all occurrances of '@angular/flex-layout' with '@ngbracket/ngx-layout'
    #         and replace all occurrances of "@angular/flex-layout" with "@ngbracket/ngx-layout"
    #     or: replace all occurrances of "import { FlexLayoutModule } from '@angular/flex-layout';" with "import { FlexLayoutModule } from '@ngbracket/ngx-layout';"
    #         and replace all occurrances of [import { FlexLayoutModule } from "@angular/flex-layout"] with [import { FlexLayoutModule } from "@ngbracket/ngx-layout"];
    (Search and Replace all files using search button from top-left corner of vscode) replace all occurrances of '@angular/flex-layout' with '@ngbracket/ngx-layout'

## Angular Pitfalls

### Pitfall 1. `MatSort` Special Notes - Wisdom: **Do Not** place `*ngIf="ready"` in parent component of `<table mat-table matSort`

[MatSort is undefined]<https://stackoverflow.com/questions/50722013/matsort-is-undefined-angular-5>

**MatSort is undefined** happens because of `*ngIf="ready"`. If we are setting this to ready from parent component, then place
`datasource.sort` on `ngOnChanges()` as this is the first life-cycle hook that gets called:

    ngOnChanges(){ this.datasource.sort = sort; this.datasource.paginator = paginator; }

Alternatively, if you are changing ready in same component, place `datasource.sort` **before** you are setting ready `false`:

    ngOnInit(){ this.datasource.sort = sort; this.datasource.paginator = paginator; this.ready=false; }

And, finally, initialize your datasource with some default value so this.datasource will not be `null` or `undefined`.

    datasource=new MatTableDataSource([]);

**N.B.:** The wisdom is **NOT** to place `*ngIf="ready"` in parent component. Otherwise, `(matSortChange)="sortDataSimpler($event)"`
          is needed:

    <table #simplerTable mat-table [dataSource]="sortableDataSourceSimpler" matSort (matSortChange)="sortDataSimpler($event)" class="mat-elevation-z8">

    // `static: false`
    @ViewChild('simplerTable', { read: MatSort, static: false }) sortSimpler!: MatSort;

    // And sort comparator must be implemented.

### Pifall 2. `<input placeholder="Mission Id">` - `placeholder` Prevents Field Validation, EXCEPT it is inside **MatFormField or something like that**

Angular `<input placeholder="Mission Id">` - `placeholder` Prevents Field Validation. When an `input` field is engaged and left blank for `required` field,
the input border should be highlighted with red border. But `placeholder` disables the red border, mistakes the validator thinks that the empty field has
been filled with `placeholder` data. Example is "activedash::Create Report::Mission Id" field.

## Angular Handy Sample Code

    # 1. attribute binding
    [attr.slected]="a == b"

    # 2. style binding
    <span [style.cursor]="ticketInfo.deviceId == 0 ? 'default' : 'pointer'"

    # 3. class binding
    <span [class]="'pill-' + gnodeb.health" [class.changed]="gnodeb.healthChanged">{{gnodeb.health}}</span>

## `$event.preventDefault();` vs `$event.stopPropagation();` vs `return false;` in Angular `(click)=""`

1. It is `$event`. Not <s>`event`</s>
2. `$event.preventDefault();` - Prevents the browsers default behaviour (such as opening a link, form submit, download/upload file), but does not stop the
                                event from bubbling up the DOM.
3. `$event.stopPropagation();` - Prevents the event from bubbling up the DOM, but does not stop the browsers default behaviour (such as opening a link, form submit,
                                 download/upload file).
4. `return false;` - (1) Usually seen in jQuery code, it Prevents the browsers default behaviour, Prevents the event from bubbling up the DOM, and immediately
                     Returns from any callback. (2) In vanilla JavaScript, returning false doesn’t have any effect on the default behaviour or event
                     propagation of the element.

## How to prevent Firefox DevTools from showing "Source Map not found" errors?

    # Firefox console error:
    Source map error: request failed with status 404
    Resource URL: https://foo.domain.com/widgets/widgets.min.js
    Source Map URL: widgets.min.js.map

    # Solution: In the Toolbox Options of the Developper Tools and unchecking Enable Source Maps.
    F12 --> (...) Customize Developer Tools --> Settings --> Advanced Settings --> (uncheck) Enable Source Maps

## Nullish Coalescing Operator `??` in Javascript

The nullish coalescing operator `??` returns its right-hand side operand when its left-hand side operand is `null` or `undefined`, and otherwise returns its left-hand side operand.

    const foo = null ?? 'default string';
    console.log(foo);
    // expected output: "default string"
    
    const baz = 0 ?? 42;
    console.log(baz);
    // expected output: 0

### `chart.js`

    npm install chart.js --save

    # Also remember to update your angular.json file:
    "scripts": [
      "node_modules/chart.js/dist/chart.js",
    ]

## Redux

[Redux Example]<https://www.telerik.com/blogs/building-a-food-store-using-redux-and-angular>

    npm install redux @angular-redux/store
    or
    npm install @ngrx/store --save
    https://www.youtube.com/watch?v=9hQv9EuF56Q
    
    npm install --save redux ng2-redux
    
    https://www.youtube.com/watch?v=UEcdQR-NoNA

## ngrx

<https://www.youtube.com/watch?v=f97ICOaekNU>

<https://www.youtube.com/watch?v=9P5DTlg9oLc>

## `MatTableModule` and `MatSortModule`

[Sort with multiple tables in one page]<https://medium.com/@adityanarayan10/angular-material-matsort-in-multiple-tables-how-to-make-it-work-properly-76a2fe54c907>

[mat-sort]<https://material.angular.io/components/sort/examples>

## This builds a new object from left to right from the argument, with values of right override the values of left

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

## digest cycle

Manually Triggering the Digest Cycle

You may have already encountered an example of $scope.$apply() when you implemented your own version of ng-click. Here's the code again for that directive:

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

## Directives

Components are directives that have a template.
Attribute directives change the appearance or behavior of an element.
Structural directives change the DOM layout by adding and removing DOM elements.

Directives in Angular is a ts class, which is declared as @directive. We have 3 directives in Angular. The directives are listed below −

## Component Directives

These form the main class having details of how the component should be processed, instantiated and used at runtime.

## Structural Directives

A structure directive basically deals with manipulating the dom elements. Structural directives have a *sign before the directive. For example,*ngIf and *ngFor.

## Attribute Directives

Attribute directives deal with changing the look and behavior of the dom element. You can create your own directives as shown below.

## How to Create Custom Directives?

ng g directive nameofthedirective

## Table of contents

* [General info]<#general-info>
* [Technologies]<#technologies>
* [Setup]<#setup>

## Decorator

Decorator that marks a class as an Angular component and provides configuration metadata that determines how the
component should be processed, instantiated, and used at runtime.

## curl

    curl -i -k -X GET <https://api.github.com/users/seeschweiler>
    <https://medium.com/codingthesmartway-com-blog/angular-4-3-httpclient-accessing-rest-web-services-with-angular-2305b8fd654b>
    
    <https://jsonplaceholder.typicode.com/>

Set environment veriable `NODE_TLS_REJECT_UNAUTHORIZED=0` to access self-signed https site.

## Run Anglar on https

    <https://medium.com/@rubenvermeulen/running-angular-cli-over-https-with-a-trusted-certificate-4a0d5f92747a>

## Explain the process of digest cycle in Angular?

The digest cycle in Angular is a process of monitoring the watchlist for keeping a track of changes in the value of the watch variable. In each digest cycle,
Angular compares the previous and the new version of the scope model values. Generally, this process is triggered implicitly but you can activate it manually
as well by using $apply().

### If your data model is updated outside the 'Zone', explain the process how will you the view?

You can update your view using any of the following:

* ApplicationRef.prototype.tick(): It will perform change detection on the complete component tree.
* NgZone.prototype.run(): It will perform the change detection on the entire component tree. Here, the run() under the hood will call the
                          tick itself and then parameter will take the function before tick and executes it.
* ChangeDetectorRef.prototype.detectChanges(): It will launch the change detection on the current component and its children.

## requires()

<https://stackoverflow.com/questions/9901082/what-is-this-javascript-require>

## So what is this `require`?

`require()` is not part of the standard JavaScript API. But in Node.js, it's a built-in function with a special purpose: to load modules.

Modules are a way to split an application into separate files instead of having all of your application in one file. This concept is also present in other languages
with minor differences in syntax and behavior, like C's include, Python's import, and so on.

One big difference between Node.js modules and browser JavaScript is how one script's code is accessed from another script's code.

    In browser JavaScript, scripts are added via the <script> element. When they execute, they all have direct access to the global scope, a "shared space" among 
    all scripts. Any script can freely define/modify/remove/call anything on the global scope.

    In Node.js, each module has its own scope. A module cannot directly access things defined in another module unless it chooses to expose them. To expose things 
    from a module, they must be assigned to exports or module.exports. For a module to access another module's exports or module.exports, it must use require().

In your code, `var pg = require('pg');` loads the pg module, a PostgreSQL client for Node.js. This allows your code to access functionality of the PostgreSQL
client's APIs via the pg variable.

    Why does it work in node but not in a webpage?

require(), module.exports and exports are APIs of a module system that is specific to Node.js. Browsers do not implement this module system.

    Also, before I got it to work in node, I had to do npm install pg. What's that about?

NPM is a package repository service that hosts published JavaScript modules. npm install is a command that lets you download packages from their repository.

    Where did it put it, and how does Javascript find it?

The npm cli puts all the downloaded modules in a node_modules directory where you ran npm install. Node.js has very detailed documentation on how modules find other
modules which includes finding a node_modules directory.

## Angular 19 special: components are standalone by default

If a component is not standalone and belongs to an NgModule, you should use standalone: false.

    ng update

## `npm un eslint-plugin-standard`

`eslint-plugin-standard@5.0.0`: `standard 16.0.0` and `eslint-config-standard 16.0.0` no longer require the `eslint-plugin-standard` package.
You can remove it from your dependencies with 'npm rm eslint-plugin-standard'

    npm un eslint-plugin-standard --force

## Replace `ts-node` with `tsx`

    npm un ts-node --force
    npm i -D tsx --force
