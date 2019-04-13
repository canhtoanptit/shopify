/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShopifymTestModule } from '../../../test.module';
import { ShopinfoDetailComponent } from 'app/entities/shopinfo/shopinfo-detail.component';
import { Shopinfo } from 'app/shared/model/shopinfo.model';

describe('Component Tests', () => {
    describe('ShopInfo Management Detail Component', () => {
        let comp: ShopinfoDetailComponent;
        let fixture: ComponentFixture<ShopinfoDetailComponent>;
        const route = ({ data: of({ shopinfo: new Shopinfo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShopifymTestModule],
                declarations: [ShopinfoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ShopinfoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ShopinfoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.shopinfo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
