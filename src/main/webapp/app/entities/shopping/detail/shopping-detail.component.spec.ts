import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppingDetailComponent } from './shopping-detail.component';

describe('Shopping Management Detail Component', () => {
  let comp: ShoppingDetailComponent;
  let fixture: ComponentFixture<ShoppingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShoppingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ shopping: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ShoppingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ShoppingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load shopping on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.shopping).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
