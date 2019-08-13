/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PaypalmngTestModule } from '../../../test.module';
import { PaypalDeleteDialogComponent } from 'app/entities/paypal/paypal-delete-dialog.component';
import { PaypalService } from 'app/entities/paypal/paypal.service';

describe('Component Tests', () => {
  describe('Paypal Management Delete Component', () => {
    let comp: PaypalDeleteDialogComponent;
    let fixture: ComponentFixture<PaypalDeleteDialogComponent>;
    let service: PaypalService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaypalmngTestModule],
        declarations: [PaypalDeleteDialogComponent]
      })
        .overrideTemplate(PaypalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaypalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaypalService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
