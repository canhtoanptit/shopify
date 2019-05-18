import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'variants',
                loadChildren: './variants/variants.module#ShopifymbackendVariantsModule'
            },
            {
                path: 'variants',
                loadChildren: './variants/variants.module#ShopifymbackendVariantsModule'
            },
            {
                path: 'variants',
                loadChildren: './variants/variants.module#ShopifymbackendVariantsModule'
            },
            {
                path: 'variants',
                loadChildren: './variants/variants.module#ShopifymbackendVariantsModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShopifymbackendEntityModule {}
