import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactPme } from 'app/shared/model/contact-pme.model';
import { ContactPmeService } from './contact-pme.service';

@Component({
    selector: 'jhi-contact-pme-delete-dialog',
    templateUrl: './contact-pme-delete-dialog.component.html'
})
export class ContactPmeDeleteDialogComponent {
    contactPme: IContactPme;

    constructor(
        protected contactPmeService: ContactPmeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactPmeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contactPmeListModification',
                content: 'Deleted an contactPme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-pme-delete-popup',
    template: ''
})
export class ContactPmeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactPme }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContactPmeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.contactPme = contactPme;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
