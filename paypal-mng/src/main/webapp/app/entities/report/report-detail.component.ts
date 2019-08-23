import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReport } from 'app/shared/model/report.model';

@Component({
  selector: 'jhi-report-detail',
  templateUrl: './report-detail.component.html'
})
export class ReportDetailComponent implements OnInit {
  report: IReport;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
  }

  previousState() {
    window.history.back();
  }
}
