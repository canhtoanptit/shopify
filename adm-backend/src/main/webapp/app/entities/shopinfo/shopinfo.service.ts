import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShopinfo } from 'app/shared/model/shopinfo.model';

type EntityResponseType = HttpResponse<IShopinfo>;
type EntityArrayResponseType = HttpResponse<IShopinfo[]>;

@Injectable({ providedIn: 'root' })
export class ShopinfoService {
    public resourceUrl = SERVER_API_URL + 'api/shopinfos';

    constructor(protected http: HttpClient) {}

    create(shopinfo: IShopinfo): Observable<EntityResponseType> {
        return this.http.post<IShopinfo>(this.resourceUrl, shopinfo, { observe: 'response' });
    }

    update(shopinfo: IShopinfo): Observable<EntityResponseType> {
        return this.http.put<IShopinfo>(this.resourceUrl, shopinfo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IShopinfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IShopinfo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
