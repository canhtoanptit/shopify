import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShopifymbackendSharedModule } from 'app/shared';
import {
    VariantsComponent,
    VariantsDetailComponent,
    VariantsUpdateComponent,
    VariantsDeletePopupComponent,
    VariantsDeleteDialogComponent,
    variantsRoute,
    variantsPopupRoute
} from './';

const ENTITY_STATES = [...variantsRoute, ...variantsPopupRoute];

@NgModule({
    imports: [ShopifymbackendSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VariantsComponent,
        VariantsDetailComponent,
        VariantsUpdateComponent,
        VariantsDeleteDialogComponent,
        VariantsDeletePopupComponent
    ],
    entryComponents: [VariantsComponent, VariantsUpdateComponent, VariantsDeleteDialogComponent, VariantsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShopifymbackendVariantsModule {}
