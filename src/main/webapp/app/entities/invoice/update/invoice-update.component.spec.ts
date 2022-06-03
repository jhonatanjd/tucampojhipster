import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InvoiceService } from '../service/invoice.service';
import { IInvoice, Invoice } from '../invoice.model';
import { IWayToPay } from 'app/entities/way-to-pay/way-to-pay.model';
import { WayToPayService } from 'app/entities/way-to-pay/service/way-to-pay.service';

import { InvoiceUpdateComponent } from './invoice-update.component';

describe('Invoice Management Update Component', () => {
  let comp: InvoiceUpdateComponent;
  let fixture: ComponentFixture<InvoiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let invoiceService: InvoiceService;
  let wayToPayService: WayToPayService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InvoiceUpdateComponent],
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
      .overrideTemplate(InvoiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InvoiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    invoiceService = TestBed.inject(InvoiceService);
    wayToPayService = TestBed.inject(WayToPayService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call WayToPay query and add missing value', () => {
      const invoice: IInvoice = { id: 456 };
      const wayToPay: IWayToPay = { id: 60898 };
      invoice.wayToPay = wayToPay;

      const wayToPayCollection: IWayToPay[] = [{ id: 36109 }];
      jest.spyOn(wayToPayService, 'query').mockReturnValue(of(new HttpResponse({ body: wayToPayCollection })));
      const additionalWayToPays = [wayToPay];
      const expectedCollection: IWayToPay[] = [...additionalWayToPays, ...wayToPayCollection];
      jest.spyOn(wayToPayService, 'addWayToPayToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ invoice });
      comp.ngOnInit();

      expect(wayToPayService.query).toHaveBeenCalled();
      expect(wayToPayService.addWayToPayToCollectionIfMissing).toHaveBeenCalledWith(wayToPayCollection, ...additionalWayToPays);
      expect(comp.wayToPaysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const invoice: IInvoice = { id: 456 };
      const wayToPay: IWayToPay = { id: 17319 };
      invoice.wayToPay = wayToPay;

      activatedRoute.data = of({ invoice });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(invoice));
      expect(comp.wayToPaysSharedCollection).toContain(wayToPay);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Invoice>>();
      const invoice = { id: 123 };
      jest.spyOn(invoiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: invoice }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(invoiceService.update).toHaveBeenCalledWith(invoice);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Invoice>>();
      const invoice = new Invoice();
      jest.spyOn(invoiceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: invoice }));
      saveSubject.complete();

      // THEN
      expect(invoiceService.create).toHaveBeenCalledWith(invoice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Invoice>>();
      const invoice = { id: 123 };
      jest.spyOn(invoiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(invoiceService.update).toHaveBeenCalledWith(invoice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackWayToPayById', () => {
      it('Should return tracked WayToPay primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackWayToPayById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
