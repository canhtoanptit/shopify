import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PaypalHistory } from 'app/shared/model/paypal-history.model';
import { PaypalHistoryService } from './paypal-history.service';
import { PaypalHistoryComponent } from './paypal-history.component';
import { PaypalHistoryDetailComponent } from './paypal-history-detail.component';
import { PaypalHistoryUpdateComponent } from './paypal-history-update.component';
import { PaypalHistoryDeletePopupComponent } from './paypal-history-delete-dialog.component';
import { IPaypalHistory } from 'app/shared/model/paypal-history.model';

@Injectable({ providedIn: 'root' })
export class PaypalHistoryResolve implements Resolve<IPaypalHistory> {
  constructor(private service: PaypalHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaypalHistory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PaypalHistory>) => response.ok),
        map((paypalHistory: HttpResponse<PaypalHistory>) => paypalHistory.body)
      );
    }
    return of(new PaypalHistory());
  }
}

export const paypalHistoryRoute: Routes = [
  {
    path: '',
    component: PaypalHistoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'PaypalHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaypalHistoryDetailComponent,
    resolve: {
      paypalHistory: PaypalHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaypalHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaypalHistoryUpdateComponent,
    resolve: {
      paypalHistory: PaypalHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaypalHistories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaypalHistoryUpdateComponent,
    resolve: {
      paypalHistory: PaypalHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaypalHistories'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const paypalHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PaypalHistoryDeletePopupComponent,
    resolve: {
      paypalHistory: PaypalHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaypalHistories'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
