import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'paypal',
        loadChildren: () => import('./paypal/paypal.module').then(m => m.PaypalmngPaypalModule)
      },
      {
        path: 'store',
        loadChildren: () => import('./store/store.module').then(m => m.PaypalmngStoreModule)
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.PaypalmngOrderModule)
      },
      {
        path: 'tracking',
        loadChildren: () => import('./tracking/tracking.module').then(m => m.PaypalmngTrackingModule)
      },
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.PaypalmngTransactionModule)
      },
      {
        path: 'paypal-history',
        loadChildren: () => import('./paypal-history/paypal-history.module').then(m => m.PaypalmngPaypalHistoryModule)
      },
      {
        path: 'report',
        loadChildren: () => import('./report/report.module').then(m => m.PaypalmngReportModule)
      },
      {
        path: 'order-daily',
        loadChildren: () => import('./order-daily/order-daily.module').then(m => m.PaypalmngOrderDailyModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaypalmngEntityModule {}
