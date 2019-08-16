/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PaypalmngTestModule } from '../../../test.module';
import { PaypalHistoryDeleteDialogComponent } from 'app/entities/paypal-history/paypal-history-delete-dialog.component';
import { PaypalHistoryService } from 'app/entities/paypal-history/paypal-history.service';

describe('Component Tests', () => {
  describe('PaypalHistory Management Delete Component', () => {
    let comp: PaypalHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<PaypalHistoryDeleteDialogComponent>;
    let service: PaypalHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaypalmngTestModule],
        declarations: [PaypalHistoryDeleteDialogComponent]
      })
        .overrideTemplate(PaypalHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaypalHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaypalHistoryService);
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
