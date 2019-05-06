import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVariants } from 'app/shared/model/variants.model';

type EntityResponseType = HttpResponse<IVariants>;
type EntityArrayResponseType = HttpResponse<IVariants[]>;

@Injectable({ providedIn: 'root' })
export class VariantsService {
    public resourceUrl = SERVER_API_URL + 'api/variants';

    constructor(protected http: HttpClient) {}

    create(variants: IVariants): Observable<EntityResponseType> {
        return this.http.post<IVariants>(this.resourceUrl, variants, { observe: 'response' });
    }

    update(variants: IVariants): Observable<EntityResponseType> {
        return this.http.put<IVariants>(this.resourceUrl, variants, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVariants>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVariants[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
