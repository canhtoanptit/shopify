import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShopInfo } from 'app/shared/model/shop-info.model';
import { ShopInfoService } from './shop-info.service';

@Component({
    selector: 'jhi-shop-info-delete-dialog',
    templateUrl: './shop-info-delete-dialog.component.html'
})
export class ShopInfoDeleteDialogComponent {
    shopInfo: IShopInfo;

    constructor(protected shopInfoService: ShopInfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shopInfoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'shopInfoListModification',
                content: 'Deleted an shopInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shop-info-delete-popup',
    template: ''
})
export class ShopInfoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shopInfo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ShopInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.shopInfo = shopInfo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/shop-info', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/shop-info', { outlets: { popup: null } }]);
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
