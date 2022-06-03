import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Transportations } from 'app/entities/enumerations/transportations.model';
import { ISale, Sale } from '../sale.model';

import { SaleService } from './sale.service';

describe('Sale Service', () => {
  let service: SaleService;
  let httpMock: HttpTestingController;
  let elemDefault: ISale;
  let expectedResult: ISale | ISale[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SaleService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      nameProducts: 'AAAAAAA',
      amountKilo: 0,
      priceKilo: 0,
      priceTotal: 0,
      city: 'AAAAAAA',
      availableDate: currentDate,
      stateTransportations: Transportations.NO,
      descriptions: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          availableDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Sale', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          availableDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          availableDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Sale()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Sale', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameProducts: 'BBBBBB',
          amountKilo: 1,
          priceKilo: 1,
          priceTotal: 1,
          city: 'BBBBBB',
          availableDate: currentDate.format(DATE_FORMAT),
          stateTransportations: 'BBBBBB',
          descriptions: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          availableDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Sale', () => {
      const patchObject = Object.assign(
        {
          nameProducts: 'BBBBBB',
          amountKilo: 1,
          priceKilo: 1,
          stateTransportations: 'BBBBBB',
        },
        new Sale()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          availableDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Sale', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameProducts: 'BBBBBB',
          amountKilo: 1,
          priceKilo: 1,
          priceTotal: 1,
          city: 'BBBBBB',
          availableDate: currentDate.format(DATE_FORMAT),
          stateTransportations: 'BBBBBB',
          descriptions: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          availableDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Sale', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSaleToCollectionIfMissing', () => {
      it('should add a Sale to an empty array', () => {
        const sale: ISale = { id: 123 };
        expectedResult = service.addSaleToCollectionIfMissing([], sale);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sale);
      });

      it('should not add a Sale to an array that contains it', () => {
        const sale: ISale = { id: 123 };
        const saleCollection: ISale[] = [
          {
            ...sale,
          },
          { id: 456 },
        ];
        expectedResult = service.addSaleToCollectionIfMissing(saleCollection, sale);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Sale to an array that doesn't contain it", () => {
        const sale: ISale = { id: 123 };
        const saleCollection: ISale[] = [{ id: 456 }];
        expectedResult = service.addSaleToCollectionIfMissing(saleCollection, sale);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sale);
      });

      it('should add only unique Sale to an array', () => {
        const saleArray: ISale[] = [{ id: 123 }, { id: 456 }, { id: 38643 }];
        const saleCollection: ISale[] = [{ id: 123 }];
        expectedResult = service.addSaleToCollectionIfMissing(saleCollection, ...saleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sale: ISale = { id: 123 };
        const sale2: ISale = { id: 456 };
        expectedResult = service.addSaleToCollectionIfMissing([], sale, sale2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sale);
        expect(expectedResult).toContain(sale2);
      });

      it('should accept null and undefined values', () => {
        const sale: ISale = { id: 123 };
        expectedResult = service.addSaleToCollectionIfMissing([], null, sale, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sale);
      });

      it('should return initial array if no Sale is added', () => {
        const saleCollection: ISale[] = [{ id: 123 }];
        expectedResult = service.addSaleToCollectionIfMissing(saleCollection, undefined, null);
        expect(expectedResult).toEqual(saleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
