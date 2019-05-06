import { NgModule } from '@angular/core';

import { ShopifymbackendSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ShopifymbackendSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ShopifymbackendSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ShopifymbackendSharedCommonModule {}
