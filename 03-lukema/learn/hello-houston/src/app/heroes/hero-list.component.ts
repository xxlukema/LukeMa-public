// TODO SOMEDAY: Feature Componetized like CrisisCenter
import { Component, HostBinding, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { slideInRightAnimation } from '../animations/animations';
import { Hero, HeroService } from './hero.service';


@Component({
    templateUrl: './hero-list.component.html',
    animations: [slideInRightAnimation]
})
export class HeroListComponent implements OnInit {

    @HostBinding('@routeAnimation') routeAnimation = true;
    @HostBinding('style.display') display = 'block';

    heroes: Observable<Hero[]>;

    private selectedId: number;

    constructor(
        private heroService: HeroService,
        private route: ActivatedRoute,
        private router: Router
    ) { }

    ngOnInit() {
        this.heroes = this.route.params
            .pipe(
                switchMap((params: Params) => {
                    this.selectedId = +params['id'];
                    return this.heroService.getHeroes();
                })
            );
    }

    isSelected(hero: Hero) { return hero.id === this.selectedId; }

    onSelect(hero: Hero) {
        this.router.navigate(['/hero', hero.id]);
    }
}
