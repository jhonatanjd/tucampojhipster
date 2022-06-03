import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IWayToPay, WayToPay } from '../way-to-pay.model';
import { WayToPayService } from '../service/way-to-pay.service';

@Component({
  selector: 'tucam-way-to-pay-update',
  templateUrl: './way-to-pay-update.component.html',
})
export class WayToPayUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [],
  });

  constructor(protected wayToPayService: WayToPayService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wayToPay }) => {
      this.updateForm(wayToPay);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wayToPay = this.createFromForm();
    if (wayToPay.id !== undefined) {
      this.subscribeToSaveResponse(this.wayToPayService.update(wayToPay));
    } else {
      this.subscribeToSaveResponse(this.wayToPayService.create(wayToPay));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWayToPay>>): void {
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

  protected updateForm(wayToPay: IWayToPay): void {
    this.editForm.patchValue({
      id: wayToPay.id,
      description: wayToPay.description,
    });
  }

  protected createFromForm(): IWayToPay {
    return {
      ...new WayToPay(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
