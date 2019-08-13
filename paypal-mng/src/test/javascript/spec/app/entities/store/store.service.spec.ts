/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StoreService } from 'app/entities/store/store.service';
import { IStore, Store } from 'app/shared/model/store.model';

describe('Service Tests', () => {
  describe('Store Service', () => {
    let injector: TestBed;
    let service: StoreService;
    let httpMock: HttpTestingController;
    let elemDefault: IStore;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(StoreService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Store(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            created_at: currentDate.format(DATE_TIME_FORMAT),
            updated_at: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Store', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            created_at: currentDate.format(DATE_TIME_FORMAT),
            updated_at: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            created_at: currentDate,
            updated_at: currentDate
          },
          returnedFromService
        );
        service
          .create(new Store(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Store', async () => {
        const returnedFromService = Object.assign(
          {
            shopify_api_key: 'BBBBBB',
            shopify_api_password: 'BBBBBB',
            store_name: 'BBBBBB',
            created_at: currentDate.format(DATE_TIME_FORMAT),
            updated_at: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            created_at: currentDate,
            updated_at: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Store', async () => {
        const returnedFromService = Object.assign(
          {
            shopify_api_key: 'BBBBBB',
            shopify_api_password: 'BBBBBB',
            store_name: 'BBBBBB',
            created_at: currentDate.format(DATE_TIME_FORMAT),
            updated_at: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            created_at: currentDate,
            updated_at: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Store', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
