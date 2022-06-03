import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAnonymous, Anonymous } from '../anonymous.model';
import { AnonymousService } from '../service/anonymous.service';
import { ISale } from 'app/entities/sale/sale.model';
import { SaleService } from 'app/entities/sale/service/sale.service';

@Component({
  selector: 'tucam-anonymous-update',
  templateUrl: './anonymous-update.component.html',
})
export class AnonymousUpdateComponent implements OnInit {
  isSaving = false;

  salesSharedCollection: ISale[] = [];

  editForm = this.fb.group({
    id: [],
    sale: [],
  });

  constructor(
    protected anonymousService: AnonymousService,
    protected saleService: SaleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anonymous }) => {
      this.updateForm(anonymous);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anonymous = this.createFromForm();
    if (anonymous.id !== undefined) {
      this.subscribeToSaveResponse(this.anonymousService.update(anonymous));
    } else {
      this.subscribeToSaveResponse(this.anonymousService.create(anonymous));
    }
  }

  trackSaleById(_index: number, item: ISale): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnonymous>>): void {
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

  protected updateForm(anonymous: IAnonymous): void {
    this.editForm.patchValue({
      id: anonymous.id,
      sale: anonymous.sale,
    });

    this.salesSharedCollection = this.saleService.addSaleToCollectionIfMissing(this.salesSharedCollection, anonymous.sale);
  }

  protected loadRelationshipsOptions(): void {
    this.saleService
      .query()
      .pipe(map((res: HttpResponse<ISale[]>) => res.body ?? []))
      .pipe(map((sales: ISale[]) => this.saleService.addSaleToCollectionIfMissing(sales, this.editForm.get('sale')!.value)))
      .subscribe((sales: ISale[]) => (this.salesSharedCollection = sales));
  }

  protected createFromForm(): IAnonymous {
    return {
      ...new Anonymous(),
      id: this.editForm.get(['id'])!.value,
      sale: this.editForm.get(['sale'])!.value,
    };
  }
}
