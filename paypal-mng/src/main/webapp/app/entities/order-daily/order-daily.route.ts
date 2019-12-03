import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderDaily } from 'app/shared/model/order-daily.model';
import { OrderDailyService } from './order-daily.service';
import { OrderDailyComponent } from './order-daily.component';
import { OrderDailyDetailComponent } from './order-daily-detail.component';
import { OrderDailyUpdateComponent } from './order-daily-update.component';
import { OrderDailyDeletePopupComponent } from './order-daily-delete-dialog.component';
import { IOrderDaily } from 'app/shared/model/order-daily.model';

@Injectable({ providedIn: 'root' })
export class OrderDailyResolve implements Resolve<IOrderDaily> {
  constructor(private service: OrderDailyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderDaily> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderDaily>) => response.ok),
        map((orderDaily: HttpResponse<OrderDaily>) => orderDaily.body)
      );
    }
    return of(new OrderDaily());
  }
}

export const orderDailyRoute: Routes = [
  {
    path: '',
    component: OrderDailyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'OrderDailies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderDailyDetailComponent,
    resolve: {
      orderDaily: OrderDailyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrderDailies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderDailyUpdateComponent,
    resolve: {
      orderDaily: OrderDailyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrderDailies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderDailyUpdateComponent,
    resolve: {
      orderDaily: OrderDailyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrderDailies'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderDailyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrderDailyDeletePopupComponent,
    resolve: {
      orderDaily: OrderDailyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrderDailies'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
