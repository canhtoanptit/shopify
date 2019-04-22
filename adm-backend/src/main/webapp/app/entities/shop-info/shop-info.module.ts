import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShopifymSharedModule } from 'app/shared';
import {
    ShopInfoComponent,
    ShopInfoDetailComponent,
    ShopInfoUpdateComponent,
    ShopInfoDeletePopupComponent,
    ShopInfoDeleteDialogComponent,
    shopInfoRoute,
    shopInfoPopupRoute
} from './';

const ENTITY_STATES = [...shopInfoRoute, ...shopInfoPopupRoute];

@NgModule({
    imports: [ShopifymSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShopInfoComponent,
        ShopInfoDetailComponent,
        ShopInfoUpdateComponent,
        ShopInfoDeleteDialogComponent,
        ShopInfoDeletePopupComponent
    ],
    entryComponents: [ShopInfoComponent, ShopInfoUpdateComponent, ShopInfoDeleteDialogComponent, ShopInfoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShopifymShopInfoModule {}
