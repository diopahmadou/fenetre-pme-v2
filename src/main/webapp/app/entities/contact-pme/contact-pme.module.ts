import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FenetrePmEv2SharedModule } from 'app/shared';
import {
    ContactPmeComponent,
    ContactPmeDetailComponent,
    ContactPmeUpdateComponent,
    ContactPmeDeletePopupComponent,
    ContactPmeDeleteDialogComponent,
    contactPmeRoute,
    contactPmePopupRoute
} from './';

const ENTITY_STATES = [...contactPmeRoute, ...contactPmePopupRoute];

@NgModule({
    imports: [FenetrePmEv2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactPmeComponent,
        ContactPmeDetailComponent,
        ContactPmeUpdateComponent,
        ContactPmeDeleteDialogComponent,
        ContactPmeDeletePopupComponent
    ],
    entryComponents: [ContactPmeComponent, ContactPmeUpdateComponent, ContactPmeDeleteDialogComponent, ContactPmeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FenetrePmEv2ContactPmeModule {}
