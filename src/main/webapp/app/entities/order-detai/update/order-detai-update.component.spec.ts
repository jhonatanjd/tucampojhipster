import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrderDetaiService } from '../service/order-detai.service';
import { IOrderDetai, OrderDetai } from '../order-detai.model';

import { OrderDetaiUpdateComponent } from './order-detai-update.component';

describe('OrderDetai Management Update Component', () => {
  let comp: OrderDetaiUpdateComponent;
  let fixture: ComponentFixture<OrderDetaiUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderDetaiService: OrderDetaiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrderDetaiUpdateComponent],
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
      .overrideTemplate(OrderDetaiUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderDetaiUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderDetaiService = TestBed.inject(OrderDetaiService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const orderDetai: IOrderDetai = { id: 456 };

      activatedRoute.data = of({ orderDetai });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(orderDetai));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderDetai>>();
      const orderDetai = { id: 123 };
      jest.spyOn(orderDetaiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderDetai });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderDetai }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderDetaiService.update).toHaveBeenCalledWith(orderDetai);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderDetai>>();
      const orderDetai = new OrderDetai();
      jest.spyOn(orderDetaiService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderDetai });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderDetai }));
      saveSubject.complete();

      // THEN
      expect(orderDetaiService.create).toHaveBeenCalledWith(orderDetai);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrderDetai>>();
      const orderDetai = { id: 123 };
      jest.spyOn(orderDetaiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderDetai });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderDetaiService.update).toHaveBeenCalledWith(orderDetai);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
