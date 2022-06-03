import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DatsDetailComponent } from './dats-detail.component';

describe('Dats Management Detail Component', () => {
  let comp: DatsDetailComponent;
  let fixture: ComponentFixture<DatsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DatsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dats: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DatsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DatsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dats on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dats).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
