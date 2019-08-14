import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPaypal, Paypal } from 'app/shared/model/paypal.model';
import { PaypalService } from './paypal.service';

@Component({
  selector: 'jhi-paypal-update',
  templateUrl: './paypal-update.component.html'
})
export class PaypalUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    secret: [null, [Validators.required]],
    clientId: [null, [Validators.required]],
    createdAt: [],
    updatedAt: []
  });

  constructor(protected paypalService: PaypalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paypal }) => {
      this.updateForm(paypal);
    });
  }

  updateForm(paypal: IPaypal) {
    this.editForm.patchValue({
      id: paypal.id,
      secret: paypal.secret,
      clientId: paypal.clientId,
      createdAt: paypal.createdAt != null ? paypal.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: paypal.updatedAt != null ? paypal.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paypal = this.createFromForm();
    if (paypal.id !== undefined) {
      this.subscribeToSaveResponse(this.paypalService.update(paypal));
    } else {
      this.subscribeToSaveResponse(this.paypalService.create(paypal));
    }
  }

  private createFromForm(): IPaypal {
    return {
      ...new Paypal(),
      id: this.editForm.get(['id']).value,
      secret: this.editForm.get(['secret']).value,
      clientId: this.editForm.get(['clientId']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaypal>>) {
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
