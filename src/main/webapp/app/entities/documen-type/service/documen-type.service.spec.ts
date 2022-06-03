import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { IDocumenType, DocumenType } from '../documen-type.model';

import { DocumenTypeService } from './documen-type.service';

describe('DocumenType Service', () => {
  let service: DocumenTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IDocumenType;
  let expectedResult: IDocumenType | IDocumenType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DocumenTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      initials: 'AAAAAAA',
      typedocument: 'AAAAAAA',
      stateDocument: State.ACTIVE,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DocumenType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DocumenType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocumenType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          initials: 'BBBBBB',
          typedocument: 'BBBBBB',
          stateDocument: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DocumenType', () => {
      const patchObject = Object.assign(
        {
          initials: 'BBBBBB',
          typedocument: 'BBBBBB',
          stateDocument: 'BBBBBB',
        },
        new DocumenType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DocumenType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          initials: 'BBBBBB',
          typedocument: 'BBBBBB',
          stateDocument: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DocumenType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDocumenTypeToCollectionIfMissing', () => {
      it('should add a DocumenType to an empty array', () => {
        const documenType: IDocumenType = { id: 123 };
        expectedResult = service.addDocumenTypeToCollectionIfMissing([], documenType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documenType);
      });

      it('should not add a DocumenType to an array that contains it', () => {
        const documenType: IDocumenType = { id: 123 };
        const documenTypeCollection: IDocumenType[] = [
          {
            ...documenType,
          },
          { id: 456 },
        ];
        expectedResult = service.addDocumenTypeToCollectionIfMissing(documenTypeCollection, documenType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocumenType to an array that doesn't contain it", () => {
        const documenType: IDocumenType = { id: 123 };
        const documenTypeCollection: IDocumenType[] = [{ id: 456 }];
        expectedResult = service.addDocumenTypeToCollectionIfMissing(documenTypeCollection, documenType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documenType);
      });

      it('should add only unique DocumenType to an array', () => {
        const documenTypeArray: IDocumenType[] = [{ id: 123 }, { id: 456 }, { id: 22250 }];
        const documenTypeCollection: IDocumenType[] = [{ id: 123 }];
        expectedResult = service.addDocumenTypeToCollectionIfMissing(documenTypeCollection, ...documenTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documenType: IDocumenType = { id: 123 };
        const documenType2: IDocumenType = { id: 456 };
        expectedResult = service.addDocumenTypeToCollectionIfMissing([], documenType, documenType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documenType);
        expect(expectedResult).toContain(documenType2);
      });

      it('should accept null and undefined values', () => {
        const documenType: IDocumenType = { id: 123 };
        expectedResult = service.addDocumenTypeToCollectionIfMissing([], null, documenType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documenType);
      });

      it('should return initial array if no DocumenType is added', () => {
        const documenTypeCollection: IDocumenType[] = [{ id: 123 }];
        expectedResult = service.addDocumenTypeToCollectionIfMissing(documenTypeCollection, undefined, null);
        expect(expectedResult).toEqual(documenTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
