import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormAddPlayerComponent } from './form-add-player.component';

describe('FormAddPlayerComponent', () => {
  let component: FormAddPlayerComponent;
  let fixture: ComponentFixture<FormAddPlayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormAddPlayerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormAddPlayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
