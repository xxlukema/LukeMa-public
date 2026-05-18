import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyMatProgressBarComponent } from './my-mat-progress-bar.component';

describe('MyMatProgressBarComponent', () => {
  let component: MyMatProgressBarComponent;
  let fixture: ComponentFixture<MyMatProgressBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyMatProgressBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyMatProgressBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
