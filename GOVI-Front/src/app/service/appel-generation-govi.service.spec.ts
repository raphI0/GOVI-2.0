import { TestBed } from '@angular/core/testing';

import { AppelGenerationGoviService } from './appel-generation-govi.service';

describe('AppelGenerationGoviService', () => {
  let service: AppelGenerationGoviService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppelGenerationGoviService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
