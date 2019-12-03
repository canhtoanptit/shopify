import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IOrderDaily, OrderDaily } from 'app/shared/model/order-daily.model';
import { OrderDailyService } from './order-daily.service';

@Component({
  selector: 'jhi-order-daily-update',
  templateUrl: './order-daily-update.component.html'
})
export class OrderDailyUpdateComponent implements OnInit {
  isSaving: boolean;
  paidAtDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    email: [],
    financialStatus: [],
    paidAt: [],
    lineItemQuantity: [],
    lineItemName: [],
    shipingName: [],
    shipingAddress: [],
    shipingStreet: [],
    shipingAddress2: [],
    shipingCompany: [],
    shipingCity: [],
    shipingZip: [],
    shipingProvince: [],
    shipingCountry: [],
    shipingPhone: [],
    note: []
  });

  constructor(protected orderDailyService: OrderDailyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderDaily }) => {
      this.updateForm(orderDaily);
    });
  }

  updateForm(orderDaily: IOrderDaily) {
    this.editForm.patchValue({
      id: orderDaily.id,
      name: orderDaily.name,
      email: orderDaily.email,
      financialStatus: orderDaily.financialStatus,
      paidAt: orderDaily.paidAt,
      lineItemQuantity: orderDaily.lineItemQuantity,
      lineItemName: orderDaily.lineItemName,
      shipingName: orderDaily.shipingName,
      shipingAddress: orderDaily.shipingAddress,
      shipingStreet: orderDaily.shipingStreet,
      shipingAddress2: orderDaily.shipingAddress2,
      shipingCompany: orderDaily.shipingCompany,
      shipingCity: orderDaily.shipingCity,
      shipingZip: orderDaily.shipingZip,
      shipingProvince: orderDaily.shipingProvince,
      shipingCountry: orderDaily.shipingCountry,
      shipingPhone: orderDaily.shipingPhone,
      note: orderDaily.note
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderDaily = this.createFromForm();
    if (orderDaily.id !== undefined) {
      this.subscribeToSaveResponse(this.orderDailyService.update(orderDaily));
    } else {
      this.subscribeToSaveResponse(this.orderDailyService.create(orderDaily));
    }
  }

  private createFromForm(): IOrderDaily {
    return {
      ...new OrderDaily(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      email: this.editForm.get(['email']).value,
      financialStatus: this.editForm.get(['financialStatus']).value,
      paidAt: this.editForm.get(['paidAt']).value,
      lineItemQuantity: this.editForm.get(['lineItemQuantity']).value,
      lineItemName: this.editForm.get(['lineItemName']).value,
      shipingName: this.editForm.get(['shipingName']).value,
      shipingAddress: this.editForm.get(['shipingAddress']).value,
      shipingStreet: this.editForm.get(['shipingStreet']).value,
      shipingAddress2: this.editForm.get(['shipingAddress2']).value,
      shipingCompany: this.editForm.get(['shipingCompany']).value,
      shipingCity: this.editForm.get(['shipingCity']).value,
      shipingZip: this.editForm.get(['shipingZip']).value,
      shipingProvince: this.editForm.get(['shipingProvince']).value,
      shipingCountry: this.editForm.get(['shipingCountry']).value,
      shipingPhone: this.editForm.get(['shipingPhone']).value,
      note: this.editForm.get(['note']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderDaily>>) {
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
