import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionStorageService } from 'ngx-webstorage';


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    constructor(private sessionStorageService: SessionStorageService, private router: Router) { }

    fullname = '';
    password = '';
    error = '';

    ngOnInit(): void {
    }

    performLogin(): void {
        if (this.password === '12345') {
            this.sessionStorageService.store('fullname', this.fullname);
            this.router.navigate(['/home']);
        } else {
            this.error = 'Invalid password';
        }
    }
}
