import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IShopping, Shopping } from '../shopping.model';

import { ShoppingService } from './shopping.service';

describe('Shopping Service', () => {
  let service: ShoppingService;
  let httpMock: HttpTestingController;
  let elemDefault: IShopping;
  let expectedResult: IShopping | IShopping[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShoppingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nameProducts: 'AAAAAAA',
      amount: 0,
      directions: 'AAAAAAA',
      city: 'AAAAAAA',
      orderdate: currentDate,
      dateOfDelivery: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          orderdate: currentDate.format(DATE_FORMAT),
          dateOfDelivery: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Shopping', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          orderdate: currentDate.format(DATE_FORMAT),
          dateOfDelivery: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          orderdate: currentDate,
          dateOfDelivery: currentDate,
        },
        returnedFromService
      );

      service.create(new Shopping()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Shopping', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameProducts: 'BBBBBB',
          amount: 1,
          directions: 'BBBBBB',
          city: 'BBBBBB',
          orderdate: currentDate.format(DATE_FORMAT),
          dateOfDelivery: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          orderdate: currentDate,
          dateOfDelivery: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Shopping', () => {
      const patchObject = Object.assign(
        {
          nameProducts: 'BBBBBB',
          directions: 'BBBBBB',
        },
        new Shopping()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          orderdate: currentDate,
          dateOfDelivery: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Shopping', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameProducts: 'BBBBBB',
          amount: 1,
          directions: 'BBBBBB',
          city: 'BBBBBB',
          orderdate: currentDate.format(DATE_FORMAT),
          dateOfDelivery: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          orderdate: currentDate,
          dateOfDelivery: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Shopping', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addShoppingToCollectionIfMissing', () => {
      it('should add a Shopping to an empty array', () => {
        const shopping: IShopping = { id: 123 };
        expectedResult = service.addShoppingToCollectionIfMissing([], shopping);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shopping);
      });

      it('should not add a Shopping to an array that contains it', () => {
        const shopping: IShopping = { id: 123 };
        const shoppingCollection: IShopping[] = [
          {
            ...shopping,
          },
          { id: 456 },
        ];
        expectedResult = service.addShoppingToCollectionIfMissing(shoppingCollection, shopping);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Shopping to an array that doesn't contain it", () => {
        const shopping: IShopping = { id: 123 };
        const shoppingCollection: IShopping[] = [{ id: 456 }];
        expectedResult = service.addShoppingToCollectionIfMissing(shoppingCollection, shopping);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shopping);
      });

      it('should add only unique Shopping to an array', () => {
        const shoppingArray: IShopping[] = [{ id: 123 }, { id: 456 }, { id: 52591 }];
        const shoppingCollection: IShopping[] = [{ id: 123 }];
        expectedResult = service.addShoppingToCollectionIfMissing(shoppingCollection, ...shoppingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shopping: IShopping = { id: 123 };
        const shopping2: IShopping = { id: 456 };
        expectedResult = service.addShoppingToCollectionIfMissing([], shopping, shopping2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shopping);
        expect(expectedResult).toContain(shopping2);
      });

      it('should accept null and undefined values', () => {
        const shopping: IShopping = { id: 123 };
        expectedResult = service.addShoppingToCollectionIfMissing([], null, shopping, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shopping);
      });

      it('should return initial array if no Shopping is added', () => {
        const shoppingCollection: IShopping[] = [{ id: 123 }];
        expectedResult = service.addShoppingToCollectionIfMissing(shoppingCollection, undefined, null);
        expect(expectedResult).toEqual(shoppingCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
