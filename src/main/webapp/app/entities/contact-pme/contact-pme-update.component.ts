import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IContactPme } from 'app/shared/model/contact-pme.model';
import { ContactPmeService } from './contact-pme.service';
import { IPme } from 'app/shared/model/pme.model';
import { PmeService } from 'app/entities/pme';

@Component({
    selector: 'jhi-contact-pme-update',
    templateUrl: './contact-pme-update.component.html'
})
export class ContactPmeUpdateComponent implements OnInit {
    contactPme: IContactPme;
    isSaving: boolean;

    pmes: IPme[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected contactPmeService: ContactPmeService,
        protected pmeService: PmeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contactPme }) => {
            this.contactPme = contactPme;
        });
        this.pmeService.query().subscribe(
            (res: HttpResponse<IPme[]>) => {
                this.pmes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contactPme.id !== undefined) {
            this.subscribeToSaveResponse(this.contactPmeService.update(this.contactPme));
        } else {
            this.subscribeToSaveResponse(this.contactPmeService.create(this.contactPme));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactPme>>) {
        result.subscribe((res: HttpResponse<IContactPme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPmeById(index: number, item: IPme) {
        return item.id;
    }
}
