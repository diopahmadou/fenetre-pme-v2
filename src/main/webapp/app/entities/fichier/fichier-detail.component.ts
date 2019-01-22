import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFichier } from 'app/shared/model/fichier.model';

@Component({
    selector: 'jhi-fichier-detail',
    templateUrl: './fichier-detail.component.html'
})
export class FichierDetailComponent implements OnInit {
    fichier: IFichier;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fichier }) => {
            this.fichier = fichier;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
