import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ShoppingService } from '../service/shopping.service';
import { IShopping, Shopping } from '../shopping.model';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { InvoiceService } from 'app/entities/invoice/service/invoice.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

import { ShoppingUpdateComponent } from './shopping-update.component';

describe('Shopping Management Update Component', () => {
  let comp: ShoppingUpdateComponent;
  let fixture: ComponentFixture<ShoppingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shoppingService: ShoppingService;
  let invoiceService: InvoiceService;
  let clientService: ClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ShoppingUpdateComponent],
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
      .overrideTemplate(ShoppingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShoppingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shoppingService = TestBed.inject(ShoppingService);
    invoiceService = TestBed.inject(InvoiceService);
    clientService = TestBed.inject(ClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Invoice query and add missing value', () => {
      const shopping: IShopping = { id: 456 };
      const invoice: IInvoice = { id: 5961 };
      shopping.invoice = invoice;

      const invoiceCollection: IInvoice[] = [{ id: 59151 }];
      jest.spyOn(invoiceService, 'query').mockReturnValue(of(new HttpResponse({ body: invoiceCollection })));
      const additionalInvoices = [invoice];
      const expectedCollection: IInvoice[] = [...additionalInvoices, ...invoiceCollection];
      jest.spyOn(invoiceService, 'addInvoiceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shopping });
      comp.ngOnInit();

      expect(invoiceService.query).toHaveBeenCalled();
      expect(invoiceService.addInvoiceToCollectionIfMissing).toHaveBeenCalledWith(invoiceCollection, ...additionalInvoices);
      expect(comp.invoicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Client query and add missing value', () => {
      const shopping: IShopping = { id: 456 };
      const client: IClient = { id: 98754 };
      shopping.client = client;

      const clientCollection: IClient[] = [{ id: 9390 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shopping });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(clientCollection, ...additionalClients);
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shopping: IShopping = { id: 456 };
      const invoice: IInvoice = { id: 73265 };
      shopping.invoice = invoice;
      const client: IClient = { id: 81606 };
      shopping.client = client;

      activatedRoute.data = of({ shopping });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(shopping));
      expect(comp.invoicesSharedCollection).toContain(invoice);
      expect(comp.clientsSharedCollection).toContain(client);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Shopping>>();
      const shopping = { id: 123 };
      jest.spyOn(shoppingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shopping });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shopping }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(shoppingService.update).toHaveBeenCalledWith(shopping);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Shopping>>();
      const shopping = new Shopping();
      jest.spyOn(shoppingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shopping });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shopping }));
      saveSubject.complete();

      // THEN
      expect(shoppingService.create).toHaveBeenCalledWith(shopping);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Shopping>>();
      const shopping = { id: 123 };
      jest.spyOn(shoppingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shopping });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shoppingService.update).toHaveBeenCalledWith(shopping);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackInvoiceById', () => {
      it('Should return tracked Invoice primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInvoiceById(0, entity);
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
  });
});
