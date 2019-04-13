import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShopinfo } from 'app/shared/model/shopinfo.model';

@Component({
    selector: 'jhi-shopinfo-detail',
    templateUrl: './shopinfo-detail.component.html'
})
export class ShopinfoDetailComponent implements OnInit {
    shopinfo: IShopinfo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shopinfo }) => {
            this.shopinfo = shopinfo;
        });
    }

    previousState() {
        window.history.back();
    }
}
