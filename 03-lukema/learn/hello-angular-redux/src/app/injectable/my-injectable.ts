import { Injectable } from '@angular/core';


@Injectable({
    providedIn: 'root'
})
export class MyInjectable {
    public globalString: string | null = null;
    public globalNumber = 18;
}
