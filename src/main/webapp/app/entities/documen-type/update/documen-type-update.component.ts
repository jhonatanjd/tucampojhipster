import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDocumenType, DocumenType } from '../documen-type.model';
import { DocumenTypeService } from '../service/documen-type.service';
import { IDats } from 'app/entities/dats/dats.model';
import { DatsService } from 'app/entities/dats/service/dats.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'tucam-documen-type-update',
  templateUrl: './documen-type-update.component.html',
})
export class DocumenTypeUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  datsCollection: IDats[] = [];

  editForm = this.fb.group({
    id: [],
    initials: [null, [Validators.required]],
    typedocument: [null, [Validators.required]],
    stateDocument: [],
    dats: [],
  });

  constructor(
    protected documenTypeService: DocumenTypeService,
    protected datsService: DatsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documenType }) => {
      this.updateForm(documenType);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documenType = this.createFromForm();
    if (documenType.id !== undefined) {
      this.subscribeToSaveResponse(this.documenTypeService.update(documenType));
    } else {
      this.subscribeToSaveResponse(this.documenTypeService.create(documenType));
    }
  }

  trackDatsById(_index: number, item: IDats): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumenType>>): void {
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

  protected updateForm(documenType: IDocumenType): void {
    this.editForm.patchValue({
      id: documenType.id,
      initials: documenType.initials,
      typedocument: documenType.typedocument,
      stateDocument: documenType.stateDocument,
      dats: documenType.dats,
    });

    this.datsCollection = this.datsService.addDatsToCollectionIfMissing(this.datsCollection, documenType.dats);
  }

  protected loadRelationshipsOptions(): void {
    this.datsService
      .query({ filter: 'documentype-is-null' })
      .pipe(map((res: HttpResponse<IDats[]>) => res.body ?? []))
      .pipe(map((dats: IDats[]) => this.datsService.addDatsToCollectionIfMissing(dats, this.editForm.get('dats')!.value)))
      .subscribe((dats: IDats[]) => (this.datsCollection = dats));
  }

  protected createFromForm(): IDocumenType {
    return {
      ...new DocumenType(),
      id: this.editForm.get(['id'])!.value,
      initials: this.editForm.get(['initials'])!.value,
      typedocument: this.editForm.get(['typedocument'])!.value,
      stateDocument: this.editForm.get(['stateDocument'])!.value,
      dats: this.editForm.get(['dats'])!.value,
    };
  }
}
