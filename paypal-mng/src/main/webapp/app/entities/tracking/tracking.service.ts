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
      created_at: tracking.created_at != null && tracking.created_at.isValid() ? tracking.created_at.toJSON() : null,
      updated_at: tracking.updated_at != null && tracking.updated_at.isValid() ? tracking.updated_at.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created_at = res.body.created_at != null ? moment(res.body.created_at) : null;
      res.body.updated_at = res.body.updated_at != null ? moment(res.body.updated_at) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tracking: ITracking) => {
        tracking.created_at = tracking.created_at != null ? moment(tracking.created_at) : null;
        tracking.updated_at = tracking.updated_at != null ? moment(tracking.updated_at) : null;
      });
    }
    return res;
  }
}
