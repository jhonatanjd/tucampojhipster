import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDocumenType, DocumenType } from '../documen-type.model';
import { DocumenTypeService } from '../service/documen-type.service';

import { DocumenTypeRoutingResolveService } from './documen-type-routing-resolve.service';

describe('DocumenType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DocumenTypeRoutingResolveService;
  let service: DocumenTypeService;
  let resultDocumenType: IDocumenType | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(DocumenTypeRoutingResolveService);
    service = TestBed.inject(DocumenTypeService);
    resultDocumenType = undefined;
  });

  describe('resolve', () => {
    it('should return IDocumenType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumenType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumenType).toEqual({ id: 123 });
    });

    it('should return new IDocumenType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumenType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDocumenType).toEqual(new DocumenType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DocumenType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumenType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumenType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
