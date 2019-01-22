/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { ContactPmeDetailComponent } from 'app/entities/contact-pme/contact-pme-detail.component';
import { ContactPme } from 'app/shared/model/contact-pme.model';

describe('Component Tests', () => {
    describe('ContactPme Management Detail Component', () => {
        let comp: ContactPmeDetailComponent;
        let fixture: ComponentFixture<ContactPmeDetailComponent>;
        const route = ({ data: of({ contactPme: new ContactPme(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [ContactPmeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContactPmeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactPmeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contactPme).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
