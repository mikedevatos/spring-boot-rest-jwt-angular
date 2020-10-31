import { TestBed } from '@angular/core/testing';

import { SaveUserAuthService } from './save-user-auth.service';

describe('SaveStateService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SaveUserAuthService = TestBed.get(SaveUserAuthService);
    expect(service).toBeTruthy();
  });
});
