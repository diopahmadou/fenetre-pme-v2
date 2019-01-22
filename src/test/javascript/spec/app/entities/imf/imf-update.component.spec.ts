/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { ImfUpdateComponent } from 'app/entities/imf/imf-update.component';
import { ImfService } from 'app/entities/imf/imf.service';
import { Imf } from 'app/shared/model/imf.model';

describe('Component Tests', () => {
    describe('Imf Management Update Component', () => {
        let comp: ImfUpdateComponent;
        let fixture: ComponentFixture<ImfUpdateComponent>;
        let service: ImfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [ImfUpdateComponent]
            })
                .overrideTemplate(ImfUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ImfUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImfService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Imf(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.imf = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Imf();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.imf = entity;
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
