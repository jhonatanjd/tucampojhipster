import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProducerDetailComponent } from './producer-detail.component';

describe('Producer Management Detail Component', () => {
  let comp: ProducerDetailComponent;
  let fixture: ComponentFixture<ProducerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProducerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ producer: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProducerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProducerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load producer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.producer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
