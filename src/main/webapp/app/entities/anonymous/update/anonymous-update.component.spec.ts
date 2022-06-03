import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnonymousService } from '../service/anonymous.service';
import { IAnonymous, Anonymous } from '../anonymous.model';
import { ISale } from 'app/entities/sale/sale.model';
import { SaleService } from 'app/entities/sale/service/sale.service';

import { AnonymousUpdateComponent } from './anonymous-update.component';

describe('Anonymous Management Update Component', () => {
  let comp: AnonymousUpdateComponent;
  let fixture: ComponentFixture<AnonymousUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anonymousService: AnonymousService;
  let saleService: SaleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnonymousUpdateComponent],
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
      .overrideTemplate(AnonymousUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnonymousUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anonymousService = TestBed.inject(AnonymousService);
    saleService = TestBed.inject(SaleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Sale query and add missing value', () => {
      const anonymous: IAnonymous = { id: 456 };
      const sale: ISale = { id: 34982 };
      anonymous.sale = sale;

      const saleCollection: ISale[] = [{ id: 20415 }];
      jest.spyOn(saleService, 'query').mockReturnValue(of(new HttpResponse({ body: saleCollection })));
      const additionalSales = [sale];
      const expectedCollection: ISale[] = [...additionalSales, ...saleCollection];
      jest.spyOn(saleService, 'addSaleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anonymous });
      comp.ngOnInit();

      expect(saleService.query).toHaveBeenCalled();
      expect(saleService.addSaleToCollectionIfMissing).toHaveBeenCalledWith(saleCollection, ...additionalSales);
      expect(comp.salesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anonymous: IAnonymous = { id: 456 };
      const sale: ISale = { id: 36978 };
      anonymous.sale = sale;

      activatedRoute.data = of({ anonymous });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(anonymous));
      expect(comp.salesSharedCollection).toContain(sale);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Anonymous>>();
      const anonymous = { id: 123 };
      jest.spyOn(anonymousService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anonymous });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anonymous }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(anonymousService.update).toHaveBeenCalledWith(anonymous);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Anonymous>>();
      const anonymous = new Anonymous();
      jest.spyOn(anonymousService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anonymous });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anonymous }));
      saveSubject.complete();

      // THEN
      expect(anonymousService.create).toHaveBeenCalledWith(anonymous);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Anonymous>>();
      const anonymous = { id: 123 };
      jest.spyOn(anonymousService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anonymous });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anonymousService.update).toHaveBeenCalledWith(anonymous);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSaleById', () => {
      it('Should return tracked Sale primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSaleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
