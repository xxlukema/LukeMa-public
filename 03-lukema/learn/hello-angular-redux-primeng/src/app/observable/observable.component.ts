import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ObservableService } from './observable.service';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';

export interface MyPojoObject {
  userId: number;
  title: string;
  body: string;
}

@Component({
  selector: 'app-observable',
  templateUrl: './observable.component.html',
  styleUrls: ['./observable.component.scss'],
})
export class ObservableComponent implements OnInit, OnDestroy {
  constructor(private observableService: ObservableService) {}

  channel$?: Subscription;

  postInput: string | undefined;
  postResultString: string | undefined;
  completeMsg: string | undefined;
  errorMsg = '(Error message here.)';
  refreshing = false;

  private postDataObject: MyPojoObject = {
    title: 'foo',
    body: 'bar',
    userId: 1,
  };

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('ObservableComponent ngOnInit() called.');

    this.clearAll();
    this.postInput = JSON.stringify(this.postDataObject);
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('ObservableComponent ngOnDestroy() called.');

    /**
     * Unsbuscribe from Observable channels here.
     */
    if (this.channel$) {
      this.channel$.unsubscribe();
    }
  }

  unsubscribe(): void {
    console.log('ObservableComponent unsubscribe() called.');

    this.refreshing = false;

    this.postResultString = 'Result: POST request canceled.';
    this.completeMsg = 'Complete: Post request canceld.';
    this.errorMsg = 'Error: Post request canceld';

    if (this.channel$) {
      this.channel$.unsubscribe();
    }
  }

  doPost(): void {
    this.refreshing = true;
    this.postResultString = 'Please wait...';
    this.completeMsg = 'Waiting for complete...';
    this.errorMsg = 'Waiting for error message...';

    console.log('observable -1- before call server.');

    this.channel$ = this.observableService
      .doSlowPost(this.postDataObject)
      .subscribe({
        next: (data: any) => {
          console.log(data);
          this.postResultString = JSON.stringify(data);
          console.log('observable -3- received server response', data);
        },
        error: (error: HttpErrorResponse) => {
          console.error('Post Observer got an error: ' + error);
          this.completeMsg = 'Post got error';

          if (error.error instanceof Error) {
            console.log('Post Client-side error occured.');
            this.errorMsg =
              'Post Observer got Client-side error: ' + JSON.stringify(error);
          } else {
            console.log('Post Server-side error occured.');
            if (error.status === 404) {
              this.errorMsg =
                'Post Observer got Server-side error: ' + error.statusText;
            } else {
              this.errorMsg =
                'Post Observer got Server-side error: ' + JSON.stringify(error);
            }
          }

          if (this.channel$) {
            this.channel$.unsubscribe();
          }

          this.refreshing = false;
        },
        complete: () => {
          console.log(
            'observable -4- finally',
            'Post Observer got a complete notification'
          );

          this.completeMsg = 'POST: Observer got a complete notification';
          this.errorMsg = 'POST: No error';

          if (this.channel$) {
            this.channel$.unsubscribe();
          }

          this.refreshing = false;
        },
      });

    console.log(
      'observable -2- After call to post. This line prints before finally block get executed.'
    );
  }

  clearAll(): void {
    this.postResultString = '(Post result here)';
    this.completeMsg = '(Complete message here.)';
    this.errorMsg = '(Error message here.)';
  }
}
