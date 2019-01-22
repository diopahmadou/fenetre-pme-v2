/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { PmeDeleteDialogComponent } from 'app/entities/pme/pme-delete-dialog.component';
import { PmeService } from 'app/entities/pme/pme.service';

describe('Component Tests', () => {
    describe('Pme Management Delete Component', () => {
        let comp: PmeDeleteDialogComponent;
        let fixture: ComponentFixture<PmeDeleteDialogComponent>;
        let service: PmeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [PmeDeleteDialogComponent]
            })
                .overrideTemplate(PmeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PmeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PmeService);
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
