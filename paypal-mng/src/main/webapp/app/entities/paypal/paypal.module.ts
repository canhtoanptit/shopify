import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaypalmngSharedModule } from 'app/shared';
import {
  PaypalComponent,
  PaypalDetailComponent,
  PaypalUpdateComponent,
  PaypalDeletePopupComponent,
  PaypalDeleteDialogComponent,
  paypalRoute,
  paypalPopupRoute
} from './';

const ENTITY_STATES = [...paypalRoute, ...paypalPopupRoute];

@NgModule({
  imports: [PaypalmngSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PaypalComponent, PaypalDetailComponent, PaypalUpdateComponent, PaypalDeleteDialogComponent, PaypalDeletePopupComponent],
  entryComponents: [PaypalComponent, PaypalUpdateComponent, PaypalDeleteDialogComponent, PaypalDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaypalmngPaypalModule {}
