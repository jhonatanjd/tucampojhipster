import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrderDetaiDetailComponent } from './order-detai-detail.component';

describe('OrderDetai Management Detail Component', () => {
  let comp: OrderDetaiDetailComponent;
  let fixture: ComponentFixture<OrderDetaiDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderDetaiDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ orderDetai: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrderDetaiDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrderDetaiDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load orderDetai on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.orderDetai).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
