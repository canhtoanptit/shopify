import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShopifymSharedModule } from 'app/shared';
import {
    ShopinfoComponent,
    ShopinfoDetailComponent,
    ShopinfoUpdateComponent,
    ShopinfoDeletePopupComponent,
    ShopinfoDeleteDialogComponent,
    shopinfoRoute,
    shopinfoPopupRoute
} from './';

const ENTITY_STATES = [...shopinfoRoute, ...shopinfoPopupRoute];

@NgModule({
    imports: [ShopifymSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShopinfoComponent,
        ShopinfoDetailComponent,
        ShopinfoUpdateComponent,
        ShopinfoDeleteDialogComponent,
        ShopinfoDeletePopupComponent
    ],
    entryComponents: [ShopinfoComponent, ShopinfoUpdateComponent, ShopinfoDeleteDialogComponent, ShopinfoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShopifymShopinfoModule {}
