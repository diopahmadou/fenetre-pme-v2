import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FenetrePmEv2SharedModule } from 'app/shared';
import {
    ImfComponent,
    ImfDetailComponent,
    ImfUpdateComponent,
    ImfDeletePopupComponent,
    ImfDeleteDialogComponent,
    imfRoute,
    imfPopupRoute
} from './';

const ENTITY_STATES = [...imfRoute, ...imfPopupRoute];

@NgModule({
    imports: [FenetrePmEv2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ImfComponent, ImfDetailComponent, ImfUpdateComponent, ImfDeleteDialogComponent, ImfDeletePopupComponent],
    entryComponents: [ImfComponent, ImfUpdateComponent, ImfDeleteDialogComponent, ImfDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FenetrePmEv2ImfModule {}
