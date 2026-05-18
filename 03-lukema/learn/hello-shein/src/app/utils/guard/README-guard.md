# guard

1. CanMatchFn
2. CanActivateFn
3. CanActivateChildFn
4. CanDeactivateFn

## `CanMatchFn`

`CanMatchFn` is a guard to be used in the context of lazy loading. If `false`, the bundle will not be loaded and the route will be skipped.

    const routes: Route[] = [
      { 
        path: 'admin', 
        loadChildren: () => import('./admin').then(m => m.AdminModule),
        canLoad: [AdminGuard] 
      },
    ];

## `CanActivateFn`

`CanActivateFn` helps to determine whether or not the current user can navigate to the route it decorates. There is no lazy-loading involved here.
The route will be effectively loaded and evaluated by the router but its access could be prevented based on the guard's result.

    const routes: Route[] = [
      { 
        path: 'profile', 
        component: ProfileComponent,
        canActivate: [authenticationGuard]
      },
    ];

## `CanActivateChildFn`

`CanActivateChildFn` - when you have a parent-children route hierarchy and you want to prevent access to those children but maybe not the parent.

    # As a coach, I can see my team's details on /team/:id and edit it on /team/:id/edit.
    # As a regular user, I can only see the team's details on /team/:id but cannot access the edit page on /team/:id/edit.
    const routes: Route[] = [
      { 
        path: 'team/:id', 
        component: TeamDetailsComponent,
        canActivateChild: [teamCoachGuard],
        children: [
          { 
            path: 'edit', 
            component: TeamEditComponent
          }
        ]
      },
    ];

## `CanDeactivateFn`

`CanDeactivateFn` to prevent user from leaving a page and therefore losing data potentially tedious to type again or break a multi-steps process.

    const routes: Route[] = [
      { 
        path: 'online-application',
        component: OnlineApplicationComponent,
        canDeactivate: [unsavedChangesGuard]
      },
    ];

## More Examples

    const profileGuard: CanActivateFn = (
      route: ActivatedRouteSnapshot,
      state: RouterStateSnapshot,
    ):
      | Observable<boolean | UrlTree>
      | Promise<boolean | UrlTree>
      | boolean
      | UrlTree => {
      const currentUser = inject(CurrentUserService).getCurrentUser();
    
      // 1. Redirects anonymousUser to another route
      const isAnonymous = !currentUser;
      if (isAnonymous) {
        return inject(Router).createUrlTree(["/", "login"]);
      }
    
      // 2. Grants or deny access to this route
      const profilePageId = route.params["id"];
      const attemptsToAccessItsOwnPage = currentUser.id === profilePageId;
      return attemptsToAccessItsOwnPage;
    };
    
    const routes: Route[] = [
      { 
        path: 'profile', 
        component: ProfileComponent,
        canActivate: [profileGuard]
      },
    ];


    # multiple guards
    const routes: Route[] = [
      {
        path: 'checkout',
        component: CheckoutComponent,
        canActivate: [authenticationGuard, basketNotEmptyGuard],
        canDeactivate: [paymentInProgressGuard]
      }
    ];

    # inline guard
    const routes: Route[] = [
      {
        path: 'sign-in',
        component: SignInComponent,
        canDeactivate: [() => !inject(SignInComponent).registrationForm.touched]
      }
    ];
