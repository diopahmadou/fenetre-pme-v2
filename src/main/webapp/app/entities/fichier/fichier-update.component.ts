import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IFichier } from 'app/shared/model/fichier.model';
import { FichierService } from './fichier.service';
import { IPret } from 'app/shared/model/pret.model';
import { PretService } from 'app/entities/pret';

@Component({
    selector: 'jhi-fichier-update',
    templateUrl: './fichier-update.component.html'
})
export class FichierUpdateComponent implements OnInit {
    fichier: IFichier;
    isSaving: boolean;

    prets: IPret[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected fichierService: FichierService,
        protected pretService: PretService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fichier }) => {
            this.fichier = fichier;
        });
        this.pretService.query().subscribe(
            (res: HttpResponse<IPret[]>) => {
                this.prets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fichier.id !== undefined) {
            this.subscribeToSaveResponse(this.fichierService.update(this.fichier));
        } else {
            this.subscribeToSaveResponse(this.fichierService.create(this.fichier));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichier>>) {
        result.subscribe((res: HttpResponse<IFichier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPretById(index: number, item: IPret) {
        return item.id;
    }
}
