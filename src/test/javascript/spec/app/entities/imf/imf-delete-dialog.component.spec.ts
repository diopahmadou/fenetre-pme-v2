/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { ImfDeleteDialogComponent } from 'app/entities/imf/imf-delete-dialog.component';
import { ImfService } from 'app/entities/imf/imf.service';

describe('Component Tests', () => {
    describe('Imf Management Delete Component', () => {
        let comp: ImfDeleteDialogComponent;
        let fixture: ComponentFixture<ImfDeleteDialogComponent>;
        let service: ImfService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [ImfDeleteDialogComponent]
            })
                .overrideTemplate(ImfDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ImfDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImfService);
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
