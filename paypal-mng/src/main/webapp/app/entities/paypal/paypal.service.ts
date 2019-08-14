import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaypal } from 'app/shared/model/paypal.model';

type EntityResponseType = HttpResponse<IPaypal>;
type EntityArrayResponseType = HttpResponse<IPaypal[]>;

@Injectable({ providedIn: 'root' })
export class PaypalService {
  public resourceUrl = SERVER_API_URL + 'api/paypals';

  constructor(protected http: HttpClient) {}

  create(paypal: IPaypal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paypal);
    return this.http
      .post<IPaypal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paypal: IPaypal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paypal);
    return this.http
      .put<IPaypal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaypal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaypal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paypal: IPaypal): IPaypal {
    const copy: IPaypal = Object.assign({}, paypal, {
      createdAt: paypal.createdAt != null && paypal.createdAt.isValid() ? paypal.createdAt.toJSON() : null,
      updatedAt: paypal.updatedAt != null && paypal.updatedAt.isValid() ? paypal.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paypal: IPaypal) => {
        paypal.createdAt = paypal.createdAt != null ? moment(paypal.createdAt) : null;
        paypal.updatedAt = paypal.updatedAt != null ? moment(paypal.updatedAt) : null;
      });
    }
    return res;
  }
}
