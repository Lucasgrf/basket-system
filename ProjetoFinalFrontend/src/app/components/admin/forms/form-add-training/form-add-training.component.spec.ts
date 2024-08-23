import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormAddTrainingComponent } from './form-add-training.component';

describe('FormAddTrainingComponent', () => {
  let component: FormAddTrainingComponent;
  let fixture: ComponentFixture<FormAddTrainingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormAddTrainingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormAddTrainingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
