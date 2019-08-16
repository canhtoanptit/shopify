import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaypalHistory } from 'app/shared/model/paypal-history.model';
import { PaypalHistoryService } from './paypal-history.service';

@Component({
  selector: 'jhi-paypal-history-delete-dialog',
  templateUrl: './paypal-history-delete-dialog.component.html'
})
export class PaypalHistoryDeleteDialogComponent {
  paypalHistory: IPaypalHistory;

  constructor(
    protected paypalHistoryService: PaypalHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paypalHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'paypalHistoryListModification',
        content: 'Deleted an paypalHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-paypal-history-delete-popup',
  template: ''
})
export class PaypalHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paypalHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PaypalHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.paypalHistory = paypalHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/paypal-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/paypal-history', { outlets: { popup: null } }]);
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
