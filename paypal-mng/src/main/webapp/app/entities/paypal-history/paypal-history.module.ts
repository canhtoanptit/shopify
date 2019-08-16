import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaypalmngSharedModule } from 'app/shared';
import {
  PaypalHistoryComponent,
  PaypalHistoryDetailComponent,
  PaypalHistoryUpdateComponent,
  PaypalHistoryDeletePopupComponent,
  PaypalHistoryDeleteDialogComponent,
  paypalHistoryRoute,
  paypalHistoryPopupRoute
} from './';

const ENTITY_STATES = [...paypalHistoryRoute, ...paypalHistoryPopupRoute];

@NgModule({
  imports: [PaypalmngSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PaypalHistoryComponent,
    PaypalHistoryDetailComponent,
    PaypalHistoryUpdateComponent,
    PaypalHistoryDeleteDialogComponent,
    PaypalHistoryDeletePopupComponent
  ],
  entryComponents: [
    PaypalHistoryComponent,
    PaypalHistoryUpdateComponent,
    PaypalHistoryDeleteDialogComponent,
    PaypalHistoryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaypalmngPaypalHistoryModule {}
