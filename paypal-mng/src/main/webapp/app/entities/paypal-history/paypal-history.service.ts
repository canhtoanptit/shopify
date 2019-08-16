import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaypalHistory } from 'app/shared/model/paypal-history.model';

type EntityResponseType = HttpResponse<IPaypalHistory>;
type EntityArrayResponseType = HttpResponse<IPaypalHistory[]>;

@Injectable({ providedIn: 'root' })
export class PaypalHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/paypal-histories';

  constructor(protected http: HttpClient) {}

  create(paypalHistory: IPaypalHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paypalHistory);
    return this.http
      .post<IPaypalHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paypalHistory: IPaypalHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paypalHistory);
    return this.http
      .put<IPaypalHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaypalHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaypalHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paypalHistory: IPaypalHistory): IPaypalHistory {
    const copy: IPaypalHistory = Object.assign({}, paypalHistory, {
      createdAt: paypalHistory.createdAt != null && paypalHistory.createdAt.isValid() ? paypalHistory.createdAt.toJSON() : null,
      updatedAt: paypalHistory.updatedAt != null && paypalHistory.updatedAt.isValid() ? paypalHistory.updatedAt.toJSON() : null
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
      res.body.forEach((paypalHistory: IPaypalHistory) => {
        paypalHistory.createdAt = paypalHistory.createdAt != null ? moment(paypalHistory.createdAt) : null;
        paypalHistory.updatedAt = paypalHistory.updatedAt != null ? moment(paypalHistory.updatedAt) : null;
      });
    }
    return res;
  }
}
