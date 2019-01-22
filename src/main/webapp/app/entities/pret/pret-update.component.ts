import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPret } from 'app/shared/model/pret.model';
import { PretService } from './pret.service';
import { IPme } from 'app/shared/model/pme.model';
import { PmeService } from 'app/entities/pme';

@Component({
    selector: 'jhi-pret-update',
    templateUrl: './pret-update.component.html'
})
export class PretUpdateComponent implements OnInit {
    pret: IPret;
    isSaving: boolean;

    pmes: IPme[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected pretService: PretService,
        protected pmeService: PmeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pret }) => {
            this.pret = pret;
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
        if (this.pret.id !== undefined) {
            this.subscribeToSaveResponse(this.pretService.update(this.pret));
        } else {
            this.subscribeToSaveResponse(this.pretService.create(this.pret));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPret>>) {
        result.subscribe((res: HttpResponse<IPret>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
