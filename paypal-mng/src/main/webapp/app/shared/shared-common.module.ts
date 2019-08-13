import { NgModule } from '@angular/core';

import { PaypalmngSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [PaypalmngSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [PaypalmngSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PaypalmngSharedCommonModule {}
