import { Component, OnDestroy, OnInit } from '@angular/core';
// import { mapTo } from 'rxjs/operators';
import { NavigationEnd, Router } from '@angular/router';
import { interval, Observable, of, Subscription } from 'rxjs';
// import { timer } from 'rxjs';
import { delay, map, switchMap, take } from 'rxjs/operators';


@Component({
    templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit, OnDestroy {
    message = 'Hello, Signin Page';

    nav$: Subscription;

    constructor(
        private router: Router,
    ) {
        this.nav$ = this.router.events.subscribe((e: any) => {   /** Trick 2/5 */
            if (e instanceof NavigationEnd) {
                this.initialiseInvites();
            }
        });
    }

    initialiseInvites() {
        // Set default values and re-fetch any data you need.     /** Trick 4/5 */
        console.log('initialiseInvites() clicked');
        // this.router.navigateByUrl('/home1');
    }

    ngOnDestroy() {
        console.log('HomeComponent ngOnDestroy()');
        if (this.nav$) {
            this.nav$.unsubscribe();          /** Trick 5/5 */
        }
    }

    ngOnInit(): void {
        console.log('HomeComponent ngOnDestroy()');

        this.message = 'Init message';
        this.simulatingHttpRequests();
        this.simulatingAngularFireStreams();
    }


    /**
     * 1. Short-lived streams like for example HTTP Requests, that only emit one value:
     *
     * 1.1. the HTTP observables are cold (or not live), meaning that they will not start emitting
     *      values until we subscribe to them
     * 1.2. these Observables only emit a single value or an error, and then after that they complete,
     *      so they are not long-lived Observables
     * 1.3. in most cases, we don't have to remember to unsubscribe from these Observables, because
     *      they will complete after emission
     *
     */
    simulatingHttpRequests() {
        console.log('simulating HTTP requests');

        const http1$ = this.simulateHttp('First', 1000);
        const http2$ = this.simulateHttp('2nd', 1000);

        http1$.subscribe(
            val => console.log(val),
            err => console.error(err),
            () => console.log('### http1$ "First" completed. ###')
        );

        http2$.subscribe(
            console.log,
            console.error,
            () => console.log('### http2$ "2nd" completed. ###')
        );

        /**
         * The following block is to test switchMap
         */
        const initiatorHttp$ = this.simulateHttp('Initiator', 1000);

        const successorHttp$ = initiatorHttp$.pipe(
            /**
             * switchMap operator is a great way of doing one HTTP request, and then using the output
             * of an initial request to do another request. This is a common way that we can use it.
             */
            switchMap(value => {
                console.log('Observed value: ' + value);
                console.log('### initiatorHttp$ completed. ###');
                return this.simulateHttp('Switched from Initiator to Successor. I am doing a different request here.', 2000);
            })
        );

        successorHttp$.subscribe(
            console.log,
            console.error,
            () => console.log('### successorHttp$ completed. ###')
        );
    }

    simulateHttp(desc: string, num: number): Observable<string> {
        return of(desc).pipe(
            delay(num)
        );
    }

    /**
     * 2. Long-lived AngularFire-like streams:
     *
     *    Long-lived streams such as the ones returned by AngularFire, which is an Angular library that
     *    provides services for interacting with the Firebase real-time database and authentication
     */
    simulatingAngularFireStreams() {
        const firebase1$ = this.simulateFirebase('Firebase', 8000);

        const firebaseResult$ = firebase1$.pipe(
            switchMap(value => {
                console.log('Observed value "' + value + '"');
                return this.simulateFirebase('switchMap observable "' + value + '"', 3000);
            })
        );

        /*
        const firebase2$ = this.simulateFirebase('Firebase Two', 4000);
        firebase2$.subscribe(
            console.log,
            console.error,
            () => console.log('firebase2$ completed')
        );
        */

        firebaseResult$.subscribe(
            console.log,
            console.error,
            () => console.log('###### firebaseResult$ completed. ######')
        );
    }

    simulateFirebase(desc: any, num: number): Observable<string> {
        return interval(num).pipe(
            take(10),
            map(item => desc + ' ' + item)
        );
    }

}
