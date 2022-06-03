import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SaleService } from '../service/sale.service';
import { ISale, Sale } from '../sale.model';
import { IProducer } from 'app/entities/producer/producer.model';
import { ProducerService } from 'app/entities/producer/service/producer.service';
import { IOrderDetai } from 'app/entities/order-detai/order-detai.model';
import { OrderDetaiService } from 'app/entities/order-detai/service/order-detai.service';

import { SaleUpdateComponent } from './sale-update.component';

describe('Sale Management Update Component', () => {
  let comp: SaleUpdateComponent;
  let fixture: ComponentFixture<SaleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saleService: SaleService;
  let producerService: ProducerService;
  let orderDetaiService: OrderDetaiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SaleUpdateComponent],
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
      .overrideTemplate(SaleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saleService = TestBed.inject(SaleService);
    producerService = TestBed.inject(ProducerService);
    orderDetaiService = TestBed.inject(OrderDetaiService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Producer query and add missing value', () => {
      const sale: ISale = { id: 456 };
      const producer: IProducer = { id: 16415 };
      sale.producer = producer;

      const producerCollection: IProducer[] = [{ id: 22122 }];
      jest.spyOn(producerService, 'query').mockReturnValue(of(new HttpResponse({ body: producerCollection })));
      const additionalProducers = [producer];
      const expectedCollection: IProducer[] = [...additionalProducers, ...producerCollection];
      jest.spyOn(producerService, 'addProducerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      expect(producerService.query).toHaveBeenCalled();
      expect(producerService.addProducerToCollectionIfMissing).toHaveBeenCalledWith(producerCollection, ...additionalProducers);
      expect(comp.producersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrderDetai query and add missing value', () => {
      const sale: ISale = { id: 456 };
      const orderDetai: IOrderDetai = { id: 75476 };
      sale.orderDetai = orderDetai;

      const orderDetaiCollection: IOrderDetai[] = [{ id: 53095 }];
      jest.spyOn(orderDetaiService, 'query').mockReturnValue(of(new HttpResponse({ body: orderDetaiCollection })));
      const additionalOrderDetais = [orderDetai];
      const expectedCollection: IOrderDetai[] = [...additionalOrderDetais, ...orderDetaiCollection];
      jest.spyOn(orderDetaiService, 'addOrderDetaiToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      expect(orderDetaiService.query).toHaveBeenCalled();
      expect(orderDetaiService.addOrderDetaiToCollectionIfMissing).toHaveBeenCalledWith(orderDetaiCollection, ...additionalOrderDetais);
      expect(comp.orderDetaisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sale: ISale = { id: 456 };
      const producer: IProducer = { id: 54245 };
      sale.producer = producer;
      const orderDetai: IOrderDetai = { id: 21875 };
      sale.orderDetai = orderDetai;

      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sale));
      expect(comp.producersSharedCollection).toContain(producer);
      expect(comp.orderDetaisSharedCollection).toContain(orderDetai);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sale>>();
      const sale = { id: 123 };
      jest.spyOn(saleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sale }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(saleService.update).toHaveBeenCalledWith(sale);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sale>>();
      const sale = new Sale();
      jest.spyOn(saleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sale }));
      saveSubject.complete();

      // THEN
      expect(saleService.create).toHaveBeenCalledWith(sale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sale>>();
      const sale = { id: 123 };
      jest.spyOn(saleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saleService.update).toHaveBeenCalledWith(sale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProducerById', () => {
      it('Should return tracked Producer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProducerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackOrderDetaiById', () => {
      it('Should return tracked OrderDetai primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrderDetaiById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
