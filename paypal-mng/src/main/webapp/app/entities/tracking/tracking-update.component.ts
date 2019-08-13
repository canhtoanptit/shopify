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
    tracking_number: [null, [Validators.required]],
    tracking_url: [null, [Validators.required]],
    paypal_tracker_id: [],
    created_at: [],
    updated_at: [],
    orderId: []
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
      tracking_number: tracking.tracking_number,
      tracking_url: tracking.tracking_url,
      paypal_tracker_id: tracking.paypal_tracker_id,
      created_at: tracking.created_at != null ? tracking.created_at.format(DATE_TIME_FORMAT) : null,
      updated_at: tracking.updated_at != null ? tracking.updated_at.format(DATE_TIME_FORMAT) : null,
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
      tracking_number: this.editForm.get(['tracking_number']).value,
      tracking_url: this.editForm.get(['tracking_url']).value,
      paypal_tracker_id: this.editForm.get(['paypal_tracker_id']).value,
      created_at:
        this.editForm.get(['created_at']).value != null ? moment(this.editForm.get(['created_at']).value, DATE_TIME_FORMAT) : undefined,
      updated_at:
        this.editForm.get(['updated_at']).value != null ? moment(this.editForm.get(['updated_at']).value, DATE_TIME_FORMAT) : undefined,
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
