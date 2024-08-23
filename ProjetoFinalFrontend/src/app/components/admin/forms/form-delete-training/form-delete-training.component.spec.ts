import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDeleteTrainingComponent } from './form-delete-training.component';

describe('FormDeleteTrainingComponent', () => {
  let component: FormDeleteTrainingComponent;
  let fixture: ComponentFixture<FormDeleteTrainingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormDeleteTrainingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormDeleteTrainingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
