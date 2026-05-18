import { Component, HostBinding, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { slideInDownAnimation } from '../animations/animations';
import { Hero, HeroService } from './hero.service';


@Component({
    templateUrl: './hero-detail.component.html',
    animations: [slideInDownAnimation]
})
export class HeroDetailComponent implements OnInit {
    @HostBinding('@routeAnimation') routeAnimation = true;
    @HostBinding('style.display') display = 'block';
    // @HostBinding('style.position') position = 'absolute';

    hero: Hero;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private service: HeroService
    ) { }

    ngOnInit() {
        this.route.params
            // (+) converts string 'id' to a number
            .pipe(
                switchMap((params: Params) => this.service.getHero(+params['id']))  // (+) converts string 'id' to a number
            )
            .subscribe((hero: Hero) => this.hero = hero);
    }

    gotoHeroes() {
        const heroId = this.hero ? this.hero.id : null;
        // Pass along the hero id if available
        // so that the HeroList component can select that hero.
        // Include a junk 'foo' property for fun.
        this.router.navigate(['/heroes', { id: heroId, foo: 'foo' }]);
    }
}
