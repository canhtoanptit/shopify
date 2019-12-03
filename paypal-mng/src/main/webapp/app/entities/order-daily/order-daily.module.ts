import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaypalmngSharedModule } from 'app/shared';
import {
  OrderDailyComponent,
  OrderDailyDetailComponent,
  OrderDailyUpdateComponent,
  OrderDailyDeletePopupComponent,
  OrderDailyDeleteDialogComponent,
  orderDailyRoute,
  orderDailyPopupRoute
} from './';

const ENTITY_STATES = [...orderDailyRoute, ...orderDailyPopupRoute];

@NgModule({
  imports: [PaypalmngSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderDailyComponent,
    OrderDailyDetailComponent,
    OrderDailyUpdateComponent,
    OrderDailyDeleteDialogComponent,
    OrderDailyDeletePopupComponent
  ],
  entryComponents: [OrderDailyComponent, OrderDailyUpdateComponent, OrderDailyDeleteDialogComponent, OrderDailyDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaypalmngOrderDailyModule {}
