import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVehicle, Vehicle } from '../vehicle.model';
import { VehicleService } from '../service/vehicle.service';
import { IDriver } from 'app/entities/driver/driver.model';
import { DriverService } from 'app/entities/driver/service/driver.service';

@Component({
  selector: 'tucam-vehicle-update',
  templateUrl: './vehicle-update.component.html',
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;

  driversCollection: IDriver[] = [];

  editForm = this.fb.group({
    id: [],
    bodyworkType: [null, [Validators.required]],
    ability: [null, [Validators.required]],
    brand: [null, [Validators.required]],
    model: [null, [Validators.required]],
    licenseLate: [null, [Validators.required]],
    color: [],
    driver: [],
  });

  constructor(
    protected vehicleService: VehicleService,
    protected driverService: DriverService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.updateForm(vehicle);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  trackDriverById(_index: number, item: IDriver): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
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

  protected updateForm(vehicle: IVehicle): void {
    this.editForm.patchValue({
      id: vehicle.id,
      bodyworkType: vehicle.bodyworkType,
      ability: vehicle.ability,
      brand: vehicle.brand,
      model: vehicle.model,
      licenseLate: vehicle.licenseLate,
      color: vehicle.color,
      driver: vehicle.driver,
    });

    this.driversCollection = this.driverService.addDriverToCollectionIfMissing(this.driversCollection, vehicle.driver);
  }

  protected loadRelationshipsOptions(): void {
    this.driverService
      .query({ filter: 'vehicle-is-null' })
      .pipe(map((res: HttpResponse<IDriver[]>) => res.body ?? []))
      .pipe(map((drivers: IDriver[]) => this.driverService.addDriverToCollectionIfMissing(drivers, this.editForm.get('driver')!.value)))
      .subscribe((drivers: IDriver[]) => (this.driversCollection = drivers));
  }

  protected createFromForm(): IVehicle {
    return {
      ...new Vehicle(),
      id: this.editForm.get(['id'])!.value,
      bodyworkType: this.editForm.get(['bodyworkType'])!.value,
      ability: this.editForm.get(['ability'])!.value,
      brand: this.editForm.get(['brand'])!.value,
      model: this.editForm.get(['model'])!.value,
      licenseLate: this.editForm.get(['licenseLate'])!.value,
      color: this.editForm.get(['color'])!.value,
      driver: this.editForm.get(['driver'])!.value,
    };
  }
}
