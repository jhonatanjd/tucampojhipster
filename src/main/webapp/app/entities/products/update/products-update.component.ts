import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProducts, Products } from '../products.model';
import { ProductsService } from '../service/products.service';
import { ISale } from 'app/entities/sale/sale.model';
import { SaleService } from 'app/entities/sale/service/sale.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IOrderDetai } from 'app/entities/order-detai/order-detai.model';
import { OrderDetaiService } from 'app/entities/order-detai/service/order-detai.service';

@Component({
  selector: 'tucam-products-update',
  templateUrl: './products-update.component.html',
})
export class ProductsUpdateComponent implements OnInit {
  isSaving = false;

  salesSharedCollection: ISale[] = [];
  categoriesSharedCollection: ICategory[] = [];
  orderDetaisSharedCollection: IOrderDetai[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    sale: [],
    category: [],
    orderDetai: [],
  });

  constructor(
    protected productsService: ProductsService,
    protected saleService: SaleService,
    protected categoryService: CategoryService,
    protected orderDetaiService: OrderDetaiService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ products }) => {
      this.updateForm(products);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const products = this.createFromForm();
    if (products.id !== undefined) {
      this.subscribeToSaveResponse(this.productsService.update(products));
    } else {
      this.subscribeToSaveResponse(this.productsService.create(products));
    }
  }

  trackSaleById(_index: number, item: ISale): number {
    return item.id!;
  }

  trackCategoryById(_index: number, item: ICategory): number {
    return item.id!;
  }

  trackOrderDetaiById(_index: number, item: IOrderDetai): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducts>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(products: IProducts): void {
    this.editForm.patchValue({
      id: products.id,
      name: products.name,
      sale: products.sale,
      category: products.category,
      orderDetai: products.orderDetai,
    });

    this.salesSharedCollection = this.saleService.addSaleToCollectionIfMissing(this.salesSharedCollection, products.sale);
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing(
      this.categoriesSharedCollection,
      products.category
    );
    this.orderDetaisSharedCollection = this.orderDetaiService.addOrderDetaiToCollectionIfMissing(
      this.orderDetaisSharedCollection,
      products.orderDetai
    );
  }

  protected loadRelationshipsOptions(): void {
    this.saleService
      .query()
      .pipe(map((res: HttpResponse<ISale[]>) => res.body ?? []))
      .pipe(map((sales: ISale[]) => this.saleService.addSaleToCollectionIfMissing(sales, this.editForm.get('sale')!.value)))
      .subscribe((sales: ISale[]) => (this.salesSharedCollection = sales));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing(categories, this.editForm.get('category')!.value)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.orderDetaiService
      .query()
      .pipe(map((res: HttpResponse<IOrderDetai[]>) => res.body ?? []))
      .pipe(
        map((orderDetais: IOrderDetai[]) =>
          this.orderDetaiService.addOrderDetaiToCollectionIfMissing(orderDetais, this.editForm.get('orderDetai')!.value)
        )
      )
      .subscribe((orderDetais: IOrderDetai[]) => (this.orderDetaisSharedCollection = orderDetais));
  }

  protected createFromForm(): IProducts {
    return {
      ...new Products(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      sale: this.editForm.get(['sale'])!.value,
      category: this.editForm.get(['category'])!.value,
      orderDetai: this.editForm.get(['orderDetai'])!.value,
    };
  }
}
