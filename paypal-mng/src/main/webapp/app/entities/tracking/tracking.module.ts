import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaypalmngSharedModule } from 'app/shared';
import {
  TrackingComponent,
  TrackingDetailComponent,
  TrackingUpdateComponent,
  TrackingDeletePopupComponent,
  TrackingDeleteDialogComponent,
  trackingRoute,
  trackingPopupRoute
} from './';

const ENTITY_STATES = [...trackingRoute, ...trackingPopupRoute];

@NgModule({
  imports: [PaypalmngSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TrackingComponent,
    TrackingDetailComponent,
    TrackingUpdateComponent,
    TrackingDeleteDialogComponent,
    TrackingDeletePopupComponent
  ],
  entryComponents: [TrackingComponent, TrackingUpdateComponent, TrackingDeleteDialogComponent, TrackingDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaypalmngTrackingModule {}
