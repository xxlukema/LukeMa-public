import { ItemService } from '@/app/item/item.service';
import { LoadingModule } from '@/app/utils/loading/loading.module';
import { CommonModule, DecimalPipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { Subject } from 'rxjs';


@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    LoadingModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule
  ],
  providers: [
    ItemService,
    DecimalPipe
  ],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.scss'
})
export class CheckoutComponent implements OnInit, OnDestroy {

  constructor() { }

  private readonly destroyed$ = new Subject<void>();

  loading = false;

  ngOnInit(): void {
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('SellComponent ngOnDestroy() called.');

    this.destroyed$.next();
    this.destroyed$.complete();
  }


}
