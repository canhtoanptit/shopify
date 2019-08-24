import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPaypalHistory, PaypalHistory } from 'app/shared/model/paypal-history.model';
import { PaypalHistoryService } from './paypal-history.service';

@Component({
  selector: 'jhi-paypal-history-update',
  templateUrl: './paypal-history-update.component.html'
})
export class PaypalHistoryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    shopifyOrderId: [null, [Validators.required]],
    shopifyTrackingNumber: [null, [Validators.required]],
    shopifyAuthorizationKey: [null, [Validators.required]],
    shopifyShippingStatus: [null, [Validators.required]],
    carrier: [null, [Validators.required]],
    status: [null, [Validators.required]],
    createdAt: [],
    updatedAt: [],
    shopifyOrderNumber: [null, [Validators.required]],
    shopifyOrderName: [null, []]
  });

  constructor(protected paypalHistoryService: PaypalHistoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paypalHistory }) => {
      this.updateForm(paypalHistory);
    });
  }

  updateForm(paypalHistory: IPaypalHistory) {
    this.editForm.patchValue({
      id: paypalHistory.id,
      shopifyOrderId: paypalHistory.shopifyOrderId,
      shopifyTrackingNumber: paypalHistory.shopifyTrackingNumber,
      shopifyAuthorizationKey: paypalHistory.shopifyAuthorizationKey,
      shopifyShippingStatus: paypalHistory.shopifyShippingStatus,
      carrier: paypalHistory.carrier,
      status: paypalHistory.status,
      createdAt: paypalHistory.createdAt != null ? paypalHistory.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: paypalHistory.updatedAt != null ? paypalHistory.updatedAt.format(DATE_TIME_FORMAT) : null,
      shopifyOrderNumber: paypalHistory.shopifyOrderNumber,
      shopifyOrderName: paypalHistory.shopifyOrderName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paypalHistory = this.createFromForm();
    if (paypalHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.paypalHistoryService.update(paypalHistory));
    } else {
      this.subscribeToSaveResponse(this.paypalHistoryService.create(paypalHistory));
    }
  }

  private createFromForm(): IPaypalHistory {
    return {
      ...new PaypalHistory(),
      id: this.editForm.get(['id']).value,
      shopifyOrderId: this.editForm.get(['shopifyOrderId']).value,
      shopifyTrackingNumber: this.editForm.get(['shopifyTrackingNumber']).value,
      shopifyAuthorizationKey: this.editForm.get(['shopifyAuthorizationKey']).value,
      shopifyShippingStatus: this.editForm.get(['shopifyShippingStatus']).value,
      carrier: this.editForm.get(['carrier']).value,
      status: this.editForm.get(['status']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      shopifyOrderNumber: this.editForm.get(['shopifyOrderNumber']).value,
      shopifyOrderName: this.editForm.get(['shopifyOrderName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaypalHistory>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
