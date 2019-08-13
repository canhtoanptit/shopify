import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaypal } from 'app/shared/model/paypal.model';

@Component({
  selector: 'jhi-paypal-detail',
  templateUrl: './paypal-detail.component.html'
})
export class PaypalDetailComponent implements OnInit {
  paypal: IPaypal;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paypal }) => {
      this.paypal = paypal;
    });
  }

  previousState() {
    window.history.back();
  }
}
