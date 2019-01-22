import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FenetrePmEv2PmeModule } from './pme/pme.module';
import { FenetrePmEv2PretModule } from './pret/pret.module';
import { FenetrePmEv2ProjetModule } from './projet/projet.module';
import { FenetrePmEv2FichierModule } from './fichier/fichier.module';
import { FenetrePmEv2ImfModule } from './imf/imf.module';
import { FenetrePmEv2ContactPmeModule } from './contact-pme/contact-pme.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        FenetrePmEv2PmeModule,
        FenetrePmEv2PretModule,
        FenetrePmEv2ProjetModule,
        FenetrePmEv2FichierModule,
        FenetrePmEv2ImfModule,
        FenetrePmEv2ContactPmeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FenetrePmEv2EntityModule {}
