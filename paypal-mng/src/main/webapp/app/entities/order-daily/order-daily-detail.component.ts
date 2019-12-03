import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderDaily } from 'app/shared/model/order-daily.model';

@Component({
  selector: 'jhi-order-daily-detail',
  templateUrl: './order-daily-detail.component.html'
})
export class OrderDailyDetailComponent implements OnInit {
  orderDaily: IOrderDaily;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderDaily }) => {
      this.orderDaily = orderDaily;
    });
  }

  previousState() {
    window.history.back();
  }
}
