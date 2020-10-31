import { TestBed } from '@angular/core/testing';

import { MessageResultService } from './message-result.service';

describe('MessageResultServiceService', () => {
  let service: MessageResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MessageResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
