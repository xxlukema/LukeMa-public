import { env } from '@/environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { lastValueFrom, Subscription } from 'rxjs';
import { MyHttpService } from './my-http.service';

interface UserResponse {
  login: string;
  bio: string;
  company: string;
}

@Component({
  selector: 'app-http',
  templateUrl: './my-http.component.html',
  styleUrls: ['./my-http.component.scss'],
})
export class MyHttpComponent implements OnInit, OnDestroy {
  routerSubscription$?: Subscription;
  doGetLukeStreaming$?: Subscription;

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private myHttpService: MyHttpService
  ) {
    /**
     * This is for every click of router link, it will trigger the cpmponent refresh.
     */
    this.routerSubscription$ = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.refresh();
      }
    });
  }

  title?: string;
  getResult?: string;
  lukeStreaming?: string;
  postInput?: string;
  postResult?: string;
  completeMsg?: string;
  errorMsg = '(Error message here.)';
  getUrl = env.testGetUrl;

  // postUrl = env.testPostUrl;
  postUrl = env.helloBaseUrl + '/spring/security/featuredpost';
  // luke_streaming_get_url = env.helloSslBaseUrl + '/spring/stream';

  lukeStreamingGetUrl = env.helloBaseUrl + '/spring/stream';

  private postDataObject = {
    title: 'foo',
    body: 'bar',
    userId: 1,
  };

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('HttpComponent ngOnInit() called.');

    this.postInput = JSON.stringify(this.postDataObject, undefined, 2);
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('HttpComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
    if (this.routerSubscription$) {
      this.routerSubscription$.unsubscribe();
    }
    if (this.doGetLukeStreaming$) {
      this.doGetLukeStreaming$.unsubscribe();
    }
  }

  refresh() {
    console.log('HttpComponent refresh()');

    this.clearAll();
  }

  doPost(): void {
    const channel$ = this.myHttpService
      .doPost(this.postUrl, this.postDataObject)
      .subscribe({
        next: (data) => {
          console.log('my-http.component', 'response', data);

          this.postResult = JSON.stringify(data);
          channel$.unsubscribe();
        },
        error: (error: HttpErrorResponse) => {
          console.error('my-http.component', 'Post Observer got an error', error);

          if (error.error instanceof Error) {
            console.log('my-http.component', 'Post Client-side error occured.');
            this.errorMsg =
              'Post Observer got Client-side error: ' + JSON.stringify(error);
          } else {
            console.log('my-http.component', 'Post Server-side error occured.');
            if (error.status === 404) {
              this.errorMsg =
                'Post Observer got Server-side error: ' + error.statusText;
            } else {
              this.errorMsg =
                'Post Observer got Server-side error: ' + JSON.stringify(error);
            }
          }
          channel$.unsubscribe();
        },
        complete: () => {
          console.log(
            'my-http.component',
            'Post Observer got a complete notification'
          );
          this.completeMsg = 'Post Observer got a complete notification';
        },
      });
  }

  doGetLukeStreaming(): void {
    this.doGetLukeStreaming$ = this.httpClient
      .post(this.lukeStreamingGetUrl, {})
      .subscribe({
        next: (data) => {
          console.log(data);

          // this.lukeStreaming += 'Stream Observer got data: ' + data;
          // this.lukeStreaming = data;

          this.lukeStreaming =
            'Stream Observer got data: ' + JSON.stringify(data);

          // this.lukeStreaming = 'Stream Observer got data: ' + data.login + ' ' + data.bio;
        },
        error: (error: HttpErrorResponse) => {
          console.error('Stream Observer got an error: ' + error);

          if (error.error instanceof Error) {
            console.log('Stream Client-side error occured.');
            this.errorMsg =
              'Stream Observer got Client-side error: ' + JSON.stringify(error);
          } else {
            console.error('Stream Server-side error occured.', error);
            if (error.status === 404) {
              this.errorMsg =
                'Stream Observer got Server-side error: ' + error.statusText;
            } else {
              this.errorMsg =
                'Stream Observer got Server-side error: ' + JSON.stringify(error);
            }
          }
        },
       complete: () => {
          console.log('Stream Observer got a complete notification');

          this.completeMsg = 'Stream Observer got a complete notification';
        }
      });
  }

  doGetAny(): void {
    const channel$ = this.httpClient.get<string>(this.getUrl).subscribe({
      next: (data) => {
        console.log(data);

        // this.getResult += 'Observer got data: ' + data;

        this.getResult += '\n' + data;

        // this.getResult = 'Observer got data: ' + JSON.stringify(data);
        // this.getResult = 'Observer got data: ' + data.login + ' ' + data.bio;

        channel$.unsubscribe();
      },
      error: (error: HttpErrorResponse) => {
        console.error('GetAny Observer got an error: ' + error);

        if (error.error instanceof Error) {
          console.log('GetAny Client-side error occured.');
          this.errorMsg =
            'GetAny Observer got Client-side error: ' + JSON.stringify(error);
        } else {
          console.log('GetAny Server-side error occured.');
          if (error.status === 404) {
            this.errorMsg =
              'GetAny Observer got Server-side error: ' + error.statusText;
          } else {
            this.errorMsg =
              'GetAny Observer got Server-side error: ' + JSON.stringify(error);
          }
        }

        channel$.unsubscribe();
      },
      complete: () => {
        console.log('GetAny Observer got a complete notification');

        this.completeMsg = 'GetAny Observer got a complete notification';
      },
    });
  }

  doGetTyped(): void {
    const channel$ = this.httpClient.get<UserResponse>(this.getUrl).subscribe({
      next: (data) => {
        console.log(data.login + ' ' + data.bio + ' ' + data.company);

        this.getResult +=
          '\n' + data.login + ' ' + data.bio + ' ' + data.company;

        channel$.unsubscribe();
      },
      error: (error: HttpErrorResponse) => {
        console.error('GetTyped Observer got an error: ' + error);

        if (error.error instanceof Error) {
          console.log('GetTyped Client-side error occured.');
          this.errorMsg =
            'GetTyped Observer got Client-side error: ' + JSON.stringify(error);
        } else {
          console.log('GetTyped Server-side error occured.');
          if (error.status === 404) {
            this.errorMsg =
              'GetTyped Observer got Server-side error: ' + error.statusText;
          } else {
            this.errorMsg =
              'GetTyped Observer got Server-side error: ' +
              JSON.stringify(error);
          }
        }

        channel$.unsubscribe();
      },
      complete: () => {
        console.log('GetTyped Observer got a complete notification');

        this.completeMsg = 'GetTyped Observer got a complete notification';
      },
    });
  }

  async doGetTypedPromise() {
    await lastValueFrom(this.httpClient.get<UserResponse>(this.getUrl)).then(
      (data) => {
        console.log(data.login + ' ' + data.bio + ' ' + data.company);

        this.getResult +=
          '\n' + data.login + ' ' + data.bio + ' ' + data.company;
      },
      (err) => {
        console.error('GetTypedPromise Observer got an error: ' + err);

        if (err.error instanceof Error) {
          console.log('GetTypedPromise Client-side error occured.');
          this.errorMsg =
            'GetTypedPromise Observer got Client-side error: ' +
            JSON.stringify(err);
        } else {
          console.log('GetTypedPromise Server-side error occured.');
          if (err.status === '404') {
            this.errorMsg =
              'GetTypedPromise Observer got Server-side error: ' +
              err.statusText;
          } else {
            this.errorMsg =
              'GetTypedPromise Observer got Server-side error: ' +
              JSON.stringify(err);
          }
        }
      }
    );
  }

  clearAll(): void {
    this.getResult = '(GET result here.)';
    this.postResult = '(POST result here.)';
    this.completeMsg = '(Complete message here.)';
    this.errorMsg = '(Error message here.)';
    this.lukeStreaming = '(Luke Streaming data here.)';
  }
}
