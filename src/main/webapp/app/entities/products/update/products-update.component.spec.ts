import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductsService } from '../service/products.service';
import { IProducts, Products } from '../products.model';
import { ISale } from 'app/entities/sale/sale.model';
import { SaleService } from 'app/entities/sale/service/sale.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IOrderDetai } from 'app/entities/order-detai/order-detai.model';
import { OrderDetaiService } from 'app/entities/order-detai/service/order-detai.service';

import { ProductsUpdateComponent } from './products-update.component';

describe('Products Management Update Component', () => {
  let comp: ProductsUpdateComponent;
  let fixture: ComponentFixture<ProductsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productsService: ProductsService;
  let saleService: SaleService;
  let categoryService: CategoryService;
  let orderDetaiService: OrderDetaiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductsUpdateComponent],
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
      .overrideTemplate(ProductsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productsService = TestBed.inject(ProductsService);
    saleService = TestBed.inject(SaleService);
    categoryService = TestBed.inject(CategoryService);
    orderDetaiService = TestBed.inject(OrderDetaiService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Sale query and add missing value', () => {
      const products: IProducts = { id: 456 };
      const sale: ISale = { id: 14640 };
      products.sale = sale;

      const saleCollection: ISale[] = [{ id: 40527 }];
      jest.spyOn(saleService, 'query').mockReturnValue(of(new HttpResponse({ body: saleCollection })));
      const additionalSales = [sale];
      const expectedCollection: ISale[] = [...additionalSales, ...saleCollection];
      jest.spyOn(saleService, 'addSaleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ products });
      comp.ngOnInit();

      expect(saleService.query).toHaveBeenCalled();
      expect(saleService.addSaleToCollectionIfMissing).toHaveBeenCalledWith(saleCollection, ...additionalSales);
      expect(comp.salesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Category query and add missing value', () => {
      const products: IProducts = { id: 456 };
      const category: ICategory = { id: 60257 };
      products.category = category;

      const categoryCollection: ICategory[] = [{ id: 98505 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ products });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(categoryCollection, ...additionalCategories);
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrderDetai query and add missing value', () => {
      const products: IProducts = { id: 456 };
      const orderDetai: IOrderDetai = { id: 81081 };
      products.orderDetai = orderDetai;

      const orderDetaiCollection: IOrderDetai[] = [{ id: 8704 }];
      jest.spyOn(orderDetaiService, 'query').mockReturnValue(of(new HttpResponse({ body: orderDetaiCollection })));
      const additionalOrderDetais = [orderDetai];
      const expectedCollection: IOrderDetai[] = [...additionalOrderDetais, ...orderDetaiCollection];
      jest.spyOn(orderDetaiService, 'addOrderDetaiToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ products });
      comp.ngOnInit();

      expect(orderDetaiService.query).toHaveBeenCalled();
      expect(orderDetaiService.addOrderDetaiToCollectionIfMissing).toHaveBeenCalledWith(orderDetaiCollection, ...additionalOrderDetais);
      expect(comp.orderDetaisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const products: IProducts = { id: 456 };
      const sale: ISale = { id: 1424 };
      products.sale = sale;
      const category: ICategory = { id: 37609 };
      products.category = category;
      const orderDetai: IOrderDetai = { id: 67943 };
      products.orderDetai = orderDetai;

      activatedRoute.data = of({ products });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(products));
      expect(comp.salesSharedCollection).toContain(sale);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.orderDetaisSharedCollection).toContain(orderDetai);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Products>>();
      const products = { id: 123 };
      jest.spyOn(productsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ products });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: products }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productsService.update).toHaveBeenCalledWith(products);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Products>>();
      const products = new Products();
      jest.spyOn(productsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ products });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: products }));
      saveSubject.complete();

      // THEN
      expect(productsService.create).toHaveBeenCalledWith(products);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Products>>();
      const products = { id: 123 };
      jest.spyOn(productsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ products });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productsService.update).toHaveBeenCalledWith(products);
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

    describe('trackCategoryById', () => {
      it('Should return tracked Category primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCategoryById(0, entity);
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
