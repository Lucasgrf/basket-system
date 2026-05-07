import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormUpdateTeamComponent } from './form-update-team.component';

describe('FormUpdateTeamComponent', () => {
  let component: FormUpdateTeamComponent;
  let fixture: ComponentFixture<FormUpdateTeamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormUpdateTeamComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormUpdateTeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
