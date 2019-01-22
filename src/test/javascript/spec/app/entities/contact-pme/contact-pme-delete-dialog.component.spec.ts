/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FenetrePmEv2TestModule } from '../../../test.module';
import { ContactPmeDeleteDialogComponent } from 'app/entities/contact-pme/contact-pme-delete-dialog.component';
import { ContactPmeService } from 'app/entities/contact-pme/contact-pme.service';

describe('Component Tests', () => {
    describe('ContactPme Management Delete Component', () => {
        let comp: ContactPmeDeleteDialogComponent;
        let fixture: ComponentFixture<ContactPmeDeleteDialogComponent>;
        let service: ContactPmeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FenetrePmEv2TestModule],
                declarations: [ContactPmeDeleteDialogComponent]
            })
                .overrideTemplate(ContactPmeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactPmeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactPmeService);
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
