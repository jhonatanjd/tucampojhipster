import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProducerService } from '../service/producer.service';
import { IProducer, Producer } from '../producer.model';

import { ProducerUpdateComponent } from './producer-update.component';

describe('Producer Management Update Component', () => {
  let comp: ProducerUpdateComponent;
  let fixture: ComponentFixture<ProducerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let producerService: ProducerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProducerUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProducerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProducerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    producerService = TestBed.inject(ProducerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const producer: IProducer = { id: 456 };

      activatedRoute.data = of({ producer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(producer));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Producer>>();
      const producer = { id: 123 };
      jest.spyOn(producerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ producer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: producer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(producerService.update).toHaveBeenCalledWith(producer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Producer>>();
      const producer = new Producer();
      jest.spyOn(producerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ producer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: producer }));
      saveSubject.complete();

      // THEN
      expect(producerService.create).toHaveBeenCalledWith(producer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Producer>>();
      const producer = { id: 123 };
      jest.spyOn(producerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ producer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(producerService.update).toHaveBeenCalledWith(producer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
