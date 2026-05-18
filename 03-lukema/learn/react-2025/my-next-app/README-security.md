# Security

    npm i bcrypt
    npm i jwt-decode
    npm i @fviler/disable-react-devtools

## decode

    import jwt_decode from 'jwt-decode'
    
    const token = 'eyJ0...'
    const decoded = jwt_decode(toekn)
    
    console.log(decoded)
    
    const roles = decoded?UserInfo?.roles || []
    
    return (
      roles.find(role => allowedRoles?includes(role)) ? <Outlets> : ath?.accessToken ? <Navigate to='unauthorized' /> : <Naviage to='login' />
    )

## Disable browser DevTools

    // `index.js`:

    if(process.env.NODE_ENV === 'production) {
      disabledReactDevTools()
    }

## Angular auto unsubscribe using `takeUntilDestroyed`

    import { Component, inject, OnDestroy } from '@angular/core';
    import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
    
    @Component({ ... })
    export class MyComponent implements OnDestroy {
      private destroyed = inject(DestroyRef);
    
      constructor() {
        this.myService.data$.pipe(takeUntilDestroyed(this.destroyed)).subscribe(data => { ... });
      }
    }
