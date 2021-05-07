import { TestBed } from '@angular/core/testing';

import { DatesUtilitiesService } from './dates-utilities.service';

describe('DatesUtilitiesService', () => {
  let service: DatesUtilitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DatesUtilitiesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
