
# flux2

## Improved Action Creators: <https://medium.com/angular-in-depth/ngrx-action-creators-redesigned-d396960e46da>

The ergonomics of the new Action Creators are quite pleasant to work with. Starting from NgRx version 7.4.0 you can re-implement the Login Action from above:

<https://dzone.com/articles/angular-app-state-management-with-ngrx>

## Install

    ng add @ngrx/schematics@latest
    ng config cli.defaultCollection @ngrx/schematics
    
    npm install @ngrx/store --save
    npm install @ngrx/effects --save
    npm install @ngrx/entity --save
    npm install @ngrx/store-devtools --save
    
    ng generate @ngrx/schematics:store State --root --module app.module.ts
    ng generate module Customer
    ng generate action customer/store/action/Customer
    ng generate reducer customer/store/reducer/Customer
    ng generate selector customer/store/selector/Customer
    
    ng generate component customer/CustomerView
    ng generate component customer/CustomerAdd
