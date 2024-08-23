import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDeletePlayerComponent } from './form-delete-player.component';

describe('FormDeletePlayerComponent', () => {
  let component: FormDeletePlayerComponent;
  let fixture: ComponentFixture<FormDeletePlayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormDeletePlayerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormDeletePlayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
