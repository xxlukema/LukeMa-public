import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Injectable, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { RentProperty, RentPropertyService } from './rent-property.service';





@Injectable()
@Component({
    templateUrl: './add-rent-property.component.html',
    styleUrls: ['./add-rent-property.component.css']
})
export class AddRentPropertyComponent implements OnInit {

    private email = 'x.luke.ma@gmail.com';
    private phone = '832-588-7811';

    private url = environment.baseUrl + '/rent/addRentProperty';

    rentProperty: RentProperty;

    private httpHeaders = new HttpHeaders({
        'Content-Type': 'application/json'
    });

    private httpOptions = {
        headers: this.httpHeaders
    };

    constructor(
        public service: RentPropertyService,
        private httpClient: HttpClient
    ) { }

    ngOnInit(): void {

        const authtoken = localStorage.getItem('authtoken');
        if (authtoken) {
            this.httpOptions.headers = this.httpOptions.headers.set('Authorization', 'Token ' + authtoken);
        }

        this.rentProperty = new RentProperty('59b0dcfb5753540bdc949a42', '', '', new Date(), new Date());
    }

    save() {

        if (this.rentProperty.summary == null || this.rentProperty.summary.length < 3) {
            alert('Summary min length is 3 chars.');
            return;
        }

        if (this.rentProperty.details == null || this.rentProperty.details.length < 3) {
            alert('Details min length is 3 chars.');
            return;
        }

        this.httpClient.post<RentProperty>(this.url, this.rentProperty, this.httpOptions)
            .pipe(
                catchError((error: any) => throwError(() => error.json().error ?? 'Server error'))
            )
            .subscribe((data) => {
                const date = new Date();
                console.log(date); alert('Response: ' + date);
            });

    }

    cancel() {
        alert('Cancel');
    }
}
