import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IShopping, Shopping } from '../shopping.model';
import { ShoppingService } from '../service/shopping.service';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { InvoiceService } from 'app/entities/invoice/service/invoice.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

@Component({
  selector: 'tucam-shopping-update',
  templateUrl: './shopping-update.component.html',
})
export class ShoppingUpdateComponent implements OnInit {
  isSaving = false;

  invoicesSharedCollection: IInvoice[] = [];
  clientsSharedCollection: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    nameProducts: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    directions: [null, [Validators.required]],
    city: [null, [Validators.required]],
    orderdate: [null, [Validators.required]],
    dateOfDelivery: [null, [Validators.required]],
    invoice: [],
    client: [],
  });

  constructor(
    protected shoppingService: ShoppingService,
    protected invoiceService: InvoiceService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shopping }) => {
      this.updateForm(shopping);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shopping = this.createFromForm();
    if (shopping.id !== undefined) {
      this.subscribeToSaveResponse(this.shoppingService.update(shopping));
    } else {
      this.subscribeToSaveResponse(this.shoppingService.create(shopping));
    }
  }

  trackInvoiceById(_index: number, item: IInvoice): number {
    return item.id!;
  }

  trackClientById(_index: number, item: IClient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShopping>>): void {
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

  protected updateForm(shopping: IShopping): void {
    this.editForm.patchValue({
      id: shopping.id,
      nameProducts: shopping.nameProducts,
      amount: shopping.amount,
      directions: shopping.directions,
      city: shopping.city,
      orderdate: shopping.orderdate,
      dateOfDelivery: shopping.dateOfDelivery,
      invoice: shopping.invoice,
      client: shopping.client,
    });

    this.invoicesSharedCollection = this.invoiceService.addInvoiceToCollectionIfMissing(this.invoicesSharedCollection, shopping.invoice);
    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing(this.clientsSharedCollection, shopping.client);
  }

  protected loadRelationshipsOptions(): void {
    this.invoiceService
      .query()
      .pipe(map((res: HttpResponse<IInvoice[]>) => res.body ?? []))
      .pipe(
        map((invoices: IInvoice[]) => this.invoiceService.addInvoiceToCollectionIfMissing(invoices, this.editForm.get('invoice')!.value))
      )
      .subscribe((invoices: IInvoice[]) => (this.invoicesSharedCollection = invoices));

    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing(clients, this.editForm.get('client')!.value)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));
  }

  protected createFromForm(): IShopping {
    return {
      ...new Shopping(),
      id: this.editForm.get(['id'])!.value,
      nameProducts: this.editForm.get(['nameProducts'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      directions: this.editForm.get(['directions'])!.value,
      city: this.editForm.get(['city'])!.value,
      orderdate: this.editForm.get(['orderdate'])!.value,
      dateOfDelivery: this.editForm.get(['dateOfDelivery'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
      client: this.editForm.get(['client'])!.value,
    };
  }
}
