import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITracking } from 'app/shared/model/tracking.model';
import { TrackingService } from './tracking.service';

@Component({
  selector: 'jhi-tracking-delete-dialog',
  templateUrl: './tracking-delete-dialog.component.html'
})
export class TrackingDeleteDialogComponent {
  tracking: ITracking;

  constructor(protected trackingService: TrackingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.trackingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'trackingListModification',
        content: 'Deleted an tracking'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tracking-delete-popup',
  template: ''
})
export class TrackingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tracking }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TrackingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tracking = tracking;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tracking', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tracking', { outlets: { popup: null } }]);
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
