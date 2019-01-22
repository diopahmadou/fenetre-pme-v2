import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImf } from 'app/shared/model/imf.model';

@Component({
    selector: 'jhi-imf-detail',
    templateUrl: './imf-detail.component.html'
})
export class ImfDetailComponent implements OnInit {
    imf: IImf;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ imf }) => {
            this.imf = imf;
        });
    }

    previousState() {
        window.history.back();
    }
}
