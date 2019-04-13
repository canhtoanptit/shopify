import { NgModule } from '@angular/core';

import { ShopifymSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ShopifymSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ShopifymSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ShopifymSharedCommonModule {}
