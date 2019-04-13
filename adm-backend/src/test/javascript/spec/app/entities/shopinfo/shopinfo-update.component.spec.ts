/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ShopifymTestModule } from '../../../test.module';
import { ShopinfoUpdateComponent } from 'app/entities/shopinfo/shopinfo-update.component';
import { ShopinfoService } from 'app/entities/shopinfo/shopinfo.service';
import { Shopinfo } from 'app/shared/model/shopinfo.model';

describe('Component Tests', () => {
    describe('ShopInfo Management Update Component', () => {
        let comp: ShopinfoUpdateComponent;
        let fixture: ComponentFixture<ShopinfoUpdateComponent>;
        let service: ShopinfoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShopifymTestModule],
                declarations: [ShopinfoUpdateComponent]
            })
                .overrideTemplate(ShopinfoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShopinfoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopinfoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Shopinfo(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.shopinfo = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Shopinfo();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.shopinfo = entity;
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
