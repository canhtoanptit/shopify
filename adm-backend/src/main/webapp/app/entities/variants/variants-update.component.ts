import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IVariants } from 'app/shared/model/variants.model';
import { VariantsService } from './variants.service';

@Component({
    selector: 'jhi-variants-update',
    templateUrl: './variants-update.component.html'
})
export class VariantsUpdateComponent implements OnInit {
    variants: IVariants;
    isSaving: boolean;

    constructor(protected variantsService: VariantsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ variants }) => {
            this.variants = variants;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.variants.id !== undefined) {
            this.subscribeToSaveResponse(this.variantsService.update(this.variants));
        } else {
            this.subscribeToSaveResponse(this.variantsService.create(this.variants));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVariants>>) {
        result.subscribe((res: HttpResponse<IVariants>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
