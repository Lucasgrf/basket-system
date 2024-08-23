import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDeleteUserComponent } from './form-delete-user.component';

describe('FormDeleteUserComponent', () => {
  let component: FormDeleteUserComponent;
  let fixture: ComponentFixture<FormDeleteUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormDeleteUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormDeleteUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
