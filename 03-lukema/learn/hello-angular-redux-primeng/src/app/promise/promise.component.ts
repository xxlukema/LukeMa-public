import { Component, OnDestroy, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { PromiseService } from './promise.service';

export interface Greeting {
    id: number;
    content: string;
}

@Component({
    selector: 'app-promise',
    templateUrl: './promise.component.html',
    styleUrls: ['./promise.component.scss']
})
export class PromiseComponent implements OnInit, OnDestroy {

    constructor(private promiseService: PromiseService) { }

    getResult: string | undefined;
    completeMsg: string | undefined;
    errorMsg = '(Error message here.)';
    refreshing = false;


    /**
     * Called whenever entering the page/template.
     */
    ngOnInit(): void {
        console.log('PromiseComponent ngOnInit() called.');

        this.clearAll();
        // this.postInput = JSON.stringify(this.postDataObject);
    }

    /**
     * Called whenever leaving the page/template.
     */
    ngOnDestroy(): void {
        console.log('PromiseComponent ngOnDestroy() called.');
        /**
         * Unsbuscribe from Observable channels here.
         */
    }

    async doGetTypedPromise(): Promise<any> {
        this.refreshing = true;
        this.getResult = 'Please wait...';
        this.completeMsg = 'Waiting for complete...';
        this.errorMsg = 'Waiting for message...';

        console.log('promise -1- before call await promise.');

        try {
            await lastValueFrom(this.promiseService.doSlowGet())
                .then(
                    data => {
                        this.refreshing = false;

                        this.getResult = 'id=' + data.id + ', content=' + data.content;
                        this.completeMsg = 'GET completed';
                        this.errorMsg = 'GET no error';

                        console.log('promise -2- with await, it will wait for response, then finally, then next statement',
                            'received response from server.', this.getResult);
                        console.log('promise -2- without await, it will be printed at the latest. After finally and next statement',
                            'received response from server.', this.getResult);
                    },
                    err => {
                        this.refreshing = false;
                        this.completeMsg = 'GET comtains error.';

                        console.error('GetTypedPromise Observer got an error: ' + err);

                        if (err.error instanceof Error) {
                            console.log('GetTypedPromise Client-side error occured.');
                            this.errorMsg = 'GetTypedPromise Observer got Client-side error: ' + JSON.stringify(err);
                        } else {
                            console.log('GetTypedPromise Server-side error occured.');
                            if (err.status === '404') {
                                this.errorMsg = 'GetTypedPromise Observer got Server-side error: ' + err.statusText;
                            } else {
                                this.errorMsg = 'GetTypedPromise Observer got Server-side error: ' + JSON.stringify(err);
                            }
                        }
                    }
                );
        } finally {
            console.log('promise -3- with await, this line will be printed after receiving response.', 'finally.');
            console.log('promise -3- without await, this line will be printed before receiving response.', 'finally.');
        }

        console.log('promise -4- with await, this line will be printed after receiving response.', 'After await promise.');
        console.log('promise -4- without await, this line will be printed before receiving response.', 'After await promise.');
    }

    clearAll(): void {
        this.getResult = '(GET result here.)';
        this.completeMsg = '(Complete message here.)';
        this.errorMsg = '(Error message here.)';
    }

}
