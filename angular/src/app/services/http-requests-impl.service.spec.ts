import { TestBed } from '@angular/core/testing';

import { HttpGetRequestRoomsServiceImpl } from './http-request-room-impl.service';

describe('HttpRequestsImplService', () => {
  let service: HttpGetRequestRoomsServiceImpl;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpGetRequestRoomsServiceImpl);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
