import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPret } from 'app/shared/model/pret.model';

@Component({
    selector: 'jhi-pret-detail',
    templateUrl: './pret-detail.component.html'
})
export class PretDetailComponent implements OnInit {
    pret: IPret;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pret }) => {
            this.pret = pret;
        });
    }

    previousState() {
        window.history.back();
    }
}
