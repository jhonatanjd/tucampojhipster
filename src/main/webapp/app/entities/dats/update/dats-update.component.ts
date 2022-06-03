import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDats, Dats } from '../dats.model';
import { DatsService } from '../service/dats.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { IProducer } from 'app/entities/producer/producer.model';
import { ProducerService } from 'app/entities/producer/service/producer.service';
import { IAdministrator } from 'app/entities/administrator/administrator.model';
import { AdministratorService } from 'app/entities/administrator/service/administrator.service';
import { IDriver } from 'app/entities/driver/driver.model';
import { DriverService } from 'app/entities/driver/service/driver.service';

@Component({
  selector: 'tucam-dats-update',
  templateUrl: './dats-update.component.html',
})
export class DatsUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];
  clientsSharedCollection: IClient[] = [];
  producersSharedCollection: IProducer[] = [];
  administratorsSharedCollection: IAdministrator[] = [];
  driversSharedCollection: IDriver[] = [];

  editForm = this.fb.group({
    id: [],
    names: [null, [Validators.required]],
    surnames: [null, [Validators.required]],
    directions: [null, [Validators.required]],
    telephone: [],
    cellPhone: [null, [Validators.required]],
    mail: [null, [Validators.required]],
    city: [null, [Validators.required]],
    user: [],
    client: [],
    producer: [],
    administrator: [],
    driver: [],
  });

  constructor(
    protected datsService: DatsService,
    protected userService: UserService,
    protected clientService: ClientService,
    protected producerService: ProducerService,
    protected administratorService: AdministratorService,
    protected driverService: DriverService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dats }) => {
      this.updateForm(dats);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dats = this.createFromForm();
    if (dats.id !== undefined) {
      this.subscribeToSaveResponse(this.datsService.update(dats));
    } else {
      this.subscribeToSaveResponse(this.datsService.create(dats));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  trackClientById(_index: number, item: IClient): number {
    return item.id!;
  }

  trackProducerById(_index: number, item: IProducer): number {
    return item.id!;
  }

  trackAdministratorById(_index: number, item: IAdministrator): number {
    return item.id!;
  }

  trackDriverById(_index: number, item: IDriver): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDats>>): void {
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

  protected updateForm(dats: IDats): void {
    this.editForm.patchValue({
      id: dats.id,
      names: dats.names,
      surnames: dats.surnames,
      directions: dats.directions,
      telephone: dats.telephone,
      cellPhone: dats.cellPhone,
      mail: dats.mail,
      city: dats.city,
      user: dats.user,
      client: dats.client,
      producer: dats.producer,
      administrator: dats.administrator,
      driver: dats.driver,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, dats.user);
    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing(this.clientsSharedCollection, dats.client);
    this.producersSharedCollection = this.producerService.addProducerToCollectionIfMissing(this.producersSharedCollection, dats.producer);
    this.administratorsSharedCollection = this.administratorService.addAdministratorToCollectionIfMissing(
      this.administratorsSharedCollection,
      dats.administrator
    );
    this.driversSharedCollection = this.driverService.addDriverToCollectionIfMissing(this.driversSharedCollection, dats.driver);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing(clients, this.editForm.get('client')!.value)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));

    this.producerService
      .query()
      .pipe(map((res: HttpResponse<IProducer[]>) => res.body ?? []))
      .pipe(
        map((producers: IProducer[]) =>
          this.producerService.addProducerToCollectionIfMissing(producers, this.editForm.get('producer')!.value)
        )
      )
      .subscribe((producers: IProducer[]) => (this.producersSharedCollection = producers));

    this.administratorService
      .query()
      .pipe(map((res: HttpResponse<IAdministrator[]>) => res.body ?? []))
      .pipe(
        map((administrators: IAdministrator[]) =>
          this.administratorService.addAdministratorToCollectionIfMissing(administrators, this.editForm.get('administrator')!.value)
        )
      )
      .subscribe((administrators: IAdministrator[]) => (this.administratorsSharedCollection = administrators));

    this.driverService
      .query()
      .pipe(map((res: HttpResponse<IDriver[]>) => res.body ?? []))
      .pipe(map((drivers: IDriver[]) => this.driverService.addDriverToCollectionIfMissing(drivers, this.editForm.get('driver')!.value)))
      .subscribe((drivers: IDriver[]) => (this.driversSharedCollection = drivers));
  }

  protected createFromForm(): IDats {
    return {
      ...new Dats(),
      id: this.editForm.get(['id'])!.value,
      names: this.editForm.get(['names'])!.value,
      surnames: this.editForm.get(['surnames'])!.value,
      directions: this.editForm.get(['directions'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      cellPhone: this.editForm.get(['cellPhone'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      city: this.editForm.get(['city'])!.value,
      user: this.editForm.get(['user'])!.value,
      client: this.editForm.get(['client'])!.value,
      producer: this.editForm.get(['producer'])!.value,
      administrator: this.editForm.get(['administrator'])!.value,
      driver: this.editForm.get(['driver'])!.value,
    };
  }
}
