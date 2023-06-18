import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GOVIDisplayerComponent } from './govidisplayer.component';

describe('GOVIDisplayerComponent', () => {
  let component: GOVIDisplayerComponent;
  let fixture: ComponentFixture<GOVIDisplayerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GOVIDisplayerComponent]
    });
    fixture = TestBed.createComponent(GOVIDisplayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
