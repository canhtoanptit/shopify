/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShopifymTestModule } from '../../../test.module';
import { ShopinfoDeleteDialogComponent } from 'app/entities/shopinfo/shopinfo-delete-dialog.component';
import { ShopinfoService } from 'app/entities/shopinfo/shopinfo.service';

describe('Component Tests', () => {
    describe('ShopInfo Management Delete Component', () => {
        let comp: ShopinfoDeleteDialogComponent;
        let fixture: ComponentFixture<ShopinfoDeleteDialogComponent>;
        let service: ShopinfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShopifymTestModule],
                declarations: [ShopinfoDeleteDialogComponent]
            })
                .overrideTemplate(ShopinfoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ShopinfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopinfoService);
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
