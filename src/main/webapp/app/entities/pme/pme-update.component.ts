import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IPme } from 'app/shared/model/pme.model';
import { PmeService } from './pme.service';
import { IImf } from 'app/shared/model/imf.model';
import { ImfService } from 'app/entities/imf';

@Component({
    selector: 'jhi-pme-update',
    templateUrl: './pme-update.component.html'
})
export class PmeUpdateComponent implements OnInit {
    pme: IPme;
    isSaving: boolean;

    imfs: IImf[];
    dateCreationDp: any;
    dateEnregistrementDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pmeService: PmeService,
        protected imfService: ImfService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pme }) => {
            this.pme = pme;
        });
        this.imfService.query().subscribe(
            (res: HttpResponse<IImf[]>) => {
                this.imfs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pme.id !== undefined) {
            this.subscribeToSaveResponse(this.pmeService.update(this.pme));
        } else {
            this.subscribeToSaveResponse(this.pmeService.create(this.pme));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPme>>) {
        result.subscribe((res: HttpResponse<IPme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackImfById(index: number, item: IImf) {
        return item.id;
    }
}
