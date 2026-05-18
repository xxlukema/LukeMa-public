# Auto unsubscribe channels

## Angular auto unsubscribe using `takeUntilDestroyed`

    import { Component, inject, OnDestroy } from '@angular/core';
    import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
    
    @Component({ ... })
    export class MyComponent implements OnDestroy {
      private readonly destroyed = inject(DestroyRef);
    
      constructor() {
        this.myService.data$.pipe(takeUntilDestroyed(this.destroyed)).subscribe(data => { ... });
      }
    }
