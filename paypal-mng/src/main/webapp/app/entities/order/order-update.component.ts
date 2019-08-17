import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  isSaving: boolean;

  stores: IStore[];

  editForm = this.fb.group({
    id: [],
    createdAt: [],
    updatedAt: [],
    orderNumber: [null, [Validators.required]],
    shopifyOrderId: [null, [Validators.required]],
    orderName: [],
    storeId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderService: OrderService,
    protected storeService: StoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ order }) => {
      this.updateForm(order);
    });
    this.storeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStore[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStore[]>) => response.body)
      )
      .subscribe((res: IStore[]) => (this.stores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(order: IOrder) {
    this.editForm.patchValue({
      id: order.id,
      createdAt: order.createdAt != null ? order.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: order.updatedAt != null ? order.updatedAt.format(DATE_TIME_FORMAT) : null,
      orderNumber: order.orderNumber,
      shopifyOrderId: order.shopifyOrderId,
      orderName: order.orderName,
      storeId: order.storeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      orderNumber: this.editForm.get(['orderNumber']).value,
      shopifyOrderId: this.editForm.get(['shopifyOrderId']).value,
      orderName: this.editForm.get(['orderName']).value,
      storeId: this.editForm.get(['storeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>) {
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

  trackStoreById(index: number, item: IStore) {
    return item.id;
  }
}
