import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WayToPayService } from '../service/way-to-pay.service';
import { IWayToPay, WayToPay } from '../way-to-pay.model';

import { WayToPayUpdateComponent } from './way-to-pay-update.component';

describe('WayToPay Management Update Component', () => {
  let comp: WayToPayUpdateComponent;
  let fixture: ComponentFixture<WayToPayUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let wayToPayService: WayToPayService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WayToPayUpdateComponent],
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
      .overrideTemplate(WayToPayUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WayToPayUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    wayToPayService = TestBed.inject(WayToPayService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const wayToPay: IWayToPay = { id: 456 };

      activatedRoute.data = of({ wayToPay });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(wayToPay));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<WayToPay>>();
      const wayToPay = { id: 123 };
      jest.spyOn(wayToPayService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wayToPay });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: wayToPay }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(wayToPayService.update).toHaveBeenCalledWith(wayToPay);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<WayToPay>>();
      const wayToPay = new WayToPay();
      jest.spyOn(wayToPayService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wayToPay });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: wayToPay }));
      saveSubject.complete();

      // THEN
      expect(wayToPayService.create).toHaveBeenCalledWith(wayToPay);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<WayToPay>>();
      const wayToPay = { id: 123 };
      jest.spyOn(wayToPayService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wayToPay });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(wayToPayService.update).toHaveBeenCalledWith(wayToPay);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
