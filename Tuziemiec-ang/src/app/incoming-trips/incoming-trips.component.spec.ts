import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IncomingTripsComponent } from './incoming-trips.component';

describe('IncomingTripsComponent', () => {
  let component: IncomingTripsComponent;
  let fixture: ComponentFixture<IncomingTripsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IncomingTripsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IncomingTripsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
