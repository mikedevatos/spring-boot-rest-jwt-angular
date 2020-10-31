import { TestBed } from '@angular/core/testing';

import { AuthGuardRoleService } from './auth-guard-role.service';

describe('AuthGuardRoleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AuthGuardRoleService = TestBed.get(AuthGuardRoleService);
    expect(service).toBeTruthy();
  });
});
