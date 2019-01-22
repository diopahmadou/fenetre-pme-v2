/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { ImfDetailComponent } from 'app/entities/imf/imf-detail.component';
import { Imf } from 'app/shared/model/imf.model';

describe('Component Tests', () => {
    describe('Imf Management Detail Component', () => {
        let comp: ImfDetailComponent;
        let fixture: ComponentFixture<ImfDetailComponent>;
        const route = ({ data: of({ imf: new Imf(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [ImfDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ImfDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ImfDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.imf).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
