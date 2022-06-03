import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IInvoice, Invoice } from '../invoice.model';
import { InvoiceService } from '../service/invoice.service';
import { IWayToPay } from 'app/entities/way-to-pay/way-to-pay.model';
import { WayToPayService } from 'app/entities/way-to-pay/service/way-to-pay.service';

@Component({
  selector: 'tucam-invoice-update',
  templateUrl: './invoice-update.component.html',
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;

  wayToPaysSharedCollection: IWayToPay[] = [];

  editForm = this.fb.group({
    id: [],
    unitPrice: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    priceTotal: [null, [Validators.required]],
    wayToPay: [],
  });

  constructor(
    protected invoiceService: InvoiceService,
    protected wayToPayService: WayToPayService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.updateForm(invoice);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  trackWayToPayById(_index: number, item: IWayToPay): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>): void {
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

  protected updateForm(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      unitPrice: invoice.unitPrice,
      amount: invoice.amount,
      priceTotal: invoice.priceTotal,
      wayToPay: invoice.wayToPay,
    });

    this.wayToPaysSharedCollection = this.wayToPayService.addWayToPayToCollectionIfMissing(
      this.wayToPaysSharedCollection,
      invoice.wayToPay
    );
  }

  protected loadRelationshipsOptions(): void {
    this.wayToPayService
      .query()
      .pipe(map((res: HttpResponse<IWayToPay[]>) => res.body ?? []))
      .pipe(
        map((wayToPays: IWayToPay[]) =>
          this.wayToPayService.addWayToPayToCollectionIfMissing(wayToPays, this.editForm.get('wayToPay')!.value)
        )
      )
      .subscribe((wayToPays: IWayToPay[]) => (this.wayToPaysSharedCollection = wayToPays));
  }

  protected createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id'])!.value,
      unitPrice: this.editForm.get(['unitPrice'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      priceTotal: this.editForm.get(['priceTotal'])!.value,
      wayToPay: this.editForm.get(['wayToPay'])!.value,
    };
  }
}
