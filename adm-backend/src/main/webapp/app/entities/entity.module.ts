import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'shopinfo',
                loadChildren: './shopinfo/shopinfo.module#ShopifymShopinfoModule'
            },
            {
                path: 'shopinfo',
                loadChildren: './shopinfo/shopinfo.module#ShopifymShopinfoModule'
            },
            {
                path: 'shopinfo',
                loadChildren: './shopinfo/shopinfo.module#ShopifymShopinfoModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShopifymEntityModule {}
