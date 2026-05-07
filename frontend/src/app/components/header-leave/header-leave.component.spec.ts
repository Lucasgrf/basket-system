import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderLeaveComponent } from './header-leave.component';

describe('HeaderLeaveComponent', () => {
  let component: HeaderLeaveComponent;
  let fixture: ComponentFixture<HeaderLeaveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderLeaveComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderLeaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
