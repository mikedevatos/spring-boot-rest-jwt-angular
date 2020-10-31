import { TestBed } from '@angular/core/testing';

import { popConfirmHandleCancelService } from './modal-confirm.service';

describe('ModalConfirmService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: popConfirmHandleCancelService = TestBed.get(popConfirmHandleCancelService);
    expect(service).toBeTruthy();
  });
});
