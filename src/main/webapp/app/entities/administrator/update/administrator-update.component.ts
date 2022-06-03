import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAdministrator, Administrator } from '../administrator.model';
import { AdministratorService } from '../service/administrator.service';

@Component({
  selector: 'tucam-administrator-update',
  templateUrl: './administrator-update.component.html',
})
export class AdministratorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected administratorService: AdministratorService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrator }) => {
      this.updateForm(administrator);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const administrator = this.createFromForm();
    if (administrator.id !== undefined) {
      this.subscribeToSaveResponse(this.administratorService.update(administrator));
    } else {
      this.subscribeToSaveResponse(this.administratorService.create(administrator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdministrator>>): void {
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

  protected updateForm(administrator: IAdministrator): void {
    this.editForm.patchValue({
      id: administrator.id,
    });
  }

  protected createFromForm(): IAdministrator {
    return {
      ...new Administrator(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
