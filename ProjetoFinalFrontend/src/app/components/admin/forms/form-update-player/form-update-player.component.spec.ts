import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormUpdatePlayerComponent } from './form-update-player.component';

describe('FormUpdatePlayerComponent', () => {
  let component: FormUpdatePlayerComponent;
  let fixture: ComponentFixture<FormUpdatePlayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormUpdatePlayerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormUpdatePlayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
