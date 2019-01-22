import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactPme } from 'app/shared/model/contact-pme.model';

@Component({
    selector: 'jhi-contact-pme-detail',
    templateUrl: './contact-pme-detail.component.html'
})
export class ContactPmeDetailComponent implements OnInit {
    contactPme: IContactPme;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactPme }) => {
            this.contactPme = contactPme;
        });
    }

    previousState() {
        window.history.back();
    }
}
