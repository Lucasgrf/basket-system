import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormUpdateTrainingComponent } from './form-update-training.component';

describe('FormUpdateTrainingComponent', () => {
  let component: FormUpdateTrainingComponent;
  let fixture: ComponentFixture<FormUpdateTrainingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormUpdateTrainingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormUpdateTrainingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
