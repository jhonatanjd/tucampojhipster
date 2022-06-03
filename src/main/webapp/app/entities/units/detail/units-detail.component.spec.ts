import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UnitsDetailComponent } from './units-detail.component';

describe('Units Management Detail Component', () => {
  let comp: UnitsDetailComponent;
  let fixture: ComponentFixture<UnitsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UnitsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ units: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UnitsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UnitsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load units on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.units).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
