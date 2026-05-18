import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyMatTableComponent } from './my-mat-table.component';

describe('MyMatTableComponent', () => {
  let component: MyMatTableComponent;
  let fixture: ComponentFixture<MyMatTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyMatTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyMatTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
