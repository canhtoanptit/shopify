import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IShopInfo } from 'app/shared/model/shop-info.model';
import { ShopInfoService } from './shop-info.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-shop-info-update',
    templateUrl: './shop-info-update.component.html'
})
export class ShopInfoUpdateComponent implements OnInit {
    shopInfo: IShopInfo;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected shopInfoService: ShopInfoService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shopInfo }) => {
            this.shopInfo = shopInfo;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shopInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.shopInfoService.update(this.shopInfo));
        } else {
            this.subscribeToSaveResponse(this.shopInfoService.create(this.shopInfo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IShopInfo>>) {
        result.subscribe((res: HttpResponse<IShopInfo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
