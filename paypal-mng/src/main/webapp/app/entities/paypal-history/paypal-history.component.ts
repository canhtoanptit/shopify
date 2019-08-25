import {Component, ElementRef, EventEmitter, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {IPaypalHistory} from 'app/shared/model/paypal-history.model';
import {AccountService} from 'app/core';

import {ITEMS_PER_PAGE} from 'app/shared';
import {PaypalHistoryService} from './paypal-history.service';

@Component({
  selector: 'jhi-paypal-history',
  templateUrl: './paypal-history.component.html',
})
export class PaypalHistoryComponent implements OnInit, OnDestroy {
  currentAccount: any;
  paypalHistories: IPaypalHistory[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  searchText = '';

  @ViewChild('searchCondition', {static: false})
  searchConditionElement: ElementRef;

  @Output()
  search: EventEmitter<string> = new EventEmitter<string>();

  constructor(
    protected paypalHistoryService: PaypalHistoryService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.paypalHistoryService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        searchParam: this.searchText,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPaypalHistory[]>) => this.paginatePaypalHistories(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  removeSearchText() {
    this.searchText = '';
  }

  setFocus() {
    this.searchConditionElement.nativeElement.focus();
  }

  querySearch() {
    this.searchText = this.searchText.trim();
    this.search.emit(this.searchText);
    this.loadAll();
  }

  transition() {
    this.router.navigate(['/paypal-history'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/paypal-history',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPaypalHistories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaypalHistory) {
    return item.id;
  }

  registerChangeInPaypalHistories() {
    this.eventSubscriber = this.eventManager.subscribe('paypalHistoryListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePaypalHistories(data: IPaypalHistory[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.paypalHistories = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
