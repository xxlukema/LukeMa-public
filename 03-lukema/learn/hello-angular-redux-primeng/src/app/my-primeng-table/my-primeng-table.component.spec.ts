import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyPrimengTableComponent } from './my-primeng-table.component';

describe('MyPrimengTableComponent', () => {
  let component: MyPrimengTableComponent;
  let fixture: ComponentFixture<MyPrimengTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyPrimengTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyPrimengTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
