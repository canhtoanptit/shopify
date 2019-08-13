/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaypalmngTestModule } from '../../../test.module';
import { TrackingDetailComponent } from 'app/entities/tracking/tracking-detail.component';
import { Tracking } from 'app/shared/model/tracking.model';

describe('Component Tests', () => {
  describe('Tracking Management Detail Component', () => {
    let comp: TrackingDetailComponent;
    let fixture: ComponentFixture<TrackingDetailComponent>;
    const route = ({ data: of({ tracking: new Tracking(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaypalmngTestModule],
        declarations: [TrackingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrackingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrackingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tracking).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
