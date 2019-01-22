/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { ContactPmeUpdateComponent } from 'app/entities/contact-pme/contact-pme-update.component';
import { ContactPmeService } from 'app/entities/contact-pme/contact-pme.service';
import { ContactPme } from 'app/shared/model/contact-pme.model';

describe('Component Tests', () => {
    describe('ContactPme Management Update Component', () => {
        let comp: ContactPmeUpdateComponent;
        let fixture: ComponentFixture<ContactPmeUpdateComponent>;
        let service: ContactPmeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [ContactPmeUpdateComponent]
            })
                .overrideTemplate(ContactPmeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactPmeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactPmeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactPme(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactPme = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContactPme();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contactPme = entity;
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
