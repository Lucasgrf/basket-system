import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDeleteCoachComponent } from './form-delete-coach.component';

describe('FormDeleteCoachComponent', () => {
  let component: FormDeleteCoachComponent;
  let fixture: ComponentFixture<FormDeleteCoachComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormDeleteCoachComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormDeleteCoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
