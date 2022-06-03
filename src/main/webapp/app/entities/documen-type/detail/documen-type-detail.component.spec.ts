import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumenTypeDetailComponent } from './documen-type-detail.component';

describe('DocumenType Management Detail Component', () => {
  let comp: DocumenTypeDetailComponent;
  let fixture: ComponentFixture<DocumenTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DocumenTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ documenType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DocumenTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DocumenTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documenType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.documenType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
