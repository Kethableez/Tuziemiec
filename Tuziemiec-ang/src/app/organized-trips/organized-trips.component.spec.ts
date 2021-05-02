import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizedTripsComponent } from './organized-trips.component';

describe('OrganizedTripsComponent', () => {
  let component: OrganizedTripsComponent;
  let fixture: ComponentFixture<OrganizedTripsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganizedTripsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizedTripsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
