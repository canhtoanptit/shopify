/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaypalmngTestModule } from '../../../test.module';
import { PaypalHistoryDetailComponent } from 'app/entities/paypal-history/paypal-history-detail.component';
import { PaypalHistory } from 'app/shared/model/paypal-history.model';

describe('Component Tests', () => {
  describe('PaypalHistory Management Detail Component', () => {
    let comp: PaypalHistoryDetailComponent;
    let fixture: ComponentFixture<PaypalHistoryDetailComponent>;
    const route = ({ data: of({ paypalHistory: new PaypalHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaypalmngTestModule],
        declarations: [PaypalHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaypalHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaypalHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paypalHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
