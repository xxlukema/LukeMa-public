import { TestBed } from '@angular/core/testing';

import { MyMatTableService } from './my-mat-table.service';

describe('MyMatTableService', () => {
  let service: MyMatTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyMatTableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
