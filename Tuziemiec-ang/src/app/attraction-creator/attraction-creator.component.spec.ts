import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttractionCreatorComponent } from './attraction-creator.component';

describe('AttractionCreatorComponent', () => {
  let component: AttractionCreatorComponent;
  let fixture: ComponentFixture<AttractionCreatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AttractionCreatorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttractionCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
