import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PaypalmngSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PaypalmngSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [PaypalmngSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PaypalmngSharedModule {
  static forRoot() {
    return {
      ngModule: PaypalmngSharedModule
    };
  }
}
