# ng-icons

[ng-icons]<https://ng-icons.github.io/ng-icons/#/>

    npm i @ng-icons/core @ng-icons/bootstrap-icons @ng-icons/feather-icons @ng-icons/heroicons

## Code

    import { NgIcon, provideIcons } from '@ng-icons/core';
    import { featherAirplay } from '@ng-icons/feather-icons';
    import { heroUsers } from '@ng-icons/heroicons/outline';

    @Component({
        selector: 'app-root',
        templateUrl: './app.component.html',
        imports: [NgIcon],
        viewProviders: [provideIcons({ featherAirplay, heroUsers })]
    })
    export class AppComponent {}
