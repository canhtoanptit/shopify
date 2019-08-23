import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {IReport} from 'app/shared/model/report.model';
import {AccountService} from 'app/core';
import {ReportService} from './report.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit, OnDestroy {
  reports: IReport[];
  currentAccount: any;
  eventSubscriber: Subscription;
  @ViewChild('file', {static: false}) file;
  public files: Set<File> = new Set();

  constructor(
    protected reportService: ReportService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {
  }

  loadAll() {
    this.reportService
      .query()
      .pipe(
        filter((res: HttpResponse<IReport[]>) => res.ok),
        map((res: HttpResponse<IReport[]>) => res.body)
      )
      .subscribe(
        (res: IReport[]) => {
          this.reports = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  upload() {
    console.log('upload ', this.files);
    this.reportService.uploadTracking(this.files);
  }

  onFilesAdded() {
    const files: { [key: string]: File } = this.file.nativeElement.files;
    for (const key in files) {
      if (!isNaN(parseInt(key, 10))) {
        this.files.add(files[key]);
      }
    }
  }

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInReports();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReport) {
    return item.id;
  }

  registerChangeInReports() {
    this.eventSubscriber = this.eventManager.subscribe('reportListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
