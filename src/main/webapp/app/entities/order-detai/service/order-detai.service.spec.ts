import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrderDetai, OrderDetai } from '../order-detai.model';

import { OrderDetaiService } from './order-detai.service';

describe('OrderDetai Service', () => {
  let service: OrderDetaiService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrderDetai;
  let expectedResult: IOrderDetai | IOrderDetai[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrderDetaiService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
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

    it('should create a OrderDetai', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new OrderDetai()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderDetai', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrderDetai', () => {
      const patchObject = Object.assign({}, new OrderDetai());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrderDetai', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a OrderDetai', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrderDetaiToCollectionIfMissing', () => {
      it('should add a OrderDetai to an empty array', () => {
        const orderDetai: IOrderDetai = { id: 123 };
        expectedResult = service.addOrderDetaiToCollectionIfMissing([], orderDetai);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderDetai);
      });

      it('should not add a OrderDetai to an array that contains it', () => {
        const orderDetai: IOrderDetai = { id: 123 };
        const orderDetaiCollection: IOrderDetai[] = [
          {
            ...orderDetai,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrderDetaiToCollectionIfMissing(orderDetaiCollection, orderDetai);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderDetai to an array that doesn't contain it", () => {
        const orderDetai: IOrderDetai = { id: 123 };
        const orderDetaiCollection: IOrderDetai[] = [{ id: 456 }];
        expectedResult = service.addOrderDetaiToCollectionIfMissing(orderDetaiCollection, orderDetai);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderDetai);
      });

      it('should add only unique OrderDetai to an array', () => {
        const orderDetaiArray: IOrderDetai[] = [{ id: 123 }, { id: 456 }, { id: 6249 }];
        const orderDetaiCollection: IOrderDetai[] = [{ id: 123 }];
        expectedResult = service.addOrderDetaiToCollectionIfMissing(orderDetaiCollection, ...orderDetaiArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderDetai: IOrderDetai = { id: 123 };
        const orderDetai2: IOrderDetai = { id: 456 };
        expectedResult = service.addOrderDetaiToCollectionIfMissing([], orderDetai, orderDetai2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderDetai);
        expect(expectedResult).toContain(orderDetai2);
      });

      it('should accept null and undefined values', () => {
        const orderDetai: IOrderDetai = { id: 123 };
        expectedResult = service.addOrderDetaiToCollectionIfMissing([], null, orderDetai, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderDetai);
      });

      it('should return initial array if no OrderDetai is added', () => {
        const orderDetaiCollection: IOrderDetai[] = [{ id: 123 }];
        expectedResult = service.addOrderDetaiToCollectionIfMissing(orderDetaiCollection, undefined, null);
        expect(expectedResult).toEqual(orderDetaiCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
