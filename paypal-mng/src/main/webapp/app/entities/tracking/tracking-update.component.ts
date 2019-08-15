import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITracking, Tracking } from 'app/shared/model/tracking.model';
import { TrackingService } from './tracking.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order';

@Component({
  selector: 'jhi-tracking-update',
  templateUrl: './tracking-update.component.html'
})
export class TrackingUpdateComponent implements OnInit {
  isSaving: boolean;

  orders: IOrder[];

  editForm = this.fb.group({
    id: [],
    trackingNumber: [null, [Validators.required]],
    trackingCompany: [null, [Validators.required]],
    trackingUrl: [null, [Validators.required]],
    paypalTrackerId: [],
    createdAt: [],
    updatedAt: [],
    orderId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected trackingService: TrackingService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tracking }) => {
      this.updateForm(tracking);
    });
    this.orderService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrder[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrder[]>) => response.body)
      )
      .subscribe((res: IOrder[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tracking: ITracking) {
    this.editForm.patchValue({
      id: tracking.id,
      trackingNumber: tracking.trackingNumber,
      trackingCompany: tracking.trackingCompany,
      trackingUrl: tracking.trackingUrl,
      paypalTrackerId: tracking.paypalTrackerId,
      createdAt: tracking.createdAt != null ? tracking.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: tracking.updatedAt != null ? tracking.updatedAt.format(DATE_TIME_FORMAT) : null,
      orderId: tracking.orderId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tracking = this.createFromForm();
    if (tracking.id !== undefined) {
      this.subscribeToSaveResponse(this.trackingService.update(tracking));
    } else {
      this.subscribeToSaveResponse(this.trackingService.create(tracking));
    }
  }

  private createFromForm(): ITracking {
    return {
      ...new Tracking(),
      id: this.editForm.get(['id']).value,
      trackingNumber: this.editForm.get(['trackingNumber']).value,
      trackingCompany: this.editForm.get(['trackingCompany']).value,
      trackingUrl: this.editForm.get(['trackingUrl']).value,
      paypalTrackerId: this.editForm.get(['paypalTrackerId']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      orderId: this.editForm.get(['orderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITracking>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
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

  trackOrderById(index: number, item: IOrder) {
    return item.id;
  }
}
