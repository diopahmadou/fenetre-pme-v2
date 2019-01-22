/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { PmeUpdateComponent } from 'app/entities/pme/pme-update.component';
import { PmeService } from 'app/entities/pme/pme.service';
import { Pme } from 'app/shared/model/pme.model';

describe('Component Tests', () => {
    describe('Pme Management Update Component', () => {
        let comp: PmeUpdateComponent;
        let fixture: ComponentFixture<PmeUpdateComponent>;
        let service: PmeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [PmeUpdateComponent]
            })
                .overrideTemplate(PmeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PmeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PmeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Pme(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.pme = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Pme();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.pme = entity;
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
