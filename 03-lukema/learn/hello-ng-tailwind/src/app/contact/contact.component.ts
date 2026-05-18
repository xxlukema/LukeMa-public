import { HttpClient } from '@angular/common/http';
import { Component, DestroyRef, inject, OnDestroy, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { catchError } from 'rxjs';
import { UserResponse, UserService } from './user.service';
import { LoadingComponent } from '../utils/loading/loading.component';


@Component({
  standalone: true,
  selector: 'app-contact',
  imports: [
    LoadingComponent,
  ],
  providers: [
    HttpClient,
  ],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent implements OnInit, OnDestroy {

  constructor(
    private readonly userService: UserService
  ) {
    console.log('ContactComponent', 'constructor');
  }

  loading = false;
  data!: UserResponse;
  user!: UserResponse;

  private readonly destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    console.log('ContactComponent', 'onInit - get data from service');

    this.loading = true;
    this.userService.getData().pipe(
      takeUntilDestroyed(this.destroyRef),
      catchError((err) => {
        console.error('ContactComponent', 'Error fetching data', err);
        return [err];
      })
    ).subscribe({
      next: (data) => {
        // this.loading = false;
        console.log('ContactComponent', 'data', data);
        this.user = data;
      },
      error: (err) => {
        this.loading = false;
        console.error('ContactComponent', 'Error fetching data', err);
      },
      complete: () => {
        this.loading = false;
        console.log('ContactComponent', 'complete');
      }
    });
  }

  ngOnDestroy(): void {
    console.log('ContactComponent', 'onDestroy');
    this.destroyRef.onDestroy(() => {
      // Place cleanup logic here if needed
      console.log('ContactComponent', 'Cleanup logic executed.');
    });
  }
}
