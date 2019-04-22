import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShopInfo } from 'app/shared/model/shop-info.model';

@Component({
    selector: 'jhi-shop-info-detail',
    templateUrl: './shop-info-detail.component.html'
})
export class ShopInfoDetailComponent implements OnInit {
    shopInfo: IShopInfo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shopInfo }) => {
            this.shopInfo = shopInfo;
        });
    }

    previousState() {
        window.history.back();
    }
}
