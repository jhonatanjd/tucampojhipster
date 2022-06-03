import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWayToPay, WayToPay } from '../way-to-pay.model';

import { WayToPayService } from './way-to-pay.service';

describe('WayToPay Service', () => {
  let service: WayToPayService;
  let httpMock: HttpTestingController;
  let elemDefault: IWayToPay;
  let expectedResult: IWayToPay | IWayToPay[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WayToPayService);
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

    it('should create a WayToPay', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new WayToPay()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WayToPay', () => {
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

    it('should partial update a WayToPay', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
        },
        new WayToPay()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WayToPay', () => {
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

    it('should delete a WayToPay', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addWayToPayToCollectionIfMissing', () => {
      it('should add a WayToPay to an empty array', () => {
        const wayToPay: IWayToPay = { id: 123 };
        expectedResult = service.addWayToPayToCollectionIfMissing([], wayToPay);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wayToPay);
      });

      it('should not add a WayToPay to an array that contains it', () => {
        const wayToPay: IWayToPay = { id: 123 };
        const wayToPayCollection: IWayToPay[] = [
          {
            ...wayToPay,
          },
          { id: 456 },
        ];
        expectedResult = service.addWayToPayToCollectionIfMissing(wayToPayCollection, wayToPay);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WayToPay to an array that doesn't contain it", () => {
        const wayToPay: IWayToPay = { id: 123 };
        const wayToPayCollection: IWayToPay[] = [{ id: 456 }];
        expectedResult = service.addWayToPayToCollectionIfMissing(wayToPayCollection, wayToPay);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wayToPay);
      });

      it('should add only unique WayToPay to an array', () => {
        const wayToPayArray: IWayToPay[] = [{ id: 123 }, { id: 456 }, { id: 83693 }];
        const wayToPayCollection: IWayToPay[] = [{ id: 123 }];
        expectedResult = service.addWayToPayToCollectionIfMissing(wayToPayCollection, ...wayToPayArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const wayToPay: IWayToPay = { id: 123 };
        const wayToPay2: IWayToPay = { id: 456 };
        expectedResult = service.addWayToPayToCollectionIfMissing([], wayToPay, wayToPay2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wayToPay);
        expect(expectedResult).toContain(wayToPay2);
      });

      it('should accept null and undefined values', () => {
        const wayToPay: IWayToPay = { id: 123 };
        expectedResult = service.addWayToPayToCollectionIfMissing([], null, wayToPay, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wayToPay);
      });

      it('should return initial array if no WayToPay is added', () => {
        const wayToPayCollection: IWayToPay[] = [{ id: 123 }];
        expectedResult = service.addWayToPayToCollectionIfMissing(wayToPayCollection, undefined, null);
        expect(expectedResult).toEqual(wayToPayCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
