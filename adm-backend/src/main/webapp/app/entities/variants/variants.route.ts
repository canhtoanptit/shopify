import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Variants } from 'app/shared/model/variants.model';
import { VariantsService } from './variants.service';
import { VariantsComponent } from './variants.component';
import { VariantsDetailComponent } from './variants-detail.component';
import { VariantsUpdateComponent } from './variants-update.component';
import { VariantsDeletePopupComponent } from './variants-delete-dialog.component';
import { IVariants } from 'app/shared/model/variants.model';

@Injectable({ providedIn: 'root' })
export class VariantsResolve implements Resolve<IVariants> {
    constructor(private service: VariantsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVariants> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Variants>) => response.ok),
                map((variants: HttpResponse<Variants>) => variants.body)
            );
        }
        return of(new Variants());
    }
}

export const variantsRoute: Routes = [
    {
        path: '',
        component: VariantsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VariantsDetailComponent,
        resolve: {
            variants: VariantsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VariantsUpdateComponent,
        resolve: {
            variants: VariantsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VariantsUpdateComponent,
        resolve: {
            variants: VariantsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const variantsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VariantsDeletePopupComponent,
        resolve: {
            variants: VariantsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Variants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
