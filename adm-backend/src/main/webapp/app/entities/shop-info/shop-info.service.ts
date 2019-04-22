import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShopInfo } from 'app/shared/model/shop-info.model';

type EntityResponseType = HttpResponse<IShopInfo>;
type EntityArrayResponseType = HttpResponse<IShopInfo[]>;

@Injectable({ providedIn: 'root' })
export class ShopInfoService {
    public resourceUrl = SERVER_API_URL + 'api/shop-infos';

    constructor(protected http: HttpClient) {}

    create(shopInfo: IShopInfo): Observable<EntityResponseType> {
        return this.http.post<IShopInfo>(this.resourceUrl, shopInfo, { observe: 'response' });
    }

    update(shopInfo: IShopInfo): Observable<EntityResponseType> {
        return this.http.put<IShopInfo>(this.resourceUrl, shopInfo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IShopInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IShopInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
