/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { PmeDetailComponent } from 'app/entities/pme/pme-detail.component';
import { Pme } from 'app/shared/model/pme.model';

describe('Component Tests', () => {
    describe('Pme Management Detail Component', () => {
        let comp: PmeDetailComponent;
        let fixture: ComponentFixture<PmeDetailComponent>;
        const route = ({ data: of({ pme: new Pme(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [PmeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PmeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PmeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pme).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
