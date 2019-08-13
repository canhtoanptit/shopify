/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaypalmngTestModule } from '../../../test.module';
import { PaypalDetailComponent } from 'app/entities/paypal/paypal-detail.component';
import { Paypal } from 'app/shared/model/paypal.model';

describe('Component Tests', () => {
  describe('Paypal Management Detail Component', () => {
    let comp: PaypalDetailComponent;
    let fixture: ComponentFixture<PaypalDetailComponent>;
    const route = ({ data: of({ paypal: new Paypal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaypalmngTestModule],
        declarations: [PaypalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaypalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaypalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paypal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
