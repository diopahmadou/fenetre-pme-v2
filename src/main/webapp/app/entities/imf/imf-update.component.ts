import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IImf } from 'app/shared/model/imf.model';
import { ImfService } from './imf.service';

@Component({
    selector: 'jhi-imf-update',
    templateUrl: './imf-update.component.html'
})
export class ImfUpdateComponent implements OnInit {
    imf: IImf;
    isSaving: boolean;

    constructor(protected imfService: ImfService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ imf }) => {
            this.imf = imf;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.imf.id !== undefined) {
            this.subscribeToSaveResponse(this.imfService.update(this.imf));
        } else {
            this.subscribeToSaveResponse(this.imfService.create(this.imf));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IImf>>) {
        result.subscribe((res: HttpResponse<IImf>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
