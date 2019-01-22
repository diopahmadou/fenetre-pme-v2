import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FenetrePmEv2SharedModule } from 'app/shared';
import {
    PretComponent,
    PretDetailComponent,
    PretUpdateComponent,
    PretDeletePopupComponent,
    PretDeleteDialogComponent,
    pretRoute,
    pretPopupRoute
} from './';

const ENTITY_STATES = [...pretRoute, ...pretPopupRoute];

@NgModule({
    imports: [FenetrePmEv2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PretComponent, PretDetailComponent, PretUpdateComponent, PretDeleteDialogComponent, PretDeletePopupComponent],
    entryComponents: [PretComponent, PretUpdateComponent, PretDeleteDialogComponent, PretDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FenetrePmEv2PretModule {}
