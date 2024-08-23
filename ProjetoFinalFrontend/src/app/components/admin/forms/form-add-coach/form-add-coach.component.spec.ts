import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormAddCoachComponent } from './form-add-coach.component';

describe('FormAddCoachComponent', () => {
  let component: FormAddCoachComponent;
  let fixture: ComponentFixture<FormAddCoachComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormAddCoachComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormAddCoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
