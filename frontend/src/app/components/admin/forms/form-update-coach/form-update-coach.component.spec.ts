import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormUpdateCoachComponent } from './form-update-coach.component';

describe('FormUpdateCoachComponent', () => {
  let component: FormUpdateCoachComponent;
  let fixture: ComponentFixture<FormUpdateCoachComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormUpdateCoachComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormUpdateCoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
