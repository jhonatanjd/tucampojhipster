import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WayToPayDetailComponent } from './way-to-pay-detail.component';

describe('WayToPay Management Detail Component', () => {
  let comp: WayToPayDetailComponent;
  let fixture: ComponentFixture<WayToPayDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WayToPayDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ wayToPay: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(WayToPayDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WayToPayDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load wayToPay on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.wayToPay).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
