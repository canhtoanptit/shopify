/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PaypalmngTestModule } from '../../../test.module';
import { TrackingDeleteDialogComponent } from 'app/entities/tracking/tracking-delete-dialog.component';
import { TrackingService } from 'app/entities/tracking/tracking.service';

describe('Component Tests', () => {
  describe('Tracking Management Delete Component', () => {
    let comp: TrackingDeleteDialogComponent;
    let fixture: ComponentFixture<TrackingDeleteDialogComponent>;
    let service: TrackingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaypalmngTestModule],
        declarations: [TrackingDeleteDialogComponent]
      })
        .overrideTemplate(TrackingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrackingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrackingService);
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
