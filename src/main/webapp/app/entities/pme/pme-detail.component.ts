import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPme } from 'app/shared/model/pme.model';

@Component({
    selector: 'jhi-pme-detail',
    templateUrl: './pme-detail.component.html'
})
export class PmeDetailComponent implements OnInit {
    pme: IPme;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pme }) => {
            this.pme = pme;
        });
    }

    previousState() {
        window.history.back();
    }
}
