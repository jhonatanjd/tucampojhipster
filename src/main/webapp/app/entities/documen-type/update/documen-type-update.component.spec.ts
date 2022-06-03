import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DocumenTypeService } from '../service/documen-type.service';
import { IDocumenType, DocumenType } from '../documen-type.model';
import { IDats } from 'app/entities/dats/dats.model';
import { DatsService } from 'app/entities/dats/service/dats.service';

import { DocumenTypeUpdateComponent } from './documen-type-update.component';

describe('DocumenType Management Update Component', () => {
  let comp: DocumenTypeUpdateComponent;
  let fixture: ComponentFixture<DocumenTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documenTypeService: DocumenTypeService;
  let datsService: DatsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DocumenTypeUpdateComponent],
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
      .overrideTemplate(DocumenTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumenTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documenTypeService = TestBed.inject(DocumenTypeService);
    datsService = TestBed.inject(DatsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call dats query and add missing value', () => {
      const documenType: IDocumenType = { id: 456 };
      const dats: IDats = { id: 49123 };
      documenType.dats = dats;

      const datsCollection: IDats[] = [{ id: 16142 }];
      jest.spyOn(datsService, 'query').mockReturnValue(of(new HttpResponse({ body: datsCollection })));
      const expectedCollection: IDats[] = [dats, ...datsCollection];
      jest.spyOn(datsService, 'addDatsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documenType });
      comp.ngOnInit();

      expect(datsService.query).toHaveBeenCalled();
      expect(datsService.addDatsToCollectionIfMissing).toHaveBeenCalledWith(datsCollection, dats);
      expect(comp.datsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const documenType: IDocumenType = { id: 456 };
      const dats: IDats = { id: 50485 };
      documenType.dats = dats;

      activatedRoute.data = of({ documenType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(documenType));
      expect(comp.datsCollection).toContain(dats);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumenType>>();
      const documenType = { id: 123 };
      jest.spyOn(documenTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documenType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documenType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(documenTypeService.update).toHaveBeenCalledWith(documenType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumenType>>();
      const documenType = new DocumenType();
      jest.spyOn(documenTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documenType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documenType }));
      saveSubject.complete();

      // THEN
      expect(documenTypeService.create).toHaveBeenCalledWith(documenType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DocumenType>>();
      const documenType = { id: 123 };
      jest.spyOn(documenTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documenType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documenTypeService.update).toHaveBeenCalledWith(documenType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDatsById', () => {
      it('Should return tracked Dats primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDatsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
