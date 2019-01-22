import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FenetrePmEv2SharedModule } from 'app/shared';
import {
    PmeComponent,
    PmeDetailComponent,
    PmeUpdateComponent,
    PmeDeletePopupComponent,
    PmeDeleteDialogComponent,
    pmeRoute,
    pmePopupRoute
} from './';

const ENTITY_STATES = [...pmeRoute, ...pmePopupRoute];

@NgModule({
    imports: [FenetrePmEv2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PmeComponent, PmeDetailComponent, PmeUpdateComponent, PmeDeleteDialogComponent, PmeDeletePopupComponent],
    entryComponents: [PmeComponent, PmeUpdateComponent, PmeDeleteDialogComponent, PmeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FenetrePmEv2PmeModule {}
