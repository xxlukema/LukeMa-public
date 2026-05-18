import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { Subject } from 'rxjs';
import { LoadingModule } from '../utils/loading/loading.module';



@Component({
  selector: 'app-sell',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    MatButtonModule,
    MatCardModule,
    MatExpansionModule,
    LoadingModule,
    MatTooltipModule
  ],
  templateUrl: './sell.component.html',
  styleUrl: './sell.component.scss'
})
export class SellComponent implements OnInit, OnDestroy {

  constructor(public router: Router,
    // private eventService: EventService
  ) { }

  private readonly destroyed$ = new Subject<void>();

  @ViewChild('panel') public panel!: ElementRef<any>;

  isPanelZero = true;
  isPanelMax = false;

  loading = false;

  public moveLeft(): void {
    this.panel.nativeElement.scrollLeft -= 400;
    if (this.panel.nativeElement.scrollLeft === 0) {
      this.isPanelZero = true;
    } else {
      this.isPanelZero = false;
    }
    this.isPanelMax = false;
  }

  public moveRight(): void {
    const pos = this.panel.nativeElement.scrollLeft;
    this.panel.nativeElement.scrollLeft += 400;
    if (pos === this.panel.nativeElement.scrollLeft) {
      this.isPanelMax = true;
    } else {
      this.isPanelMax = false;
    }
    this.isPanelZero = false;
  }

  ngOnInit() {
    console.log('SellComponent ngOnInit() called.');
    /*
    this.eventService.emitEvent({
      isInSellingPage: true
    });
    */
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('SellComponent ngOnDestroy() called.');

    this.destroyed$.next();
    this.destroyed$.complete();
  }

  scrollTo($element: any): void {
    $element.scrollIntoView({ behavior: 'smooth', block: 'start', inline: 'nearest' });
  }

  gohome() {
    this.router.navigate(['/home']);
  }

  gotoListing() {
    console.log('---------goto listing.');
    this.router.navigate(['/sell/list']);
  }
}
