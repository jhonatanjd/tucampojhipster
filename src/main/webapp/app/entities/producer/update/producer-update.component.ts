import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProducer, Producer } from '../producer.model';
import { ProducerService } from '../service/producer.service';

@Component({
  selector: 'tucam-producer-update',
  templateUrl: './producer-update.component.html',
})
export class ProducerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameProduct: [null, [Validators.required]],
  });

  constructor(protected producerService: ProducerService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ producer }) => {
      this.updateForm(producer);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const producer = this.createFromForm();
    if (producer.id !== undefined) {
      this.subscribeToSaveResponse(this.producerService.update(producer));
    } else {
      this.subscribeToSaveResponse(this.producerService.create(producer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducer>>): void {
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

  protected updateForm(producer: IProducer): void {
    this.editForm.patchValue({
      id: producer.id,
      nameProduct: producer.nameProduct,
    });
  }

  protected createFromForm(): IProducer {
    return {
      ...new Producer(),
      id: this.editForm.get(['id'])!.value,
      nameProduct: this.editForm.get(['nameProduct'])!.value,
    };
  }
}
