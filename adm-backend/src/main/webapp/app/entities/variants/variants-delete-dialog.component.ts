import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVariants } from 'app/shared/model/variants.model';
import { VariantsService } from './variants.service';

@Component({
    selector: 'jhi-variants-delete-dialog',
    templateUrl: './variants-delete-dialog.component.html'
})
export class VariantsDeleteDialogComponent {
    variants: IVariants;

    constructor(protected variantsService: VariantsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.variantsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'variantsListModification',
                content: 'Deleted an variants'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-variants-delete-popup',
    template: ''
})
export class VariantsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ variants }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VariantsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.variants = variants;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/variants', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/variants', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
