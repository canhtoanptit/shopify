import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaypal } from 'app/shared/model/paypal.model';
import { PaypalService } from './paypal.service';

@Component({
  selector: 'jhi-paypal-delete-dialog',
  templateUrl: './paypal-delete-dialog.component.html'
})
export class PaypalDeleteDialogComponent {
  paypal: IPaypal;

  constructor(protected paypalService: PaypalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paypalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'paypalListModification',
        content: 'Deleted an paypal'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-paypal-delete-popup',
  template: ''
})
export class PaypalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paypal }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PaypalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.paypal = paypal;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/paypal', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/paypal', { outlets: { popup: null } }]);
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
