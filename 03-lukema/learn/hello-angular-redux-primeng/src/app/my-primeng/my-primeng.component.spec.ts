import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyPrimengComponent } from './my-primeng.component';

describe('PrimengComponent', () => {
  let component: MyPrimengComponent;
  let fixture: ComponentFixture<MyPrimengComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyPrimengComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyPrimengComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
