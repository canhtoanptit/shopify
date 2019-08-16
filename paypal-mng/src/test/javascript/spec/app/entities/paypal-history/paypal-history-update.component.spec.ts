/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PaypalmngTestModule } from '../../../test.module';
import { PaypalHistoryUpdateComponent } from 'app/entities/paypal-history/paypal-history-update.component';
import { PaypalHistoryService } from 'app/entities/paypal-history/paypal-history.service';
import { PaypalHistory } from 'app/shared/model/paypal-history.model';

describe('Component Tests', () => {
  describe('PaypalHistory Management Update Component', () => {
    let comp: PaypalHistoryUpdateComponent;
    let fixture: ComponentFixture<PaypalHistoryUpdateComponent>;
    let service: PaypalHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaypalmngTestModule],
        declarations: [PaypalHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaypalHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaypalHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaypalHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaypalHistory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaypalHistory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
