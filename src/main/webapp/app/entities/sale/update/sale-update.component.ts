import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISale, Sale } from '../sale.model';
import { SaleService } from '../service/sale.service';
import { IProducer } from 'app/entities/producer/producer.model';
import { ProducerService } from 'app/entities/producer/service/producer.service';
import { IOrderDetai } from 'app/entities/order-detai/order-detai.model';
import { OrderDetaiService } from 'app/entities/order-detai/service/order-detai.service';
import { Transportations } from 'app/entities/enumerations/transportations.model';

@Component({
  selector: 'tucam-sale-update',
  templateUrl: './sale-update.component.html',
})
export class SaleUpdateComponent implements OnInit {
  isSaving = false;
  transportationsValues = Object.keys(Transportations);

  producersSharedCollection: IProducer[] = [];
  orderDetaisSharedCollection: IOrderDetai[] = [];

  editForm = this.fb.group({
    id: [],
    nameProducts: [null, [Validators.required]],
    amountKilo: [null, [Validators.required]],
    priceKilo: [null, [Validators.required]],
    priceTotal: [null, [Validators.required]],
    city: [null, [Validators.required]],
    availableDate: [null, [Validators.required]],
    stateTransportations: [],
    descriptions: [],
    producer: [],
    orderDetai: [],
  });

  constructor(
    protected saleService: SaleService,
    protected producerService: ProducerService,
    protected orderDetaiService: OrderDetaiService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sale }) => {
      this.updateForm(sale);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sale = this.createFromForm();
    if (sale.id !== undefined) {
      this.subscribeToSaveResponse(this.saleService.update(sale));
    } else {
      this.subscribeToSaveResponse(this.saleService.create(sale));
    }
  }

  trackProducerById(_index: number, item: IProducer): number {
    return item.id!;
  }

  trackOrderDetaiById(_index: number, item: IOrderDetai): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISale>>): void {
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

  protected updateForm(sale: ISale): void {
    this.editForm.patchValue({
      id: sale.id,
      nameProducts: sale.nameProducts,
      amountKilo: sale.amountKilo,
      priceKilo: sale.priceKilo,
      priceTotal: sale.priceTotal,
      city: sale.city,
      availableDate: sale.availableDate,
      stateTransportations: sale.stateTransportations,
      descriptions: sale.descriptions,
      producer: sale.producer,
      orderDetai: sale.orderDetai,
    });

    this.producersSharedCollection = this.producerService.addProducerToCollectionIfMissing(this.producersSharedCollection, sale.producer);
    this.orderDetaisSharedCollection = this.orderDetaiService.addOrderDetaiToCollectionIfMissing(
      this.orderDetaisSharedCollection,
      sale.orderDetai
    );
  }

  protected loadRelationshipsOptions(): void {
    this.producerService
      .query()
      .pipe(map((res: HttpResponse<IProducer[]>) => res.body ?? []))
      .pipe(
        map((producers: IProducer[]) =>
          this.producerService.addProducerToCollectionIfMissing(producers, this.editForm.get('producer')!.value)
        )
      )
      .subscribe((producers: IProducer[]) => (this.producersSharedCollection = producers));

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

  protected createFromForm(): ISale {
    return {
      ...new Sale(),
      id: this.editForm.get(['id'])!.value,
      nameProducts: this.editForm.get(['nameProducts'])!.value,
      amountKilo: this.editForm.get(['amountKilo'])!.value,
      priceKilo: this.editForm.get(['priceKilo'])!.value,
      priceTotal: this.editForm.get(['priceTotal'])!.value,
      city: this.editForm.get(['city'])!.value,
      availableDate: this.editForm.get(['availableDate'])!.value,
      stateTransportations: this.editForm.get(['stateTransportations'])!.value,
      descriptions: this.editForm.get(['descriptions'])!.value,
      producer: this.editForm.get(['producer'])!.value,
      orderDetai: this.editForm.get(['orderDetai'])!.value,
    };
  }
}
