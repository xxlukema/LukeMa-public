# Constructor Injected Instances **CANNOT** Be Accessed During Class Member Initialization

`this.sanitizer` is `undefined` outside `ngOninit()` function (Angular, TS)

      src\app\injectable\injectable.component.ts:

      myHtml: SafeHtml = {};

      /**
       * Wrong! ==> Will get "TypeError: this.domSanitizer is undefined"
       *
       * 'private readonly domSanitizer: DomSanitizer' is injected in constructor. At membership initilization stage, 'this.domSanitizer' has not
       * been injected yet.
       *
       * Solution: Do this inside ngOnInit()
       */
      // myHtml = this.domSanitizer.bypassSecurityTrustHtml(this.originalHtml);
    
      ngOnInit(): void {
        /**
         * Correct: access 'this.domSanitizer' inside 'ngOnInit()' to avoid "TypeError: this.domSanitizer is undefined"
         */
        this.myHtml = this.domSanitizer.bypassSecurityTrustHtml(this.originalHtml);
      }
