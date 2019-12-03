import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderDaily } from 'app/shared/model/order-daily.model';

type EntityResponseType = HttpResponse<IOrderDaily>;
type EntityArrayResponseType = HttpResponse<IOrderDaily[]>;

@Injectable({ providedIn: 'root' })
export class OrderDailyService {
  public resourceUrl = SERVER_API_URL + 'api/order-dailies';

  constructor(protected http: HttpClient) {}

  create(orderDaily: IOrderDaily): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderDaily);
    return this.http
      .post<IOrderDaily>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderDaily: IOrderDaily): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderDaily);
    return this.http
      .put<IOrderDaily>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderDaily>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderDaily[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderDaily: IOrderDaily): IOrderDaily {
    const copy: IOrderDaily = Object.assign({}, orderDaily, {
      paidAt: orderDaily.paidAt != null && orderDaily.paidAt.isValid() ? orderDaily.paidAt.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.paidAt = res.body.paidAt != null ? moment(res.body.paidAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderDaily: IOrderDaily) => {
        orderDaily.paidAt = orderDaily.paidAt != null ? moment(orderDaily.paidAt) : null;
      });
    }
    return res;
  }
}
