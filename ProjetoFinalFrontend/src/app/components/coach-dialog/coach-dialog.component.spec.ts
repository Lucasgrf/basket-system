import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachDialogComponent } from './coach-dialog.component';

describe('CoachDialogComponent', () => {
  let component: CoachDialogComponent;
  let fixture: ComponentFixture<CoachDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoachDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoachDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
