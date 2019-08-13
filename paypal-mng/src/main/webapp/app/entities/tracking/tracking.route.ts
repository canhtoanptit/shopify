import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tracking } from 'app/shared/model/tracking.model';
import { TrackingService } from './tracking.service';
import { TrackingComponent } from './tracking.component';
import { TrackingDetailComponent } from './tracking-detail.component';
import { TrackingUpdateComponent } from './tracking-update.component';
import { TrackingDeletePopupComponent } from './tracking-delete-dialog.component';
import { ITracking } from 'app/shared/model/tracking.model';

@Injectable({ providedIn: 'root' })
export class TrackingResolve implements Resolve<ITracking> {
  constructor(private service: TrackingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITracking> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tracking>) => response.ok),
        map((tracking: HttpResponse<Tracking>) => tracking.body)
      );
    }
    return of(new Tracking());
  }
}

export const trackingRoute: Routes = [
  {
    path: '',
    component: TrackingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Trackings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrackingDetailComponent,
    resolve: {
      tracking: TrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trackings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrackingUpdateComponent,
    resolve: {
      tracking: TrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trackings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrackingUpdateComponent,
    resolve: {
      tracking: TrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trackings'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const trackingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TrackingDeletePopupComponent,
    resolve: {
      tracking: TrackingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trackings'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
