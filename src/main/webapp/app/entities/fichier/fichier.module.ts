import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FenetrePmEv2SharedModule } from 'app/shared';
import {
    FichierComponent,
    FichierDetailComponent,
    FichierUpdateComponent,
    FichierDeletePopupComponent,
    FichierDeleteDialogComponent,
    fichierRoute,
    fichierPopupRoute
} from './';

const ENTITY_STATES = [...fichierRoute, ...fichierPopupRoute];

@NgModule({
    imports: [FenetrePmEv2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FichierComponent,
        FichierDetailComponent,
        FichierUpdateComponent,
        FichierDeleteDialogComponent,
        FichierDeletePopupComponent
    ],
    entryComponents: [FichierComponent, FichierUpdateComponent, FichierDeleteDialogComponent, FichierDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FenetrePmEv2FichierModule {}
