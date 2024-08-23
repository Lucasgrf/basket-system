import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDeleteTeamComponent } from './form-delete-team.component';

describe('FormDeleteTeamComponent', () => {
  let component: FormDeleteTeamComponent;
  let fixture: ComponentFixture<FormDeleteTeamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormDeleteTeamComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormDeleteTeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
