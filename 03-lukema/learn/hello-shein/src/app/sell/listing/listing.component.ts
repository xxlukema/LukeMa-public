import { LoadingModule } from '@/app/utils/loading/loading.module';
import { NmsService } from '@/app/utils/services/nms.service';
import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { NavigationExtras, Router, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@ngbracket/ngx-layout';
import { Subject } from 'rxjs';


@Component({
  selector: 'app-listing',
  standalone: true,
  imports: [CommonModule, FlexLayoutModule, MatInputModule,
    RouterModule,
    MatButtonModule,
    FormsModule,
    LoadingModule,
    ReactiveFormsModule
  ],
  providers: [
    TitleCasePipe
  ],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.scss'
})
export class ListingComponent implements OnInit, OnDestroy {

  constructor(public router: Router,
    // private eventService: EventService
    private nmsService: NmsService,
    private titleCasePipe: TitleCasePipe
  ) { }

  private readonly destroyed$ = new Subject<void>();

  loading = false;

  title?: string | null;

  formGroup = new FormGroup({
    title: new FormControl(this.title, [])
  });

  onTitleKeyUp() {
    if (this.formGroup.get('title')) {
      const val = this.titleCasePipe.transform(this.formGroup.get('title')?.value);
      this.formGroup.get('title')?.setValue(val);
    }
  }

  ngOnInit() {
    console.log('ListingComponent ngOnInit() called.');
    this.title = sessionStorage.getItem('titile');
    /*
    this.eventService.emitEvent({
    });
    */
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('ListingComponent ngOnDestroy() called.');

    this.destroyed$.next();
    this.destroyed$.complete();
  }

  gohome() {
    this.router.navigate(['/home']);
  }

  onSubmit() {

    let title = this.formGroup.get('title')?.value;

    title = this.nmsService.removeDuplicatedWhiteSpaces(title);

    if (title) {
      sessionStorage.setItem('title', title);
    }

    const navigationExtras: NavigationExtras = {
      state: {
        title
      }
    };

    this.router.navigate(['/sell/findmatch'], navigationExtras);
  }

  onKeyUp() {
  }
}
