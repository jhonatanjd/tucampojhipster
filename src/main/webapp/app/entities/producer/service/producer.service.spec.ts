import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProducer, Producer } from '../producer.model';

import { ProducerService } from './producer.service';

describe('Producer Service', () => {
  let service: ProducerService;
  let httpMock: HttpTestingController;
  let elemDefault: IProducer;
  let expectedResult: IProducer | IProducer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProducerService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nameProduct: 'AAAAAAA',
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

    it('should create a Producer', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Producer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Producer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameProduct: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Producer', () => {
      const patchObject = Object.assign({}, new Producer());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Producer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameProduct: 'BBBBBB',
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

    it('should delete a Producer', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProducerToCollectionIfMissing', () => {
      it('should add a Producer to an empty array', () => {
        const producer: IProducer = { id: 123 };
        expectedResult = service.addProducerToCollectionIfMissing([], producer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(producer);
      });

      it('should not add a Producer to an array that contains it', () => {
        const producer: IProducer = { id: 123 };
        const producerCollection: IProducer[] = [
          {
            ...producer,
          },
          { id: 456 },
        ];
        expectedResult = service.addProducerToCollectionIfMissing(producerCollection, producer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Producer to an array that doesn't contain it", () => {
        const producer: IProducer = { id: 123 };
        const producerCollection: IProducer[] = [{ id: 456 }];
        expectedResult = service.addProducerToCollectionIfMissing(producerCollection, producer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(producer);
      });

      it('should add only unique Producer to an array', () => {
        const producerArray: IProducer[] = [{ id: 123 }, { id: 456 }, { id: 16503 }];
        const producerCollection: IProducer[] = [{ id: 123 }];
        expectedResult = service.addProducerToCollectionIfMissing(producerCollection, ...producerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const producer: IProducer = { id: 123 };
        const producer2: IProducer = { id: 456 };
        expectedResult = service.addProducerToCollectionIfMissing([], producer, producer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(producer);
        expect(expectedResult).toContain(producer2);
      });

      it('should accept null and undefined values', () => {
        const producer: IProducer = { id: 123 };
        expectedResult = service.addProducerToCollectionIfMissing([], null, producer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(producer);
      });

      it('should return initial array if no Producer is added', () => {
        const producerCollection: IProducer[] = [{ id: 123 }];
        expectedResult = service.addProducerToCollectionIfMissing(producerCollection, undefined, null);
        expect(expectedResult).toEqual(producerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
