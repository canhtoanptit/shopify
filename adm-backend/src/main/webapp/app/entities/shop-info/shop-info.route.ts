import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ShopInfo } from 'app/shared/model/shop-info.model';
import { ShopInfoService } from './shop-info.service';
import { ShopInfoComponent } from './shop-info.component';
import { ShopInfoDetailComponent } from './shop-info-detail.component';
import { ShopInfoUpdateComponent } from './shop-info-update.component';
import { ShopInfoDeletePopupComponent } from './shop-info-delete-dialog.component';
import { IShopInfo } from 'app/shared/model/shop-info.model';

@Injectable({ providedIn: 'root' })
export class ShopInfoResolve implements Resolve<IShopInfo> {
    constructor(private service: ShopInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShopInfo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ShopInfo>) => response.ok),
                map((shopInfo: HttpResponse<ShopInfo>) => shopInfo.body)
            );
        }
        return of(new ShopInfo());
    }
}

export const shopInfoRoute: Routes = [
    {
        path: '',
        component: ShopInfoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ShopInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ShopInfoDetailComponent,
        resolve: {
            shopInfo: ShopInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShopInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ShopInfoUpdateComponent,
        resolve: {
            shopInfo: ShopInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShopInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ShopInfoUpdateComponent,
        resolve: {
            shopInfo: ShopInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShopInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shopInfoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ShopInfoDeletePopupComponent,
        resolve: {
            shopInfo: ShopInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShopInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
