import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStore, Store } from 'app/shared/model/store.model';
import { StoreService } from './store.service';
import { IPaypal } from 'app/shared/model/paypal.model';
import { PaypalService } from 'app/entities/paypal';

@Component({
  selector: 'jhi-store-update',
  templateUrl: './store-update.component.html'
})
export class StoreUpdateComponent implements OnInit {
  isSaving: boolean;

  paypals: IPaypal[];

  editForm = this.fb.group({
    id: [],
    shopifyApiKey: [null, [Validators.required]],
    shopifyApiPassword: [null, [Validators.required]],
    storeName: [null, [Validators.required]],
    createdAt: [],
    updatedAt: [],
    shopifyApiUrl: [null, [Validators.required]],
    automationStatus: [],
    sinceId: [],
    paypalId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected storeService: StoreService,
    protected paypalService: PaypalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ store }) => {
      this.updateForm(store);
    });
    this.paypalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPaypal[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaypal[]>) => response.body)
      )
      .subscribe((res: IPaypal[]) => (this.paypals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(store: IStore) {
    this.editForm.patchValue({
      id: store.id,
      shopifyApiKey: store.shopifyApiKey,
      shopifyApiPassword: store.shopifyApiPassword,
      storeName: store.storeName,
      createdAt: store.createdAt != null ? store.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: store.updatedAt != null ? store.updatedAt.format(DATE_TIME_FORMAT) : null,
      shopifyApiUrl: store.shopifyApiUrl,
      automationStatus: store.automationStatus,
      sinceId: store.sinceId,
      paypalId: store.paypalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const store = this.createFromForm();
    if (store.id !== undefined) {
      this.subscribeToSaveResponse(this.storeService.update(store));
    } else {
      this.subscribeToSaveResponse(this.storeService.create(store));
    }
  }

  private createFromForm(): IStore {
    return {
      ...new Store(),
      id: this.editForm.get(['id']).value,
      shopifyApiKey: this.editForm.get(['shopifyApiKey']).value,
      shopifyApiPassword: this.editForm.get(['shopifyApiPassword']).value,
      storeName: this.editForm.get(['storeName']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      shopifyApiUrl: this.editForm.get(['shopifyApiUrl']).value,
      automationStatus: this.editForm.get(['automationStatus']).value,
      sinceId: this.editForm.get(['sinceId']).value,
      paypalId: this.editForm.get(['paypalId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStore>>) {
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

  trackPaypalById(index: number, item: IPaypal) {
    return item.id;
  }
}
