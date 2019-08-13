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
    client_id: [null, [Validators.required]],
    secret: [null, [Validators.required]],
    created_at: [],
    updated_at: []
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
      client_id: paypal.client_id,
      secret: paypal.secret,
      created_at: paypal.created_at != null ? paypal.created_at.format(DATE_TIME_FORMAT) : null,
      updated_at: paypal.updated_at != null ? paypal.updated_at.format(DATE_TIME_FORMAT) : null
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
      client_id: this.editForm.get(['client_id']).value,
      secret: this.editForm.get(['secret']).value,
      created_at:
        this.editForm.get(['created_at']).value != null ? moment(this.editForm.get(['created_at']).value, DATE_TIME_FORMAT) : undefined,
      updated_at:
        this.editForm.get(['updated_at']).value != null ? moment(this.editForm.get(['updated_at']).value, DATE_TIME_FORMAT) : undefined
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
