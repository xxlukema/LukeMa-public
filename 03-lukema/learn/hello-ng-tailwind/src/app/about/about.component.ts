import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserResponse } from '../contact/user.service';
import { LoadingComponent } from '../utils/loading/loading.component';

@Component({
  selector: 'app-about',
  imports: [
    LoadingComponent
  ],
  templateUrl: './about.component.html',
  styleUrl: './about.component.css'
})
export class AboutComponent implements OnInit, OnDestroy {
  constructor(
    private readonly route: ActivatedRoute
  ) {
    console.log('AboutComponent', 'constructor');
  }

  loading = false
  user!: UserResponse

  ngOnInit(): void {
    console.log('AboutComponent', 'onInit - get user from route');

    /**
    this.route.data.subscribe(data => {
      this.user = data['user'];
      console.log('AboutComponent', 'user', this.user);
      console.log('AboutComponent', 'data', data);
    });
    */

    this.loading = true;

    this.route.data.subscribe({
      next: (data) => {
        this.user = data['user'];
        this.loading = false;
        console.log('AboutComponent', 'user', this.user);
        console.log('AboutComponent', 'data', data);
      },
      error: (err) => {
        console.error('AboutComponent', 'Error fetching user data', err);
      },
      complete: () => {
        this.loading = false;
        console.log('AboutComponent', 'complete ============');
      }
    });

    /**
     * this.user = this.route.snapshot.data['user']
     * this.loading = false;
     */
  }

  ngOnDestroy(): void {
    console.log('AboutComponent', 'onDestroy');
  }


}
