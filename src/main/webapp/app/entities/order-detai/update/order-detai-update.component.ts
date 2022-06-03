import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IOrderDetai, OrderDetai } from '../order-detai.model';
import { OrderDetaiService } from '../service/order-detai.service';

@Component({
  selector: 'tucam-order-detai-update',
  templateUrl: './order-detai-update.component.html',
})
export class OrderDetaiUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected orderDetaiService: OrderDetaiService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderDetai }) => {
      this.updateForm(orderDetai);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderDetai = this.createFromForm();
    if (orderDetai.id !== undefined) {
      this.subscribeToSaveResponse(this.orderDetaiService.update(orderDetai));
    } else {
      this.subscribeToSaveResponse(this.orderDetaiService.create(orderDetai));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderDetai>>): void {
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

  protected updateForm(orderDetai: IOrderDetai): void {
    this.editForm.patchValue({
      id: orderDetai.id,
    });
  }

  protected createFromForm(): IOrderDetai {
    return {
      ...new OrderDetai(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
