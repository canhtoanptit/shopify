import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Paypal } from 'app/shared/model/paypal.model';
import { PaypalService } from './paypal.service';
import { PaypalComponent } from './paypal.component';
import { PaypalDetailComponent } from './paypal-detail.component';
import { PaypalUpdateComponent } from './paypal-update.component';
import { PaypalDeletePopupComponent } from './paypal-delete-dialog.component';
import { IPaypal } from 'app/shared/model/paypal.model';

@Injectable({ providedIn: 'root' })
export class PaypalResolve implements Resolve<IPaypal> {
  constructor(private service: PaypalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaypal> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Paypal>) => response.ok),
        map((paypal: HttpResponse<Paypal>) => paypal.body)
      );
    }
    return of(new Paypal());
  }
}

export const paypalRoute: Routes = [
  {
    path: '',
    component: PaypalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'Paypals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaypalDetailComponent,
    resolve: {
      paypal: PaypalResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'Paypals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaypalUpdateComponent,
    resolve: {
      paypal: PaypalResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'Paypals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaypalUpdateComponent,
    resolve: {
      paypal: PaypalResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'Paypals'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const paypalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PaypalDeletePopupComponent,
    resolve: {
      paypal: PaypalResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'Paypals'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
