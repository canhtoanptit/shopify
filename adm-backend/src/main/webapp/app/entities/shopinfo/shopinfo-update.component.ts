import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IShopinfo } from 'app/shared/model/shopinfo.model';
import { ShopinfoService } from './shopinfo.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-shopinfo-update',
    templateUrl: './shopinfo-update.component.html'
})
export class ShopinfoUpdateComponent implements OnInit {
    shopinfo: IShopinfo;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected shopinfoService: ShopinfoService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shopinfo }) => {
            this.shopinfo = shopinfo;
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
        if (this.shopinfo.id !== undefined) {
            this.subscribeToSaveResponse(this.shopinfoService.update(this.shopinfo));
        } else {
            this.subscribeToSaveResponse(this.shopinfoService.create(this.shopinfo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IShopinfo>>) {
        result.subscribe((res: HttpResponse<IShopinfo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
