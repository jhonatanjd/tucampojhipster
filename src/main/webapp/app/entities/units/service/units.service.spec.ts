import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUnits, Units } from '../units.model';

import { UnitsService } from './units.service';

describe('Units Service', () => {
  let service: UnitsService;
  let httpMock: HttpTestingController;
  let elemDefault: IUnits;
  let expectedResult: IUnits | IUnits[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UnitsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      description: 'AAAAAAA',
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

    it('should create a Units', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Units()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Units', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          description: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Units', () => {
      const patchObject = Object.assign({}, new Units());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Units', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          description: 'BBBBBB',
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

    it('should delete a Units', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUnitsToCollectionIfMissing', () => {
      it('should add a Units to an empty array', () => {
        const units: IUnits = { id: 123 };
        expectedResult = service.addUnitsToCollectionIfMissing([], units);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(units);
      });

      it('should not add a Units to an array that contains it', () => {
        const units: IUnits = { id: 123 };
        const unitsCollection: IUnits[] = [
          {
            ...units,
          },
          { id: 456 },
        ];
        expectedResult = service.addUnitsToCollectionIfMissing(unitsCollection, units);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Units to an array that doesn't contain it", () => {
        const units: IUnits = { id: 123 };
        const unitsCollection: IUnits[] = [{ id: 456 }];
        expectedResult = service.addUnitsToCollectionIfMissing(unitsCollection, units);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(units);
      });

      it('should add only unique Units to an array', () => {
        const unitsArray: IUnits[] = [{ id: 123 }, { id: 456 }, { id: 61535 }];
        const unitsCollection: IUnits[] = [{ id: 123 }];
        expectedResult = service.addUnitsToCollectionIfMissing(unitsCollection, ...unitsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const units: IUnits = { id: 123 };
        const units2: IUnits = { id: 456 };
        expectedResult = service.addUnitsToCollectionIfMissing([], units, units2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(units);
        expect(expectedResult).toContain(units2);
      });

      it('should accept null and undefined values', () => {
        const units: IUnits = { id: 123 };
        expectedResult = service.addUnitsToCollectionIfMissing([], null, units, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(units);
      });

      it('should return initial array if no Units is added', () => {
        const unitsCollection: IUnits[] = [{ id: 123 }];
        expectedResult = service.addUnitsToCollectionIfMissing(unitsCollection, undefined, null);
        expect(expectedResult).toEqual(unitsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
