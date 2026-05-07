import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CredentialPageComponent } from './credential-page.component';

describe('CredentialPageComponent', () => {
  let component: CredentialPageComponent;
  let fixture: ComponentFixture<CredentialPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CredentialPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CredentialPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
