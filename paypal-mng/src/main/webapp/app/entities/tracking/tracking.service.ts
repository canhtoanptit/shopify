import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITracking } from 'app/shared/model/tracking.model';

type EntityResponseType = HttpResponse<ITracking>;
type EntityArrayResponseType = HttpResponse<ITracking[]>;

@Injectable({ providedIn: 'root' })
export class TrackingService {
  public resourceUrl = SERVER_API_URL + 'api/trackings';

  constructor(protected http: HttpClient) {}

  create(tracking: ITracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tracking);
    return this.http
      .post<ITracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tracking: ITracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tracking);
    return this.http
      .put<ITracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITracking>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITracking[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tracking: ITracking): ITracking {
    const copy: ITracking = Object.assign({}, tracking, {
      createdAt: tracking.createdAt != null && tracking.createdAt.isValid() ? tracking.createdAt.toJSON() : null,
      updatedAt: tracking.updatedAt != null && tracking.updatedAt.isValid() ? tracking.updatedAt.toJSON() : null
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
      res.body.forEach((tracking: ITracking) => {
        tracking.createdAt = tracking.createdAt != null ? moment(tracking.createdAt) : null;
        tracking.updatedAt = tracking.updatedAt != null ? moment(tracking.updatedAt) : null;
      });
    }
    return res;
  }
}
