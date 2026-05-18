
import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';


@Component({
    selector: 'app-form',
    templateUrl: './form.component.html',
    styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit, OnDestroy {

    routerSubscription$: Subscription;

    channel$?: Subscription;

    constructor(private httpClient: HttpClient, private router: Router) {
        /**
         * This is for every click of router link, it will trigger the cpmponent refresh.
         *
         * If 'this.routerSubscription$.unsubscribe();' is not called, subscriber will keep getting
         * router events even if user leaves this touted view.
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

    value = false;

    @ViewChild('buttonOne')
    buttonOne!: HTMLButtonElement;

    @ViewChild('buttonTwo')
    buttonTwo!: HTMLButtonElement;

    buttonLabel?: string;

    buttonOneClick() {
        this.value = !this.value;
    }

    /**
     * in: HTMLButtonElement
     */
    buttonTwoClick() {
        this.buttonOne.click();
    }

    whichButton(input: HTMLButtonElement) {
        this.buttonLabel = input.textContent?.toString();
        console.log('input.value', this.buttonLabel);
    }

    // luke_streaming_get_url = environment.helloSslBaseUrl + '/spring/stream';

    /**
     * Called whenever entering the page/template.
     */
    ngOnInit(): void {
        console.log('HttpComponent ngOnInit() called.');

        // this.postInput = JSON.stringify(this.postDataObject, undefined, 2);
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
    }

    refresh() {
        console.log('HttpComponent refresh()');

        this.clearAll();
    }

    clearAll(): void {
        this.getResult = '(GET result here.)';
        this.postResult = '(POST result here.)';
        this.completeMsg = '(Complete message here.)';
        this.errorMsg = '(Error message here.)';
        this.lukeStreaming = '(Luke Streaming data here.)';
    }

}
