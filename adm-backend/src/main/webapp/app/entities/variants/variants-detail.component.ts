import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVariants } from 'app/shared/model/variants.model';

@Component({
    selector: 'jhi-variants-detail',
    templateUrl: './variants-detail.component.html'
})
export class VariantsDetailComponent implements OnInit {
    variants: IVariants;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ variants }) => {
            this.variants = variants;
        });
    }

    previousState() {
        window.history.back();
    }
}
