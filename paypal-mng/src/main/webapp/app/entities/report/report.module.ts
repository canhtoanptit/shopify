import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaypalmngSharedModule } from 'app/shared';
import {
  ReportComponent,
  ReportDetailComponent,
  reportRoute,
} from './';

const ENTITY_STATES = [...reportRoute];

@NgModule({
  imports: [PaypalmngSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ReportComponent, ReportDetailComponent],
  entryComponents: [ReportComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaypalmngReportModule {}
