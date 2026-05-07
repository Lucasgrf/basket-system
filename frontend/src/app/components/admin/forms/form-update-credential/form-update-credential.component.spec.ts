import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormUpdateCredentialComponent } from './form-update-credential.component';

describe('FormUpdateCredentialComponent', () => {
  let component: FormUpdateCredentialComponent;
  let fixture: ComponentFixture<FormUpdateCredentialComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormUpdateCredentialComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormUpdateCredentialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
