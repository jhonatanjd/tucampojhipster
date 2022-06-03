import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UnitsService } from '../service/units.service';
import { IUnits, Units } from '../units.model';
import { IProducts } from 'app/entities/products/products.model';
import { ProductsService } from 'app/entities/products/service/products.service';

import { UnitsUpdateComponent } from './units-update.component';

describe('Units Management Update Component', () => {
  let comp: UnitsUpdateComponent;
  let fixture: ComponentFixture<UnitsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let unitsService: UnitsService;
  let productsService: ProductsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UnitsUpdateComponent],
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
      .overrideTemplate(UnitsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UnitsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    unitsService = TestBed.inject(UnitsService);
    productsService = TestBed.inject(ProductsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Products query and add missing value', () => {
      const units: IUnits = { id: 456 };
      const products: IProducts = { id: 6668 };
      units.products = products;

      const productsCollection: IProducts[] = [{ id: 61030 }];
      jest.spyOn(productsService, 'query').mockReturnValue(of(new HttpResponse({ body: productsCollection })));
      const additionalProducts = [products];
      const expectedCollection: IProducts[] = [...additionalProducts, ...productsCollection];
      jest.spyOn(productsService, 'addProductsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ units });
      comp.ngOnInit();

      expect(productsService.query).toHaveBeenCalled();
      expect(productsService.addProductsToCollectionIfMissing).toHaveBeenCalledWith(productsCollection, ...additionalProducts);
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const units: IUnits = { id: 456 };
      const products: IProducts = { id: 26267 };
      units.products = products;

      activatedRoute.data = of({ units });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(units));
      expect(comp.productsSharedCollection).toContain(products);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Units>>();
      const units = { id: 123 };
      jest.spyOn(unitsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ units });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: units }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(unitsService.update).toHaveBeenCalledWith(units);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Units>>();
      const units = new Units();
      jest.spyOn(unitsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ units });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: units }));
      saveSubject.complete();

      // THEN
      expect(unitsService.create).toHaveBeenCalledWith(units);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Units>>();
      const units = { id: 123 };
      jest.spyOn(unitsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ units });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(unitsService.update).toHaveBeenCalledWith(units);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProductsById', () => {
      it('Should return tracked Products primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
