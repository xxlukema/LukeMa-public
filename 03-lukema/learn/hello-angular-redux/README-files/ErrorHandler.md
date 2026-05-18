# Angular Error Handler

Angular Errors Flow In This Sequence:

1. HttpClient Interceptor Error Handler Service
2. `NmsService.handleError` through pipe()
3. Error Handler in Component
4. **Unhandler Exceptions** go to Gloable Error Handler

## 1. HttpClient Interceptor Error Handler Service Processes Error The First

    src\app\utils\http-Interceptor.service.ts

## 2. `NmsService.handleError`

    src\app\utils\nms.service.ts:

    doGetTyped(): Observable<UserResponse> {
      return this.httpClient.get<UserResponse>(env.testGetUrl, this.nmsService.httpOptions).pipe(
        catchError(this.nmsService.handleError)
      );
    }

## 3. Error Handler in Component Handles Exception The Second

    src\app\my-http\my-http.component.ts

      doGetTyped(): void {
        const channel$ = this.myHttpService.doGetTyped().subscribe({
          next: (data) => {
            channel$.unsubscribe();
          },
          error: (error: HttpErrorResponse) => {
            console.error('=== 3 === GetTyped Observer got an error: ' + error);
            channel$.unsubscribe();
          }
        });
      }

## 4. If An Error/Exception Is Not Handled In Component, Gloable Error Handler Will Kick In

    src\app\utils\global-error-handler.service.ts
