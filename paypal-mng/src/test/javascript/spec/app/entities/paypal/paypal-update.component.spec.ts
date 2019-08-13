/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PaypalmngTestModule } from '../../../test.module';
import { PaypalUpdateComponent } from 'app/entities/paypal/paypal-update.component';
import { PaypalService } from 'app/entities/paypal/paypal.service';
import { Paypal } from 'app/shared/model/paypal.model';

describe('Component Tests', () => {
  describe('Paypal Management Update Component', () => {
    let comp: PaypalUpdateComponent;
    let fixture: ComponentFixture<PaypalUpdateComponent>;
    let service: PaypalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaypalmngTestModule],
        declarations: [PaypalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaypalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaypalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaypalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Paypal(123);
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
        const entity = new Paypal();
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
