import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaypalHistory } from 'app/shared/model/paypal-history.model';

@Component({
  selector: 'jhi-paypal-history-detail',
  templateUrl: './paypal-history-detail.component.html'
})
export class PaypalHistoryDetailComponent implements OnInit {
  paypalHistory: IPaypalHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paypalHistory }) => {
      this.paypalHistory = paypalHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
