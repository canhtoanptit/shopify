import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShopinfo } from 'app/shared/model/shopinfo.model';
import { ShopinfoService } from './shopinfo.service';

@Component({
    selector: 'jhi-shopinfo-delete-dialog',
    templateUrl: './shopinfo-delete-dialog.component.html'
})
export class ShopinfoDeleteDialogComponent {
    shopinfo: IShopinfo;

    constructor(protected shopinfoService: ShopinfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shopinfoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'shopinfoListModification',
                content: 'Deleted an shopinfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shopinfo-delete-popup',
    template: ''
})
export class ShopinfoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shopinfo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ShopinfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.shopinfo = shopinfo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/shopinfo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/shopinfo', { outlets: { popup: null } }]);
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
