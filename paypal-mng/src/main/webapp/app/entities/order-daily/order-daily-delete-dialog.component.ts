import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderDaily } from 'app/shared/model/order-daily.model';
import { OrderDailyService } from './order-daily.service';

@Component({
  selector: 'jhi-order-daily-delete-dialog',
  templateUrl: './order-daily-delete-dialog.component.html'
})
export class OrderDailyDeleteDialogComponent {
  orderDaily: IOrderDaily;

  constructor(
    protected orderDailyService: OrderDailyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderDailyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderDailyListModification',
        content: 'Deleted an orderDaily'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-daily-delete-popup',
  template: ''
})
export class OrderDailyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderDaily }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderDailyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderDaily = orderDaily;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/order-daily', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/order-daily', { outlets: { popup: null } }]);
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
