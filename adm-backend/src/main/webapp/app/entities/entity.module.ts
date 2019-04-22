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
            },
            {
                path: 'product',
                loadChildren: './product/product.module#ShopifymProductModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#ShopifymProductModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#ShopifymProductModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#ShopifymProductModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#ShopifymProductModule'
            },
            {
                path: 'shop-info',
                loadChildren: './shop-info/shop-info.module#ShopifymShopInfoModule'
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
