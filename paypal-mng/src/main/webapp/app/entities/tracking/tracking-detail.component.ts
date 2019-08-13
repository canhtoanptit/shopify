import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITracking } from 'app/shared/model/tracking.model';

@Component({
  selector: 'jhi-tracking-detail',
  templateUrl: './tracking-detail.component.html'
})
export class TrackingDetailComponent implements OnInit {
  tracking: ITracking;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tracking }) => {
      this.tracking = tracking;
    });
  }

  previousState() {
    window.history.back();
  }
}
