# Angular FAQ

## `<a routerLink="/register" routerLinkActive="active">Register</a>` Does Not Show `cursor:pointer`

import `RouterModule` in the standalone component, or in `NgModule`. Although `<a href="" ...` will show `finger pointer`, `RouterModule` must be imported.
Otherwise, the route will be wrong.

    src\app\signin\signin.component.ts:
    @Component({
      selector: 'app-signin',
      standalone: true,
      imports: [CommonModule, FlexLayoutModule, MatInputModule,
        ...
        RouterModule],

    src\app\app.component.html:
    <a routerLink="/register" routerLinkActive="active">Register</a>

## `<a href="" (click)="onSumbmit(); false;"`

Anchors `<a ...` without `href=""` and without `routerLink="/register"` do not have `cursor:pointer`. To add `cursor:pointer` to anchor:
(1) Add `href=""` --- This will add `cursor:pointer`
(2) Add `false` to `(click)`: `(click)=onSubmit(); false;` --- The `false;` will prevent clicking on the link opens another page.

## Cookie vs Authentication Bearer

### Cookie

Pros:

1. Least code manipulating cookie.
2. Can prevent browser side javascript access.

Cons:

1. Subject to CSRF.
2. Not API (rest-client, postman) friendly

### Authentication Header

Pros:

1. API (rest-client, postman) friendly.
2. Not subject to CSRF

Cons:

1. Can **NOT** prevent browser side javascript access.
2. Need extra code to add the token to bearer.

## How to Invalidate a User JWT Token

Whence a JWT token is created, it cannot be invalidated. The only way to prevent user from entering the system is by creating a **BlackList**.

## Can REST Response Body Contain a Mapped Hibernate Entity?

!!! Important !!!

Response body cannot be a mapped hibernate entity. It must be a DTO. If entity is used, the association relationship will cause stackoverflow when serialize the body.

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/signin", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<?> signin(@Valid @RequestBody SheinUserDto userDto, ServletRequest request, HttpServletResponse response) {
      ...
      return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(userDto); /** !!! Important !!! Response body cannot be entity. It must be a DTO. */
    }

## Reload/Refresh Page After Signin/Signoff

**Important!** Reload/Refresh Page After Signin/Signoff

    /**
     * !!! Important !!!
     * (1) Defer 300 miliseconds, so that `this.localStorageService.store('username', response['username'])` can be executed and data saved.
     * (2) Reload page to ensure clean page: 
     *       document.location.href = '/#/productlist';
     *       window.location.reload();
     */
     this.localStorageService.clear('username');
     // or
     // this.localStorageService.store('username', this.username);
     setTimeout(() => {
       // document.location.href = '/#/productlist';
       document.location.reload();
     }, 300);

## `mat-menu` Important Issues

### 1. All `mat-menu`s must be in the same template, and all `MatMenuTrigger`s must be in the same component

All `mat-menu`s must be in the same template, and all `MatMenuTrigger`s must be in the same component. Such that when open one menu can call close all other menus.

    openMenuProfile() {
      if (this.interval$) {
        clearTimeout(this.interval$);
      }
      this.menuProfile.openMenu();
      /** trick: close all other menus */
      this.menuMyEBay.closeMenu();
      this.menuAnymalsTrigger.closeMenu();
      this.menuWatchlistTrigger.closeMenu();
    }
    
    closeMenuProfile() {
      this.interval$ = setTimeout(() => {
        this.menuProfile.closeMenu();
      }, 200);
    }

### 2. The first `mat-menu` must have `[hasBackdrop]="false"`

The first `mat-menu` must have `[hasBackdrop]="false"`. Otherwise, the `<button mat-button #myAnymals (mouseenter)="openMyMenuAnimals()" (mouseleave)="closeMyMenuAnimals()">`
will cause infinite loop.

    <button mat-button #myAnymals [matMenuTriggerFor]="animals" (mouseenter)="openMyMenuAnimals()" (mouseleave)="closeMyMenuAnimals()">
      <i class="fa-solid fa-hippo"></i>
      Animal index
    </button>
    <mat-menu #animals="matMenu" overlapTrigger="false" [hasBackdrop]="false">     <===== `[hasBackdrop]="false"` to prevent infinite loop of menu close and open.
      <section (mouseenter)="openMyMenuAnimals()" (mouseleave)="closeMyMenuAnimals()">
        <button mat-menu-item [matMenuTriggerFor]="vertebrates">Vertebrates</button>
        <button mat-menu-item [matMenuTriggerFor]="invertebrates">Invertebrates</button>
      </section>
    </mat-menu>

### 3. The first `mat-menu` must have `overlapTrigger="false"` to look good

## Magic `as` - To give member properties datatypes. To avoid using `data['property']`. To use `data.property`

Use `as` to give member properties datatypes. To avoid using `data['property']`. To use `data.property`

    const state = this.router.lastSuccessfulNavigation?.extras.state as {
      title: string
    };

    this.title = state?.title;

## `lastSuccessfulNavigation` vs `getCurrentNavigation()`

 1. `lastSuccessfulNavigation` can be used inside `ngOnInit()`.
 2. `getCurrentNavigation()` must be used inside constructor.

    /**
     * !!! Trick !!!
     * this.router.getCurrentNavigation() must be used inside constructor. Otherwise, it will always be always null;
     */
    const state = this.router.lastSuccessfulNavigation?.extras.state as {
      title: string
    };

    this.title = state?.title;

## `css` display close icon on top-right corner of each image

    # 1. The parent element must use `position: relative;`.
    # 2. The child element must use `position: absolute; top: 0px; right: 0px;`.
    <style>
      #content {
        position: relative;
      }
      #content img {
        position: absolute;
        top: 0px;
        right: 0px;
      }
    </style>
    
    <div id="content">
      <img src="images/ribbon.png" class="ribbon" alt="" />
      <div>some text...</div>
    </div>

## Chrome enable `console.debug()`

    Chrome :: F12 (to open debug/devtools pane) :: (click) Console (Tab) :: (top-right corner of devtools pane) Default levels (dropdown) :: Verbose :: (It will show) All levels

## Firefox disable source map error

    Firefox :: F12 (To toggle on Developer Tools) :: Top right corner of Developer Tools: ... x :: (Click on three dots ...) :: Settings
            :: Advanced Settings :: (Uncheck) Enable Source Maps  

## Set default dropdown select option value

    # `find-match.component.ts`:
    this.findMatchService.getCategoryConditions(this.title).pipe(
      takeUntil(this.destroyed$)
    ).subscribe({
      next: (response: CategoryConditions) => {
        console.debug('FindMatchComponent getCategoryConditions()', response);
        this.category = response.category;
        /**
         * !!! Trick !!!
         * Default dropdown value is set here!
         */
        this.formGroup.get('category')?.setValue(this.category);  // <============== Set default here!
      },
      ...

    # `find-match.component.html`:
    <select fxFlex="18em" [compareWith]="nmsService.compareObjects" formControlName="category" name="category"
      (change)="changeCategory(); onKeyUp()" [class.invalid]="category == ''">
      @for(item of categories; track item) {
      <!-- ======== default dropdown is set in component.ts ======== -->
      <!-- this.formGroup.get('category')?.setValue(this.category); -->
      <!-- ======== default dropdown is set in component.ts ======== -->
      <option [value]="item.category" [selected]="item.category === category">
        {{item.category | titlecase}}
      </option>
      }
    </select>

## `MatTooltipModule`

    export declare type TooltipPosition = 'left' | 'right' | 'above' | 'below' | 'before' | 'after';

## `MatSort` will not work if it is inside `*ngIf` or `@if`

**!!! Trick !!!**

1. If the table is inside `*ngIf` of `@if`, it won't be working.
2. `matSortDisableClear="true"` --- always. Otherwise, the table header has a state without sort arrow.

## `<button mat-raised-button>` height fix: `style="padding-top: 0.5em; padding-bottom: 0.5em;"`

If `<button mat-raised-button>` heght too short, add `style="padding-top: 0.5em; padding-bottom: 0.5em;"`

    <button mat-raised-button color="primary" (click)="onSubmit()" style="padding-top: 0.5em; padding-bottom: 0.5em;">
      Search
    </button>

## Dialog window data incorrect

!!! Trick !!!

1. Create and assign dialog data step by step, to prevent dialog window loss sync with data.
2. If create data inline inside open(ConditionDialog, {data: {...}}), dialog window will open without data.

## All `GET` `@PathVariable`s must be urlsafe

Use `this.nmsService.toBase64Urlsafe(title)`.

    /**
     * `src/app/sell/find-match/find-match.service.ts`:
     */
    
    private getConditionsByTitleUrl = '/spring/shein/getConditionsByTitle/{title}';

    const base64Title = this.nmsService.toBase64Urlsafe(title);
    const url = env.baseUrl + this.getItemsForSellerUrl.replace('{sellerUsername}', username);

    /**
     * `src\main\java\com\learn\shein\mongo\resources\MongoResource.java`
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/getConditionsByTitle/{title}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CategoryConditions> getConditionsByTitle(@PathVariable("title") String title)
        throws Exception {
        log.debug("Enter.... title: {}", () -> title);

        String newTitle = Base64Utils.decodeUrlsafeInput(title);

        log.debug("Enter.... new title: {}", () -> newTitle);
