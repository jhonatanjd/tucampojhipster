import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDats, Dats } from '../dats.model';

import { DatsService } from './dats.service';

describe('Dats Service', () => {
  let service: DatsService;
  let httpMock: HttpTestingController;
  let elemDefault: IDats;
  let expectedResult: IDats | IDats[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DatsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      names: 'AAAAAAA',
      surnames: 'AAAAAAA',
      directions: 'AAAAAAA',
      telephone: 0,
      cellPhone: 0,
      mail: 'AAAAAAA',
      city: 'AAAAAAA',
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

    it('should create a Dats', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Dats()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Dats', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          names: 'BBBBBB',
          surnames: 'BBBBBB',
          directions: 'BBBBBB',
          telephone: 1,
          cellPhone: 1,
          mail: 'BBBBBB',
          city: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Dats', () => {
      const patchObject = Object.assign(
        {
          names: 'BBBBBB',
          telephone: 1,
          city: 'BBBBBB',
        },
        new Dats()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Dats', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          names: 'BBBBBB',
          surnames: 'BBBBBB',
          directions: 'BBBBBB',
          telephone: 1,
          cellPhone: 1,
          mail: 'BBBBBB',
          city: 'BBBBBB',
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

    it('should delete a Dats', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDatsToCollectionIfMissing', () => {
      it('should add a Dats to an empty array', () => {
        const dats: IDats = { id: 123 };
        expectedResult = service.addDatsToCollectionIfMissing([], dats);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dats);
      });

      it('should not add a Dats to an array that contains it', () => {
        const dats: IDats = { id: 123 };
        const datsCollection: IDats[] = [
          {
            ...dats,
          },
          { id: 456 },
        ];
        expectedResult = service.addDatsToCollectionIfMissing(datsCollection, dats);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Dats to an array that doesn't contain it", () => {
        const dats: IDats = { id: 123 };
        const datsCollection: IDats[] = [{ id: 456 }];
        expectedResult = service.addDatsToCollectionIfMissing(datsCollection, dats);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dats);
      });

      it('should add only unique Dats to an array', () => {
        const datsArray: IDats[] = [{ id: 123 }, { id: 456 }, { id: 46966 }];
        const datsCollection: IDats[] = [{ id: 123 }];
        expectedResult = service.addDatsToCollectionIfMissing(datsCollection, ...datsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dats: IDats = { id: 123 };
        const dats2: IDats = { id: 456 };
        expectedResult = service.addDatsToCollectionIfMissing([], dats, dats2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dats);
        expect(expectedResult).toContain(dats2);
      });

      it('should accept null and undefined values', () => {
        const dats: IDats = { id: 123 };
        expectedResult = service.addDatsToCollectionIfMissing([], null, dats, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dats);
      });

      it('should return initial array if no Dats is added', () => {
        const datsCollection: IDats[] = [{ id: 123 }];
        expectedResult = service.addDatsToCollectionIfMissing(datsCollection, undefined, null);
        expect(expectedResult).toEqual(datsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
