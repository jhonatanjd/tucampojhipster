import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IUnits, Units } from '../units.model';
import { UnitsService } from '../service/units.service';
import { IProducts } from 'app/entities/products/products.model';
import { ProductsService } from 'app/entities/products/service/products.service';

@Component({
  selector: 'tucam-units-update',
  templateUrl: './units-update.component.html',
})
export class UnitsUpdateComponent implements OnInit {
  isSaving = false;

  productsSharedCollection: IProducts[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    products: [],
  });

  constructor(
    protected unitsService: UnitsService,
    protected productsService: ProductsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ units }) => {
      this.updateForm(units);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const units = this.createFromForm();
    if (units.id !== undefined) {
      this.subscribeToSaveResponse(this.unitsService.update(units));
    } else {
      this.subscribeToSaveResponse(this.unitsService.create(units));
    }
  }

  trackProductsById(_index: number, item: IProducts): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnits>>): void {
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

  protected updateForm(units: IUnits): void {
    this.editForm.patchValue({
      id: units.id,
      description: units.description,
      products: units.products,
    });

    this.productsSharedCollection = this.productsService.addProductsToCollectionIfMissing(this.productsSharedCollection, units.products);
  }

  protected loadRelationshipsOptions(): void {
    this.productsService
      .query()
      .pipe(map((res: HttpResponse<IProducts[]>) => res.body ?? []))
      .pipe(
        map((products: IProducts[]) =>
          this.productsService.addProductsToCollectionIfMissing(products, this.editForm.get('products')!.value)
        )
      )
      .subscribe((products: IProducts[]) => (this.productsSharedCollection = products));
  }

  protected createFromForm(): IUnits {
    return {
      ...new Units(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      products: this.editForm.get(['products'])!.value,
    };
  }
}
