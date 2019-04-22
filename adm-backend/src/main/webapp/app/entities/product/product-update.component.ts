import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IShopInfo } from 'app/shared/model/shop-info.model';
import { ShopInfoService } from 'app/entities/shop-info';

@Component({
    selector: 'jhi-product-update',
    templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
    product: IProduct;
    isSaving: boolean;

    shopinfos: IShopInfo[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productService: ProductService,
        protected shopInfoService: ShopInfoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ product }) => {
            this.product = product;
        });
        this.shopInfoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IShopInfo[]>) => mayBeOk.ok),
                map((response: HttpResponse<IShopInfo[]>) => response.body)
            )
            .subscribe((res: IShopInfo[]) => (this.shopinfos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.product.id !== undefined) {
            this.subscribeToSaveResponse(this.productService.update(this.product));
        } else {
            this.subscribeToSaveResponse(this.productService.create(this.product));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
        result.subscribe((res: HttpResponse<IProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackShopInfoById(index: number, item: IShopInfo) {
        return item.id;
    }
}
