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
