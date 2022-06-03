import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DatsService } from '../service/dats.service';
import { IDats, Dats } from '../dats.model';

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

import { DatsUpdateComponent } from './dats-update.component';

describe('Dats Management Update Component', () => {
  let comp: DatsUpdateComponent;
  let fixture: ComponentFixture<DatsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let datsService: DatsService;
  let userService: UserService;
  let clientService: ClientService;
  let producerService: ProducerService;
  let administratorService: AdministratorService;
  let driverService: DriverService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DatsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DatsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DatsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    datsService = TestBed.inject(DatsService);
    userService = TestBed.inject(UserService);
    clientService = TestBed.inject(ClientService);
    producerService = TestBed.inject(ProducerService);
    administratorService = TestBed.inject(AdministratorService);
    driverService = TestBed.inject(DriverService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const dats: IDats = { id: 456 };
      const user: IUser = { id: 49699 };
      dats.user = user;

      const userCollection: IUser[] = [{ id: 19550 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Client query and add missing value', () => {
      const dats: IDats = { id: 456 };
      const client: IClient = { id: 62597 };
      dats.client = client;

      const clientCollection: IClient[] = [{ id: 92044 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(clientCollection, ...additionalClients);
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Producer query and add missing value', () => {
      const dats: IDats = { id: 456 };
      const producer: IProducer = { id: 31285 };
      dats.producer = producer;

      const producerCollection: IProducer[] = [{ id: 56591 }];
      jest.spyOn(producerService, 'query').mockReturnValue(of(new HttpResponse({ body: producerCollection })));
      const additionalProducers = [producer];
      const expectedCollection: IProducer[] = [...additionalProducers, ...producerCollection];
      jest.spyOn(producerService, 'addProducerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      expect(producerService.query).toHaveBeenCalled();
      expect(producerService.addProducerToCollectionIfMissing).toHaveBeenCalledWith(producerCollection, ...additionalProducers);
      expect(comp.producersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Administrator query and add missing value', () => {
      const dats: IDats = { id: 456 };
      const administrator: IAdministrator = { id: 58995 };
      dats.administrator = administrator;

      const administratorCollection: IAdministrator[] = [{ id: 49628 }];
      jest.spyOn(administratorService, 'query').mockReturnValue(of(new HttpResponse({ body: administratorCollection })));
      const additionalAdministrators = [administrator];
      const expectedCollection: IAdministrator[] = [...additionalAdministrators, ...administratorCollection];
      jest.spyOn(administratorService, 'addAdministratorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      expect(administratorService.query).toHaveBeenCalled();
      expect(administratorService.addAdministratorToCollectionIfMissing).toHaveBeenCalledWith(
        administratorCollection,
        ...additionalAdministrators
      );
      expect(comp.administratorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Driver query and add missing value', () => {
      const dats: IDats = { id: 456 };
      const driver: IDriver = { id: 23363 };
      dats.driver = driver;

      const driverCollection: IDriver[] = [{ id: 51461 }];
      jest.spyOn(driverService, 'query').mockReturnValue(of(new HttpResponse({ body: driverCollection })));
      const additionalDrivers = [driver];
      const expectedCollection: IDriver[] = [...additionalDrivers, ...driverCollection];
      jest.spyOn(driverService, 'addDriverToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      expect(driverService.query).toHaveBeenCalled();
      expect(driverService.addDriverToCollectionIfMissing).toHaveBeenCalledWith(driverCollection, ...additionalDrivers);
      expect(comp.driversSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dats: IDats = { id: 456 };
      const user: IUser = { id: 51126 };
      dats.user = user;
      const client: IClient = { id: 5708 };
      dats.client = client;
      const producer: IProducer = { id: 33616 };
      dats.producer = producer;
      const administrator: IAdministrator = { id: 33140 };
      dats.administrator = administrator;
      const driver: IDriver = { id: 17595 };
      dats.driver = driver;

      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dats));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.producersSharedCollection).toContain(producer);
      expect(comp.administratorsSharedCollection).toContain(administrator);
      expect(comp.driversSharedCollection).toContain(driver);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Dats>>();
      const dats = { id: 123 };
      jest.spyOn(datsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dats }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(datsService.update).toHaveBeenCalledWith(dats);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Dats>>();
      const dats = new Dats();
      jest.spyOn(datsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dats }));
      saveSubject.complete();

      // THEN
      expect(datsService.create).toHaveBeenCalledWith(dats);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Dats>>();
      const dats = { id: 123 };
      jest.spyOn(datsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dats });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(datsService.update).toHaveBeenCalledWith(dats);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackClientById', () => {
      it('Should return tracked Client primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProducerById', () => {
      it('Should return tracked Producer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProducerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAdministratorById', () => {
      it('Should return tracked Administrator primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdministratorById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDriverById', () => {
      it('Should return tracked Driver primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDriverById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
