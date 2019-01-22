import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProjet } from 'app/shared/model/projet.model';
import { ProjetService } from './projet.service';
import { IPme } from 'app/shared/model/pme.model';
import { PmeService } from 'app/entities/pme';

@Component({
    selector: 'jhi-projet-update',
    templateUrl: './projet-update.component.html'
})
export class ProjetUpdateComponent implements OnInit {
    projet: IProjet;
    isSaving: boolean;

    pmes: IPme[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected projetService: ProjetService,
        protected pmeService: PmeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ projet }) => {
            this.projet = projet;
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
        if (this.projet.id !== undefined) {
            this.subscribeToSaveResponse(this.projetService.update(this.projet));
        } else {
            this.subscribeToSaveResponse(this.projetService.create(this.projet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjet>>) {
        result.subscribe((res: HttpResponse<IProjet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
