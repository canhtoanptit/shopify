import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Shopinfo } from 'app/shared/model/shopinfo.model';
import { ShopinfoService } from './shopinfo.service';
import { ShopinfoComponent } from './shopinfo.component';
import { ShopinfoDetailComponent } from './shopinfo-detail.component';
import { ShopinfoUpdateComponent } from './shopinfo-update.component';
import { ShopinfoDeletePopupComponent } from './shopinfo-delete-dialog.component';
import { IShopinfo } from 'app/shared/model/shopinfo.model';

@Injectable({ providedIn: 'root' })
export class ShopinfoResolve implements Resolve<IShopinfo> {
    constructor(private service: ShopinfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShopinfo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Shopinfo>) => response.ok),
                map((shopinfo: HttpResponse<Shopinfo>) => shopinfo.body)
            );
        }
        return of(new Shopinfo());
    }
}

export const shopinfoRoute: Routes = [
    {
        path: '',
        component: ShopinfoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Shopinfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ShopinfoDetailComponent,
        resolve: {
            shopinfo: ShopinfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shopinfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ShopinfoUpdateComponent,
        resolve: {
            shopinfo: ShopinfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shopinfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ShopinfoUpdateComponent,
        resolve: {
            shopinfo: ShopinfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shopinfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shopinfoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ShopinfoDeletePopupComponent,
        resolve: {
            shopinfo: ShopinfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Shopinfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
